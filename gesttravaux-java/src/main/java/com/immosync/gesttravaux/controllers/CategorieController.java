package com.immosync.gesttravaux.controllers;

import com.immosync.gesttravaux.models.Categorie;
import com.immosync.gesttravaux.services.CategorieService;
import com.immosync.gesttravaux.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategorieController {

    private static final Logger logger = LoggerFactory.getLogger(CategorieController.class);

    @FXML private TableView<Categorie> categorieTable;
    @FXML private TableColumn<Categorie, Integer> idColumn;
    @FXML private TableColumn<Categorie, String> typeColumn;
    @FXML private TextField typeField;
    @FXML private TextField searchField;

    private final CategorieService categorieService;
    private final ObservableList<Categorie> categorieList;
    private Categorie currentCategorie;

    public CategorieController() {
        this.categorieService = new CategorieService();
        this.categorieList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        loadCategories();
        setupTableSelectionListener();
        clearForm();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idCategorie"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void setupTableSelectionListener() {
        categorieTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayCategorie(newSelection);
                    }
                }
        );
    }

    private void loadCategories() {
        try {
            List<Categorie> categories = categorieService.getAllCategories();
            categorieList.setAll(categories);
            categorieTable.setItems(categorieList);
            logger.info("Loaded {} categories", categories.size());
        } catch (Exception e) {
            logger.error("Error loading categories", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    private void displayCategorie(Categorie categorie) {
        currentCategorie = categorie;
        typeField.setText(categorie.getType());
    }

    @FXML
    private void handleNew() {
        clearForm();
        categorieTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleEdit() {
        Categorie selected = categorieTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner une catégorie à modifier.");
        }
    }

    @FXML
    private void handleDelete() {
        Categorie selected = categorieTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner une catégorie à supprimer.");
            return;
        }

        if (AlertUtil.showDeleteConfirmation(selected.getType())) {
            try {
                categorieService.deleteCategorie(selected.getIdCategorie());
                AlertUtil.showSuccess("Catégorie supprimée avec succès");
                loadCategories();
                clearForm();
            } catch (Exception e) {
                logger.error("Error deleting categorie", e);
                AlertUtil.showError("Erreur de suppression", e);
            }
        }
    }

    @FXML
    private void handleSave() {
        try {
            if (currentCategorie == null) {
                Categorie categorie = new Categorie();
                categorie.setType(typeField.getText().trim());
                categorieService.createCategorie(categorie);
                AlertUtil.showSuccess("Catégorie créée avec succès");
            } else {
                currentCategorie.setType(typeField.getText().trim());
                categorieService.updateCategorie(currentCategorie);
                AlertUtil.showSuccess("Catégorie modifiée avec succès");
            }
            loadCategories();
            clearForm();
        } catch (IllegalArgumentException e) {
            AlertUtil.showValidationError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving categorie", e);
            AlertUtil.showError("Erreur de sauvegarde", e);
        }
    }

    @FXML
    private void handleCancel() {
        clearForm();
        categorieTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadCategories();
        } else {
            try {
                List<Categorie> results = categorieService.searchByType(searchTerm);
                categorieList.setAll(results);
            } catch (Exception e) {
                logger.error("Error searching categories", e);
                AlertUtil.showError("Erreur de recherche", e);
            }
        }
    }

    @FXML
    private void handleRefresh() {
        loadCategories();
        clearForm();
    }

    private void clearForm() {
        currentCategorie = null;
        typeField.clear();
    }
}
