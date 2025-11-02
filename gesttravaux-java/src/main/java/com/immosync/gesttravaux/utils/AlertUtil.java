package com.immosync.gesttravaux.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utility class for displaying JavaFX alerts and dialogs.
 * Provides convenient methods for common alert types.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
public class AlertUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private AlertUtil() {
    }

    /**
     * Shows an information alert.
     *
     * @param title the alert title
     * @param header the alert header
     * @param content the alert content
     */
    public static void showInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows a success alert.
     *
     * @param message the success message
     */
    public static void showSuccess(String message) {
        showInfo("Succès", "Opération réussie", message);
    }

    /**
     * Shows a warning alert.
     *
     * @param title the alert title
     * @param header the alert header
     * @param content the alert content
     */
    public static void showWarning(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an error alert.
     *
     * @param title the alert title
     * @param header the alert header
     * @param content the alert content
     */
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an error alert with an exception.
     *
     * @param title the alert title
     * @param exception the exception
     */
    public static void showError(String title, Exception exception) {
        showError(title, "Une erreur est survenue", exception.getMessage());
    }

    /**
     * Shows a validation error alert.
     *
     * @param message the validation error message
     */
    public static void showValidationError(String message) {
        showError("Erreur de validation", "Données invalides", message);
    }

    /**
     * Shows a confirmation dialog.
     *
     * @param title the dialog title
     * @param header the dialog header
     * @param content the dialog content
     * @return true if user confirmed, false otherwise
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows a delete confirmation dialog.
     *
     * @param itemName the name of the item to delete
     * @return true if user confirmed deletion, false otherwise
     */
    public static boolean showDeleteConfirmation(String itemName) {
        return showConfirmation(
                "Confirmation de suppression",
                "Êtes-vous sûr de vouloir supprimer cet élément ?",
                "Cette action supprimera " + itemName + " de manière permanente."
        );
    }
}
