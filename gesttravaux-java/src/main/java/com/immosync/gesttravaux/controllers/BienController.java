package com.immosync.gesttravaux.controllers;
import com.immosync.gesttravaux.models.*;
import com.immosync.gesttravaux.services.*;
import com.immosync.gesttravaux.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class BienController {
    private static final Logger logger = LoggerFactory.getLogger(BienController.class);
    @FXML private TableView<Bien> bienTable;
    @FXML private TableColumn<Bien, Integer> idColumn;
    @FXML private TableColumn<Bien, String> adresseColumn, villeColumn, proprietaireColumn;
    @FXML private TextField adresseField, villeField, searchField;
    @FXML private ComboBox<Proprietaire> proprietaireCombo;
    private final BienService bienService;
    private final ProprietaireService proprietaireService;
    private final ObservableList<Bien> bienList;
    private Bien currentBien;

    public BienController() {
        this.bienService = new BienService();
        this.proprietaireService = new ProprietaireService();
        this.bienList = FXCollections.observableArrayList();
    }

    @FXML public void initialize() {
        setupTableColumns();
        loadProprietaires();
        loadBiens();
        setupTableSelectionListener();
        clearForm();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBien"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresseBien"));
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("villeBien"));
        proprietaireColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProprietaire() != null ? 
                cellData.getValue().getProprietaire().getFullName() : ""
            )
        );
    }

    private void setupTableSelectionListener() {
        bienTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) displayBien(newSelection);
            }
        );
    }

    private void loadProprietaires() {
        try {
            List<Proprietaire> proprietaires = proprietaireService.getAllProprietaires();
            proprietaireCombo.setItems(FXCollections.observableArrayList(proprietaires));
        } catch (Exception e) {
            logger.error("Error loading proprietaires", e);
        }
    }

    private void loadBiens() {
        try {
            List<Bien> biens = bienService.getAllBiens();
            bienList.setAll(biens);
            bienTable.setItems(bienList);
            logger.info("Loaded {} biens", biens.size());
        } catch (Exception e) {
            logger.error("Error loading biens", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    private void displayBien(Bien bien) {
        currentBien = bien;
        adresseField.setText(bien.getAdresseBien());
        villeField.setText(bien.getVilleBien());
        proprietaireCombo.setValue(bien.getProprietaire());
    }

    @FXML private void handleNew() { clearForm(); bienTable.getSelectionModel().clearSelection(); }
    @FXML private void handleEdit() { 
        if (bienTable.getSelectionModel().getSelectedItem() == null)
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un bien à modifier.");
    }

    @FXML private void handleDelete() {
        Bien selected = bienTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un bien à supprimer.");
            return;
        }
        if (AlertUtil.showDeleteConfirmation(selected.getFullAddress())) {
            try {
                bienService.deleteBien(selected.getIdBien());
                AlertUtil.showSuccess("Bien supprimé avec succès");
                loadBiens();
                clearForm();
            } catch (Exception e) {
                logger.error("Error deleting bien", e);
                AlertUtil.showError("Erreur de suppression", e);
            }
        }
    }

    @FXML private void handleSave() {
        try {
            if (currentBien == null) {
                Bien bien = new Bien();
                setBienFromForm(bien);
                bienService.createBien(bien);
                AlertUtil.showSuccess("Bien créé avec succès");
            } else {
                setBienFromForm(currentBien);
                bienService.updateBien(currentBien);
                AlertUtil.showSuccess("Bien modifié avec succès");
            }
            loadBiens();
            clearForm();
        } catch (IllegalArgumentException e) {
            AlertUtil.showValidationError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving bien", e);
            AlertUtil.showError("Erreur de sauvegarde", e);
        }
    }

    private void setBienFromForm(Bien bien) {
        bien.setAdresseBien(adresseField.getText().trim());
        bien.setVilleBien(villeField.getText().trim());
        bien.setProprietaire(proprietaireCombo.getValue());
    }

    @FXML private void handleCancel() { clearForm(); bienTable.getSelectionModel().clearSelection(); }
    @FXML private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) loadBiens();
        else {
            try {
                List<Bien> results = bienService.searchByAddress(searchTerm);
                bienList.setAll(results);
            } catch (Exception e) {
                logger.error("Error searching biens", e);
                AlertUtil.showError("Erreur de recherche", e);
            }
        }
    }

    @FXML private void handleRefresh() { loadBiens(); clearForm(); }
    private void clearForm() {
        currentBien = null;
        adresseField.clear();
        villeField.clear();
        proprietaireCombo.setValue(null);
    }
}
