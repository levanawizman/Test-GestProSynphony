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

public class PrestationController {
    private static final Logger logger = LoggerFactory.getLogger(PrestationController.class);
    @FXML private TableView<Prestation> prestationTable;
    @FXML private TableColumn<Prestation, Integer> idColumn;
    @FXML private TableColumn<Prestation, String> libelleColumn, categorieColumn;
    @FXML private TextField libelleField, searchField;
    @FXML private ComboBox<Categorie> categorieCombo;
    private final PrestationService prestationService;
    private final CategorieService categorieService;
    private final ObservableList<Prestation> prestationList;
    private Prestation currentPrestation;

    public PrestationController() {
        this.prestationService = new PrestationService();
        this.categorieService = new CategorieService();
        this.prestationList = FXCollections.observableArrayList();
    }

    @FXML public void initialize() {
        setupTableColumns();
        loadCategories();
        loadPrestations();
        setupTableSelectionListener();
        clearForm();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idPrestation"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        categorieColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCategorie() != null ? 
                cellData.getValue().getCategorie().getType() : ""
            )
        );
    }

    private void setupTableSelectionListener() {
        prestationTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) displayPrestation(newSelection);
            }
        );
    }

    private void loadCategories() {
        try {
            List<Categorie> categories = categorieService.getAllCategories();
            categorieCombo.setItems(FXCollections.observableArrayList(categories));
        } catch (Exception e) {
            logger.error("Error loading categories", e);
        }
    }

    private void loadPrestations() {
        try {
            List<Prestation> prestations = prestationService.getAllPrestations();
            prestationList.setAll(prestations);
            prestationTable.setItems(prestationList);
            logger.info("Loaded {} prestations", prestations.size());
        } catch (Exception e) {
            logger.error("Error loading prestations", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    private void displayPrestation(Prestation prestation) {
        currentPrestation = prestation;
        libelleField.setText(prestation.getLibelle());
        categorieCombo.setValue(prestation.getCategorie());
    }

    @FXML private void handleNew() { clearForm(); prestationTable.getSelectionModel().clearSelection(); }
    @FXML private void handleEdit() { 
        if (prestationTable.getSelectionModel().getSelectedItem() == null)
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner une prestation à modifier.");
    }

    @FXML private void handleDelete() {
        Prestation selected = prestationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner une prestation à supprimer.");
            return;
        }
        if (AlertUtil.showDeleteConfirmation(selected.getLibelle())) {
            try {
                prestationService.deletePrestation(selected.getIdPrestation());
                AlertUtil.showSuccess("Prestation supprimée avec succès");
                loadPrestations();
                clearForm();
            } catch (Exception e) {
                logger.error("Error deleting prestation", e);
                AlertUtil.showError("Erreur de suppression", e);
            }
        }
    }

    @FXML private void handleSave() {
        try {
            if (currentPrestation == null) {
                Prestation prestation = new Prestation();
                setPrestationFromForm(prestation);
                prestationService.createPrestation(prestation);
                AlertUtil.showSuccess("Prestation créée avec succès");
            } else {
                setPrestationFromForm(currentPrestation);
                prestationService.updatePrestation(currentPrestation);
                AlertUtil.showSuccess("Prestation modifiée avec succès");
            }
            loadPrestations();
            clearForm();
        } catch (IllegalArgumentException e) {
            AlertUtil.showValidationError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving prestation", e);
            AlertUtil.showError("Erreur de sauvegarde", e);
        }
    }

    private void setPrestationFromForm(Prestation prestation) {
        prestation.setLibelle(libelleField.getText().trim());
        prestation.setCategorie(categorieCombo.getValue());
    }

    @FXML private void handleCancel() { clearForm(); prestationTable.getSelectionModel().clearSelection(); }
    @FXML private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) loadPrestations();
        else {
            try {
                List<Prestation> results = prestationService.searchByLibelle(searchTerm);
                prestationList.setAll(results);
            } catch (Exception e) {
                logger.error("Error searching prestations", e);
                AlertUtil.showError("Erreur de recherche", e);
            }
        }
    }

    @FXML private void handleRefresh() { loadPrestations(); clearForm(); }
    private void clearForm() {
        currentPrestation = null;
        libelleField.clear();
        categorieCombo.setValue(null);
    }
}
