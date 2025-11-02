package com.immosync.gesttravaux.controllers;

import com.immosync.gesttravaux.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main controller for the application.
 * Manages navigation between different modules.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Label statusLabel;

    @FXML
    private Label dbStatusLabel;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        logger.info("Main controller initialized");
        updateStatus("Application prête");
    }

    /**
     * Shows the Entrepreneurs view.
     */
    @FXML
    private void showEntrepreneurs() {
        loadView("Entrepreneurs", "/fxml/Entrepreneurs.fxml");
    }

    /**
     * Shows the Categories view.
     */
    @FXML
    private void showCategories() {
        loadView("Catégories", "/fxml/Categories.fxml");
    }

    /**
     * Shows the Proprietaires view.
     */
    @FXML
    private void showProprietaires() {
        loadView("Propriétaires", "/fxml/Proprietaires.fxml");
    }

    /**
     * Shows the Biens view.
     */
    @FXML
    private void showBiens() {
        loadView("Biens", "/fxml/Biens.fxml");
    }

    /**
     * Shows the Prestations view.
     */
    @FXML
    private void showPrestations() {
        loadView("Prestations", "/fxml/Prestations.fxml");
    }

    /**
     * Shows the Chantiers view.
     */
    @FXML
    private void showChantiers() {
        loadView("Chantiers", "/fxml/Chantiers.fxml");
    }

    /**
     * Shows the About dialog.
     */
    @FXML
    private void showAbout() {
        AlertUtil.showInfo(
                "À propos",
                "GestTravaux Pro v1.0",
                "Application de gestion de travaux immobiliers\n\n" +
                "Développé pour ImmοSync\n" +
                "© 2025 - Tous droits réservés"
        );
    }

    /**
     * Handles application exit.
     */
    @FXML
    private void handleExit() {
        if (AlertUtil.showConfirmation(
                "Quitter",
                "Voulez-vous vraiment quitter l'application ?",
                "Toutes les données non sauvegardées seront perdues.")) {
            System.exit(0);
        }
    }

    /**
     * Loads a view in a new tab.
     *
     * @param tabName the tab name
     * @param fxmlPath the FXML file path
     */
    private void loadView(String tabName, String fxmlPath) {
        try {
            // Check if tab already exists
            for (Tab tab : mainTabPane.getTabs()) {
                if (tab.getText().equals(tabName)) {
                    mainTabPane.getSelectionModel().select(tab);
                    return;
                }
            }

            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Create new tab
            Tab tab = new Tab(tabName);
            tab.setContent(root);
            mainTabPane.getTabs().add(tab);
            mainTabPane.getSelectionModel().select(tab);

            updateStatus("Module " + tabName + " chargé");
            logger.info("Loaded view: {}", tabName);

        } catch (Exception e) {
            logger.error("Error loading view: {}", tabName, e);
            AlertUtil.showError("Erreur", "Impossible de charger le module " + tabName, e.getMessage());
        }
    }

    /**
     * Updates the status label.
     *
     * @param status the status message
     */
    private void updateStatus(String status) {
        statusLabel.setText(status);
    }
}
