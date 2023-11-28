package org.nora.dictionary.controllers;

import javafx.scene.control.Alert;

public class UtilsController {
    public void showNotification(String title, String content) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle(title);
        notification.setHeaderText(content);
        notification.setContentText(null);
        notification.showAndWait();
    }
}
