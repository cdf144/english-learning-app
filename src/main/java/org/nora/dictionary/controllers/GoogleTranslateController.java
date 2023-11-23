package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javazoom.jl.decoder.JavaLayerException;
import org.nora.dictionary.utils.GoogleTranslateAPI;
import org.nora.dictionary.utils.GoogleVoiceAPI;
import org.nora.dictionary.utils.TextToSpeech;

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
    @FXML
    private ImageView speakButtonSrc;
    @FXML
    private ImageView speakButtonDest;
    @FXML
    private Label charCountLabel;

    String[] sourceLangs = {"English", "Vietnamese", "Auto"};
    String[] destLangs = {"English", "Vietnamese"};
    GoogleTranslateAPI.LANGUAGE sourceLang;
    GoogleTranslateAPI.LANGUAGE destLang;

    private static final int CHAR_LIMIT = 9999;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sourceLangChoiceBox.getItems().addAll(sourceLangs);
        sourceLangChoiceBox.setValue(sourceLangs[0]);

        destLangChoiceBox.getItems().addAll(destLangs);
        destLangChoiceBox.setValue(destLangs[1]);

        sourceLang = GoogleTranslateAPI.LANGUAGE.ENGLISH;
        destLang = GoogleTranslateAPI.LANGUAGE.VIETNAMESE;
        updateCharCountLabel();
    }

    public void onInputTextAreaTyped() {
        updateCharCountLabel();
    }

    private void updateCharCountLabel() {
        String inputText = inputTextArea.getText();

        if (inputText.length() > 9999) {
            showNotification("Warning!", "Translate word limit reached!");
            inputText = inputText.substring(0, 9999);
            inputTextArea.setText(inputText);
        }

        charCountLabel.setText(inputText.length() + " / " + CHAR_LIMIT);
    }

    @FXML
    private void translateButtonClicked() {
        String inputText = inputTextArea.getText();

        if (sourceLangChoiceBox.getValue().equals(sourceLangs[0])) {
            sourceLang = GoogleTranslateAPI.LANGUAGE.ENGLISH;
        } else if (sourceLangChoiceBox.getValue().equals(sourceLangs[1])) {
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
            resultTextArea.setText("ERROR: Cannot connect to Google Translate.");
        }
    }

    public void onSpeakButtonSrcClick() {
        String input = inputTextArea.getText();
        if (input.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        String languageOutput = sourceLangChoiceBox.getValue().equals(sourceLangs[1])
                ? "vi-VN"
                : "en-US";

        try {
            GoogleVoiceAPI.getInstance().playAudio(GoogleVoiceAPI.getInstance().getAudio(inputTextArea.getText(),
                    languageOutput));
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(inputTextArea.getText());
        }
    }

    public void onSpeakButtonDestClick() {
        String input = resultTextArea.getText();
        if (input.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        String languageOutput = destLangChoiceBox.getValue().equals(destLangs[1])
                                ? "vi-VN"
                                : "en-US";

        try {
            GoogleVoiceAPI.getInstance().playAudio(
                    GoogleVoiceAPI.getInstance().getAudio(resultTextArea.getText(),
                    languageOutput)
            );
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(resultTextArea.getText());
        }
    }

    public void showNotification(String title, String content) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle(title);
        notification.setHeaderText(content);
        notification.setContentText(null);
        notification.showAndWait();
    }
}