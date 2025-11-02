package com.immosync.gesttravaux.controllers;

import com.immosync.gesttravaux.models.Proprietaire;
import com.immosync.gesttravaux.services.ProprietaireService;
import com.immosync.gesttravaux.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class ProprietaireController {
    private static final Logger logger = LoggerFactory.getLogger(ProprietaireController.class);
    @FXML private TableView<Proprietaire> proprietaireTable;
    @FXML private TableColumn<Proprietaire, Integer> idColumn;
    @FXML private TableColumn<Proprietaire, String> nomColumn;
    @FXML private TableColumn<Proprietaire, String> prenomColumn;
    @FXML private TableColumn<Proprietaire, String> emailColumn;
    @FXML private TableColumn<Proprietaire, String> telColumn;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telField;
    @FXML private TextField searchField;
    private final ProprietaireService proprietaireService;
    private final ObservableList<Proprietaire> proprietaireList;
    private Proprietaire currentProprietaire;

    public ProprietaireController() {
        this.proprietaireService = new ProprietaireService();
        this.proprietaireList = FXCollections.observableArrayList();
    }

    @FXML public void initialize() {
        setupTableColumns();
        loadProprietaires();
        setupTableSelectionListener();
        clearForm();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProprietaire"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomProprietaire"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenomProprietaire"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailProprietaire"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telProprietaire"));
    }

    private void setupTableSelectionListener() {
        proprietaireTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) displayProprietaire(newSelection);
                }
        );
    }

    private void loadProprietaires() {
        try {
            List<Proprietaire> proprietaires = proprietaireService.getAllProprietaires();
            proprietaireList.setAll(proprietaires);
            proprietaireTable.setItems(proprietaireList);
            logger.info("Loaded {} proprietaires", proprietaires.size());
        } catch (Exception e) {
            logger.error("Error loading proprietaires", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    private void displayProprietaire(Proprietaire proprietaire) {
        currentProprietaire = proprietaire;
        nomField.setText(proprietaire.getNomProprietaire());
        prenomField.setText(proprietaire.getPrenomProprietaire());
        emailField.setText(proprietaire.getEmailProprietaire());
        telField.setText(proprietaire.getTelProprietaire());
    }

    @FXML private void handleNew() {
        clearForm();
        proprietaireTable.getSelectionModel().clearSelection();
    }

    @FXML private void handleEdit() {
        Proprietaire selected = proprietaireTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un propriétaire à modifier.");
        }
    }

    @FXML private void handleDelete() {
        Proprietaire selected = proprietaireTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un propriétaire à supprimer.");
            return;
        }
        if (AlertUtil.showDeleteConfirmation(selected.getFullName())) {
            try {
                proprietaireService.deleteProprietaire(selected.getIdProprietaire());
                AlertUtil.showSuccess("Propriétaire supprimé avec succès");
                loadProprietaires();
                clearForm();
            } catch (Exception e) {
                logger.error("Error deleting proprietaire", e);
                AlertUtil.showError("Erreur de suppression", e);
            }
        }
    }

    @FXML private void handleSave() {
        try {
            if (currentProprietaire == null) {
                Proprietaire proprietaire = new Proprietaire();
                setProprietaireFromForm(proprietaire);
                proprietaireService.createProprietaire(proprietaire);
                AlertUtil.showSuccess("Propriétaire créé avec succès");
            } else {
                setProprietaireFromForm(currentProprietaire);
                proprietaireService.updateProprietaire(currentProprietaire);
                AlertUtil.showSuccess("Propriétaire modifié avec succès");
            }
            loadProprietaires();
            clearForm();
        } catch (IllegalArgumentException e) {
            AlertUtil.showValidationError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving proprietaire", e);
            AlertUtil.showError("Erreur de sauvegarde", e);
        }
    }

    private void setProprietaireFromForm(Proprietaire proprietaire) {
        proprietaire.setNomProprietaire(nomField.getText().trim());
        proprietaire.setPrenomProprietaire(prenomField.getText().trim());
        proprietaire.setEmailProprietaire(emailField.getText().trim());
        proprietaire.setTelProprietaire(telField.getText().trim());
    }

    @FXML private void handleCancel() {
        clearForm();
        proprietaireTable.getSelectionModel().clearSelection();
    }

    @FXML private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadProprietaires();
        } else {
            try {
                List<Proprietaire> results = proprietaireService.searchByName(searchTerm);
                proprietaireList.setAll(results);
            } catch (Exception e) {
                logger.error("Error searching proprietaires", e);
                AlertUtil.showError("Erreur de recherche", e);
            }
        }
    }

    @FXML private void handleRefresh() {
        loadProprietaires();
        clearForm();
    }

    private void clearForm() {
        currentProprietaire = null;
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        telField.clear();
    }
}
