package com.immosync.gesttravaux.controllers;

import com.immosync.gesttravaux.models.Categorie;
import com.immosync.gesttravaux.models.Entrepreneur;
import com.immosync.gesttravaux.services.CategorieService;
import com.immosync.gesttravaux.services.EntrepreneurService;
import com.immosync.gesttravaux.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller for Entrepreneur management.
 * Handles CRUD operations and many-to-many category relationships.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class EntrepreneurController {

    private static final Logger logger = LoggerFactory.getLogger(EntrepreneurController.class);

    @FXML private TableView<Entrepreneur> entrepreneurTable;
    @FXML private TableColumn<Entrepreneur, Integer> idColumn;
    @FXML private TableColumn<Entrepreneur, String> nomColumn;
    @FXML private TableColumn<Entrepreneur, String> prenomColumn;
    @FXML private TableColumn<Entrepreneur, String> emailColumn;
    @FXML private TableColumn<Entrepreneur, String> telColumn;
    @FXML private TableColumn<Entrepreneur, String> villeColumn;

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telField;
    @FXML private TextField villeField;
    @FXML private TextField searchField;
    @FXML private ListView<Categorie> categoriesListView;

    private final EntrepreneurService entrepreneurService;
    private final CategorieService categorieService;
    private final ObservableList<Entrepreneur> entrepreneurList;
    private Entrepreneur currentEntrepreneur;

    /**
     * Constructor.
     */
    public EntrepreneurController() {
        this.entrepreneurService = new EntrepreneurService();
        this.categorieService = new CategorieService();
        this.entrepreneurList = FXCollections.observableArrayList();
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadEntrepreneurs();
        setupTableSelectionListener();
        clearForm();
    }

    /**
     * Sets up table columns.
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEntrepreneur"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomEntrepreneur"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenomEntrepreneur"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailEntrepreneur"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telEntrepreneur"));
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("villeDeploiement"));
    }

    /**
     * Sets up table selection listener.
     */
    private void setupTableSelectionListener() {
        entrepreneurTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayEntrepreneur(newSelection);
                    }
                }
        );
    }

    /**
     * Loads all entrepreneurs.
     */
    private void loadEntrepreneurs() {
        try {
            List<Entrepreneur> entrepreneurs = entrepreneurService.getAllEntrepreneurs();
            entrepreneurList.setAll(entrepreneurs);
            entrepreneurTable.setItems(entrepreneurList);
            logger.info("Loaded {} entrepreneurs", entrepreneurs.size());
        } catch (Exception e) {
            logger.error("Error loading entrepreneurs", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    /**
     * Displays entrepreneur details in form.
     */
    private void displayEntrepreneur(Entrepreneur entrepreneur) {
        currentEntrepreneur = entrepreneur;
        nomField.setText(entrepreneur.getNomEntrepreneur());
        prenomField.setText(entrepreneur.getPrenomEntrepreneur());
        emailField.setText(entrepreneur.getEmailEntrepreneur());
        telField.setText(entrepreneur.getTelEntrepreneur());
        villeField.setText(entrepreneur.getVilleDeploiement());

        // Display categories
        categoriesListView.setItems(
                FXCollections.observableArrayList(entrepreneur.getCategories())
        );
    }

    /**
     * Handles new entrepreneur creation.
     */
    @FXML
    private void handleNew() {
        clearForm();
        entrepreneurTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles entrepreneur edit.
     */
    @FXML
    private void handleEdit() {
        Entrepreneur selected = entrepreneurTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un entrepreneur à modifier.");
        }
    }

    /**
     * Handles entrepreneur deletion.
     */
    @FXML
    private void handleDelete() {
        Entrepreneur selected = entrepreneurTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("Attention", "Aucune sélection", "Veuillez sélectionner un entrepreneur à supprimer.");
            return;
        }

        if (AlertUtil.showDeleteConfirmation(selected.getFullName())) {
            try {
                entrepreneurService.deleteEntrepreneur(selected.getIdEntrepreneur());
                AlertUtil.showSuccess("Entrepreneur supprimé avec succès");
                loadEntrepreneurs();
                clearForm();
            } catch (Exception e) {
                logger.error("Error deleting entrepreneur", e);
                AlertUtil.showError("Erreur de suppression", e);
            }
        }
    }

    /**
     * Handles entrepreneur save.
     */
    @FXML
    private void handleSave() {
        try {
            if (currentEntrepreneur == null) {
                // Create new entrepreneur
                Entrepreneur entrepreneur = new Entrepreneur();
                setEntrepreneurFromForm(entrepreneur);
                entrepreneurService.createEntrepreneur(entrepreneur);
                AlertUtil.showSuccess("Entrepreneur créé avec succès");
            } else {
                // Update existing entrepreneur
                setEntrepreneurFromForm(currentEntrepreneur);
                entrepreneurService.updateEntrepreneur(currentEntrepreneur);
                AlertUtil.showSuccess("Entrepreneur modifié avec succès");
            }
            loadEntrepreneurs();
            clearForm();
        } catch (IllegalArgumentException e) {
            AlertUtil.showValidationError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving entrepreneur", e);
            AlertUtil.showError("Erreur de sauvegarde", e);
        }
    }

    /**
     * Sets entrepreneur properties from form fields.
     */
    private void setEntrepreneurFromForm(Entrepreneur entrepreneur) {
        entrepreneur.setNomEntrepreneur(nomField.getText().trim());
        entrepreneur.setPrenomEntrepreneur(prenomField.getText().trim());
        entrepreneur.setEmailEntrepreneur(emailField.getText().trim());
        entrepreneur.setTelEntrepreneur(telField.getText().trim());
        entrepreneur.setVilleDeploiement(villeField.getText().trim());
    }

    /**
     * Handles form cancellation.
     */
    @FXML
    private void handleCancel() {
        clearForm();
        entrepreneurTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles search.
     */
    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadEntrepreneurs();
        } else {
            try {
                List<Entrepreneur> results = entrepreneurService.searchByName(searchTerm);
                entrepreneurList.setAll(results);
            } catch (Exception e) {
                logger.error("Error searching entrepreneurs", e);
                AlertUtil.showError("Erreur de recherche", e);
            }
        }
    }

    /**
     * Handles refresh.
     */
    @FXML
    private void handleRefresh() {
        loadEntrepreneurs();
        clearForm();
    }

    /**
     * Handles category management.
     */
    @FXML
    private void handleManageCategories() {
        if (currentEntrepreneur == null) {
            AlertUtil.showWarning("Attention", "Aucun entrepreneur sélectionné",
                    "Veuillez sélectionner un entrepreneur pour gérer ses catégories.");
            return;
        }

        // Open category selection dialog
        try {
            List<Categorie> allCategories = categorieService.getAllCategories();

            Dialog<List<Categorie>> dialog = new Dialog<>();
            dialog.setTitle("Gérer les Catégories");
            dialog.setHeaderText("Sélectionnez les catégories pour " + currentEntrepreneur.getFullName());

            ListView<Categorie> categoryListView = new ListView<>();
            categoryListView.getItems().setAll(allCategories);
            categoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // Select current categories
            for (Categorie cat : currentEntrepreneur.getCategories()) {
                categoryListView.getSelectionModel().select(cat);
            }

            dialog.getDialogPane().setContent(categoryListView);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    return categoryListView.getSelectionModel().getSelectedItems();
                }
                return null;
            });

            dialog.showAndWait().ifPresent(selectedCategories -> {
                currentEntrepreneur.getCategories().clear();
                currentEntrepreneur.getCategories().addAll(selectedCategories);
                categoriesListView.setItems(FXCollections.observableArrayList(selectedCategories));
            });

        } catch (Exception e) {
            logger.error("Error managing categories", e);
            AlertUtil.showError("Erreur", e);
        }
    }

    /**
     * Clears the form.
     */
    private void clearForm() {
        currentEntrepreneur = null;
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        telField.clear();
        villeField.clear();
        categoriesListView.getItems().clear();
    }
}
