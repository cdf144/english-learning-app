package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GoogleTranslateController {

    @FXML
    private TextField inputTextField;

    @FXML
    private TextField resultTextField;

    @FXML
    private Button translateButton;

    @FXML
    private void translateButtonClicked() {
        String inputText = inputTextField.getText();
        resultTextField.setText(inputText);
    }
}