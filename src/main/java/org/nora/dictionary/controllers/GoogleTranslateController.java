package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import org.nora.dictionary.utils.GoogleTranslateAPI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GoogleTranslateController implements Initializable {
    @FXML
    private ChoiceBox<String> sourceLangChoiceBox;
    @FXML
    private ChoiceBox<String> destLangChoiceBox;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea resultTextArea;
    @FXML
    private Button translateButton;

    String[] sourceLangs = {"English", "Vietnamese", "Auto"};
    String[] destLangs = {"English", "Vietnamese"};
    GoogleTranslateAPI.LANGUAGE sourceLang;
    GoogleTranslateAPI.LANGUAGE destLang;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sourceLangChoiceBox.getItems().addAll(sourceLangs);
        sourceLangChoiceBox.setValue(sourceLangs[0]);

        destLangChoiceBox.getItems().addAll(destLangs);
        destLangChoiceBox.setValue(destLangs[1]);

        sourceLang = GoogleTranslateAPI.LANGUAGE.ENGLISH;
        destLang = GoogleTranslateAPI.LANGUAGE.VIETNAMESE;
    }

    @FXML
    private void translateButtonClicked() {
        String inputText = inputTextArea.getText();

        if (sourceLangChoiceBox.getValue().equals(sourceLangs[0])) {
            sourceLang = GoogleTranslateAPI.LANGUAGE.ENGLISH;
        } else if (sourceLangChoiceBox.getValue().equals(sourceLangs[0])) {
            sourceLang = GoogleTranslateAPI.LANGUAGE.VIETNAMESE;
        } else {
            sourceLang = GoogleTranslateAPI.LANGUAGE.AUTO;
        }

        destLang = destLangChoiceBox.getValue().equals(destLangs[0])
                ? GoogleTranslateAPI.LANGUAGE.ENGLISH
                : GoogleTranslateAPI.LANGUAGE.VIETNAMESE;

        try {
            resultTextArea.setText(GoogleTranslateAPI.translate(
                    inputText, sourceLang, destLang
            ));
        } catch (IOException e) {
            resultTextArea.setText("Error, cannot connect to Google Translate.");
        }
    }
}