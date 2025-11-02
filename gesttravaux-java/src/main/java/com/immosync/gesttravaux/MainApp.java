package com.immosync.gesttravaux;

import com.immosync.gesttravaux.config.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main JavaFX Application class for GestTravaux Pro.
 * Entry point for the application.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
    private static Stage primaryStage;

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage
     */
    @Override
    public void start(Stage stage) {
        try {
            logger.info("Starting GestTravaux Pro application...");
            primaryStage = stage;

            // Initialize Hibernate
            HibernateUtil.getSessionFactory();
            logger.info("Hibernate SessionFactory initialized");

            // Load main FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

            // Create scene
            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            // Setup stage
            stage.setTitle("GestTravaux Pro - Gestion de Travaux Immobiliers");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

            logger.info("Application started successfully");

        } catch (Exception e) {
            logger.error("Error starting application", e);
            e.printStackTrace();
        }
    }

    /**
     * Stops the application and cleans up resources.
     */
    @Override
    public void stop() {
        logger.info("Stopping application...");
        HibernateUtil.shutdown();
        logger.info("Application stopped");
    }

    /**
     * Gets the primary stage.
     *
     * @return the primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Main method - launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
