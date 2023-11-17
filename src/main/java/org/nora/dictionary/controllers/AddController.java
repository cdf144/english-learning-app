package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import org.nora.dictionary.DictionaryApplication;

public class AddController {
    @FXML
    private TextArea wordField;
    @FXML
    private HTMLEditor explainField;
    @FXML
    private TextField pronunciationField;
    @FXML
    private TextArea shortDescArea;
    @FXML
    private Label warningLabel;
    @FXML
    private Button addButton;

    public void onWordFieldType() {
        String word = wordField.getText();
        if (DictionaryApplication.dictionary.wordExist(word)) {
            warningLabel.setText("Warning: Word already exist in Dictionary!");
            warningLabel.setTextFill(Color.RED);
            addButton.setDisable(true);
            explainField.setDisable(true);
            pronunciationField.setDisable(true);
            shortDescArea.setDisable(true);
        } else {
            warningLabel.setText("Word does not exist in Dictionary!");
            warningLabel.setTextFill(Color.LIMEGREEN);
            addButton.setDisable(false);
            explainField.setDisable(false);
            pronunciationField.setDisable(false);
            shortDescArea.setDisable(false);
        }
    }

    public void onAddButtonClick() {
        String target = wordField.getText();
        String explain = explainField.getHtmlText().replace(" dir=\"ltr\"><head></head" +
                "><body contenteditable=\"true\">", ">");
        explain = explain.replace("</body>", "");
        explain = explain.replace("<html>", "");
        explain = explain.replace("</html>", "");

        String shortDesc = shortDescArea.getText();
        String pronunciation = pronunciationField.getText();

        DictionaryApplication.dictionary.addToDictionary(target, explain, shortDesc, pronunciation);

        showNotification("Add to Dictionary", "Added '" + target + "' to Dictionary successfully");
        clear();
    }

    public void clear() {
        wordField.setText("");
        explainField.setHtmlText("");
        pronunciationField.setText("");
        shortDescArea.setText("");
        warningLabel.setText("");

        addButton.setDisable(true);
        explainField.setDisable(true);
        pronunciationField.setDisable(true);
        shortDescArea.setDisable(true);
    }

    public void showNotification(String title, String content) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle(title);
        notification.setHeaderText(content);
        notification.setContentText(null);
        notification.showAndWait();
    }
}