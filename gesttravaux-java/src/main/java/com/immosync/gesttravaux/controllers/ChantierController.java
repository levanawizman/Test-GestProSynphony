package com.immosync.gesttravaux.controllers;
import com.immosync.gesttravaux.models.*;
import com.immosync.gesttravaux.services.*;
import com.immosync.gesttravaux.utils.AlertUtil;
import com.immosync.gesttravaux.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class ChantierController {
    private static final Logger logger = LoggerFactory.getLogger(ChantierController.class);
    @FXML private TableView<Chantier> chantierTable;
    @FXML private TableColumn<Chantier, Integer> idColumn;
    @FXML private TableColumn<Chantier, String> adresseColumn, villeColumn, bienColumn, inspecteurColumn, statutColumn;
    @FXML private TextField adresseField, villeField, searchField;
    @FXML private ComboBox<Bien> bienCombo;
    @FXML private ComboBox<Inspecteur> inspecteurCombo;
    @FXML private ComboBox<String> statutCombo;
    @FXML private TextArea infoArea;
    @FXML private ListView<Document> documentsListView;
    private final ChantierService chantierService;
    private final BienService bienService;
    private final InspecteurDAO inspecteurDAO;
    private final ObservableList<Chantier> chantierList;
    private Chantier currentChantier;

    public ChantierController() {
        this.chantierService = new ChantierService();
        this.bienService = new BienService();
        this.inspecteurDAO = new InspecteurDAO();
        this.chantierList = FXCollections.observableArrayList();
    }

    @FXML public void initialize() {
        setupTableColumns();
        setupStatutCombo();
        loadBiens();
        loadInspecteurs();
        loadChantiers();
        setupTableSelectionListener();
        clearForm();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idChantier"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresseChantier"));
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("villeChantier"));
        bienColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getBien() != null ? cellData.getValue().getBien().getFullAddress() : ""
            )
        );
        inspecteurColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getInspecteur() != null ? cellData.getValue().getInspecteur().getFullName() : ""
            )
        );
        statutColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatutString())
        );
    }

    private void setupStatutCombo() {
        statutCombo.setItems(FXCollections.observableArrayList("Non démarré", "En cours", "Terminé"));
    }

    private void setupTableSelectionListener() {
        chantierTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) displayChantier(newSelection);
            }
        );
    }

    private void loadBiens() {
        try {
            List<Bien> biens = bienService.getAllBiens();
            bienCombo.setItems(FXCollections.observableArrayList(biens));
        } catch (Exception e) {
            logger.error("Error loading biens", e);
        }
    }

    private void loadInspecteurs() {
        try {
            List<Inspecteur> inspecteurs = inspecteurDAO.findAll();
            inspecteurCombo.setItems(FXCollections.observableArrayList(inspecteurs));
        } catch (Exception e) {
            logger.error("Error loading inspecteurs", e);
        }
    }

    private void loadChantiers() {
        try {
            List<Chantier> chantiers = chantierService.getAllChantiers();
            chantierList.setAll(chantiers);
            chantierTable.setItems(chantierList);
            logger.info("Loaded {} chantiers", chantiers.size());
        } catch (Exception e) {
            logger.error("Error loading chantiers", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    private void displayChantier(Chantier chantier) {
        currentChantier = chantier;
        adresseField.setText(chantier.getAdresseChantier());
        villeField.setText(chantier.getVilleChantier());
        bienCombo.setValue(chantier.getBien());
        inspecteurCombo.setValue(chantier.getInspecteur());
        statutCombo.setValue(chantier.getStatutString());
        infoArea.setText(chantier.getInfoChantier());
        documentsListView.setItems(FXCollections.observableArrayList(chantier.getDocuments()));
    }

    @FXML private void handleNew() { clearForm(); chantierTable.getSelectionModel().clearSelection(); }
    @FXML private void handleEdit() { 
        if (chantierTable.getSelectionModel().getSelectedItem() == null)
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un chantier à modifier.");
    }

    @FXML private void handleDelete() {
        Chantier selected = chantierTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un chantier à supprimer.");
            return;
        }
        if (AlertUtil.showDeleteConfirmation("Chantier #" + selected.getIdChantier())) {
            try {
                chantierService.deleteChantier(selected.getIdChantier());
                AlertUtil.showSuccess("Chantier supprimé avec succès");
                loadChantiers();
                clearForm();
            } catch (Exception e) {
                logger.error("Error deleting chantier", e);
                AlertUtil.showError("Erreur de suppression", e);
            }
        }
    }

    @FXML private void handleSave() {
        try {
            if (currentChantier == null) {
                Chantier chantier = new Chantier();
                setChantierFromForm(chantier);
                chantierService.createChantier(chantier);
                AlertUtil.showSuccess("Chantier créé avec succès");
            } else {
                setChantierFromForm(currentChantier);
                chantierService.updateChantier(currentChantier);
                AlertUtil.showSuccess("Chantier modifié avec succès");
            }
            loadChantiers();
            clearForm();
        } catch (IllegalArgumentException e) {
            AlertUtil.showValidationError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving chantier", e);
            AlertUtil.showError("Erreur de sauvegarde", e);
        }
    }

    private void setChantierFromForm(Chantier chantier) {
        chantier.setAdresseChantier(adresseField.getText().trim());
        chantier.setVilleChantier(villeField.getText().trim());
        chantier.setBien(bienCombo.getValue());
        chantier.setInspecteur(inspecteurCombo.getValue());
        chantier.setInfoChantier(infoArea.getText().trim());
        String statut = statutCombo.getValue();
        if (statut != null) {
            if (statut.equals("Non démarré")) chantier.setStatutChantier(0);
            else if (statut.equals("En cours")) chantier.setStatutChantier(1);
            else if (statut.equals("Terminé")) chantier.setStatutChantier(2);
        }
    }

    @FXML private void handleCancel() { clearForm(); chantierTable.getSelectionModel().clearSelection(); }
    @FXML private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) loadChantiers();
        else {
            try {
                List<Chantier> results = chantierService.search(searchTerm);
                chantierList.setAll(results);
            } catch (Exception e) {
                logger.error("Error searching chantiers", e);
                AlertUtil.showError("Erreur de recherche", e);
            }
        }
    }

    @FXML private void handleRefresh() { loadChantiers(); clearForm(); }
    
    @FXML private void handleAddDocument() {
        if (currentChantier == null) {
            AlertUtil.showWarning("Attention", "Aucun chantier sélectionné", "Veuillez sélectionner un chantier.");
            return;
        }
        AlertUtil.showInfo("Information", "Fonctionnalité", "L'ajout de documents sera implémenté dans une version ultérieure.");
    }

    @FXML private void handleViewReport() {
        if (currentChantier == null) {
            AlertUtil.showWarning("Attention", "Aucun chantier sélectionné", "Veuillez sélectionner un chantier.");
            return;
        }
        AlertUtil.showInfo("Rapports d'inspection", "Documents disponibles", 
            "Nombre de documents: " + currentChantier.getDocuments().size());
    }

    private void clearForm() {
        currentChantier = null;
        adresseField.clear();
        villeField.clear();
        bienCombo.setValue(null);
        inspecteurCombo.setValue(null);
        statutCombo.setValue(null);
        infoArea.clear();
        documentsListView.getItems().clear();
    }
}
