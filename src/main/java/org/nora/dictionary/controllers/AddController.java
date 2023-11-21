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
        String word = wordField.getText().trim().replaceAll("\\s+", " ");
        if (word.isEmpty()) {
            clearFields();
        } else if (DictionaryApplication.dictionary.wordExist(word)) {
            warningLabel.setText("Warning: Word already exist in Dictionary!");
            warningLabel.setTextFill(Color.RED);
            disableAddInfoFields();
        } else {
            warningLabel.setText("Word does not exist in Dictionary!");
            warningLabel.setTextFill(Color.LIMEGREEN);
            enableAddInfoFields();
            updateExplainFieldTemplate();
        }
    }

    public void disableAddInfoFields() {
        addButton.setDisable(true);
        explainField.setDisable(true);
        pronunciationField.setDisable(true);
        shortDescArea.setDisable(true);
    }

    public void enableAddInfoFields() {
        addButton.setDisable(false);
        explainField.setDisable(false);
        pronunciationField.setDisable(false);
        shortDescArea.setDisable(false);
    }

    public void updateExplainFieldTemplate() {
        String explainTemplate = String.format(
                "<h1>%s</h1><h3><i>/%s/</i></h3><h2></h2><ul><li></li></ul>",
                wordField.getText(),
                pronunciationField.getText());
        explainField.setHtmlText(explainTemplate);
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
        clearFields();
    }

    public void clearFields() {
        wordField.setText("");
        explainField.setHtmlText("");
        pronunciationField.setText("");
        shortDescArea.setText("");
        warningLabel.setText("");

        disableAddInfoFields();
    }

    public void showNotification(String title, String content) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle(title);
        notification.setHeaderText(content);
        notification.setContentText(null);
        notification.showAndWait();
    }
}
