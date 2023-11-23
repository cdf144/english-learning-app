package org.nora.dictionary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javazoom.jl.decoder.JavaLayerException;
import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.entities.Word;
import org.nora.dictionary.management.FavoriteWords;
import org.nora.dictionary.management.SearchHistory;
import org.nora.dictionary.utils.GoogleVoiceAPI;
import org.nora.dictionary.utils.TextToSpeech;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearcherController implements Initializable {
    @FXML
    protected TextField searchField;
    @FXML
    protected ListView<String> autocompleteList;
    @FXML
    protected Label wordTargetLabel;
    @FXML
    protected WebView wordExplainView;
    @FXML
    protected HTMLEditor wordExplainEditor;
    @FXML
    protected ImageView wordToSpeechUS;
    @FXML
    protected ImageView wordToSpeechUK;
    @FXML
    protected ImageView favoriteButton;
    @FXML
    protected ImageView saveEditButton;
    @FXML
    protected ImageView editButton;
    @FXML
    protected ImageView deleteButton;

    protected Image starImage;
    protected Image starFilledImage;
    protected Image editImage;
    protected Image cancelImage;

    protected final ObservableList<String> autocompleteWordList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autocompleteList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onAutocompleteListClick();
            }
        });

        File star = new File(DictionaryApplication.PATH_UTILITY_ICONS_FOLDER + "star.png");
        starImage = new Image(star.toURI().toString());
        File starFilled = new File(DictionaryApplication.PATH_UTILITY_ICONS_FOLDER + "star_filled.png");
        starFilledImage = new Image(starFilled.toURI().toString());
        File edit = new File(DictionaryApplication.PATH_UTILITY_ICONS_FOLDER + "edit_note.png");
        editImage = new Image(edit.toURI().toString());
        File cancel = new File(DictionaryApplication.PATH_UTILITY_ICONS_FOLDER + "cancel.png");
        cancelImage = new Image(cancel.toURI().toString());

        updateAutocompleteList();
    }

    public void updateAutocompleteList() {
        autocompleteWordList.clear();
        DictionaryApplication.dictionary.dictionarySearcher(searchField.getText().trim().replaceAll("\\s+", " "));

        for (Word word : DictionaryApplication.dictionary.getSearchResultList()) {
            autocompleteWordList.add(word.getTarget());
        }

        autocompleteList.setItems(autocompleteWordList);
    }

    public void onSearchFieldKeyTyped() {
        updateAutocompleteList();
    }

    public void onAutocompleteListClick() {
        String target = autocompleteList.getSelectionModel().getSelectedItem();
        if (target == null) {
            return;
        }

        if (FavoriteWords.inFavorites(target)) {
            favoriteButton.setImage(starFilledImage);
        } else {
            favoriteButton.setImage(starImage);
        }

        Word word = DictionaryApplication.dictionary.dictionaryLookupWord(target);
        if (word == null) {
            if (wordExplainEditor.isDisable()) {
                wordExplainView.getEngine().loadContent("No word exists!", "text/html");
            } else if (wordExplainView.isDisable()) {
                wordExplainEditor.setHtmlText("No word exists!");
            }
            return;
        }

        wordTargetLabel.setText(word.getTarget());

        if (wordExplainEditor.isDisable()) {
            wordExplainView.getEngine().loadContent(word.getExplain(), "text/html");
        } else if (wordExplainView.isDisable()) {
            wordExplainEditor.setHtmlText(word.getExplain());
        }

        SearchHistory.getSearchHistory().add(word.getTarget());
    }

    public void onWordLabelClick() {
        String word = wordTargetLabel.getText();
        if (word.isEmpty()) {
            showNotification("Copy to clipboard", "No word chosen!");
            return;
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(word);
        clipboard.setContent(content);

        showNotification("Copy to clipboard", "'" + word + "' copied to clipboard!");
    }

    public void onWordToSpeechUSClick() {
        String word = wordTargetLabel.getText();
        if (word.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        try {
            GoogleVoiceAPI.getInstance().playAudio(GoogleVoiceAPI.getInstance().getAudio(wordTargetLabel.getText(),
                    "en-US"));
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(wordTargetLabel.getText());
        }
    }

    public void onWordToSpeechUKClick() {
        String word = wordTargetLabel.getText();
        if (word.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        try {
            GoogleVoiceAPI.getInstance().playAudio(GoogleVoiceAPI.getInstance().getAudio(wordTargetLabel.getText(),
                    "en-UK"));
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(wordTargetLabel.getText());
        }
    }

    public void onFavoriteClick() {
        String target = wordTargetLabel.getText();
        if (target.isEmpty()) {
            showNotification("Favorite", "No word chosen!");
            return;
        }

        if (!FavoriteWords.inFavorites(target)) {
            FavoriteWords.insertFavorite(target);
            favoriteButton.setImage(starFilledImage);
        } else {
            FavoriteWords.removeFavorite(target);
            favoriteButton.setImage(starImage);
        }
    }

    public void onEditButtonClick() {
        if (wordTargetLabel.getText().isEmpty()) {
            showNotification("Edit", "Cannot edit a non-existent word!");
            return;
        }
        if (wordExplainEditor.isVisible()) {
            disableEditView();
        } else {
            enableEditView();
        }
    }

    protected void enableEditView() {
        wordExplainView.setVisible(false);
        wordExplainView.setDisable(true);

        String explain = DictionaryApplication.dictionary.dictionaryLookup(wordTargetLabel.getText());
        wordExplainEditor.setVisible(true);
        wordExplainEditor.setDisable(false);
        wordExplainEditor.setHtmlText(explain);

        saveEditButton.setVisible(true);
        saveEditButton.setDisable(false);

        deleteButton.setVisible(false);
        deleteButton.setDisable(true);

        favoriteButton.setVisible(false);
        favoriteButton.setDisable(true);

        editButton.setImage(cancelImage);
    }

    protected void disableEditView() {
        String explain = DictionaryApplication.dictionary.dictionaryLookup(wordTargetLabel.getText());
        wordExplainView.setVisible(true);
        wordExplainView.setDisable(false);
        wordExplainView.getEngine().loadContent(explain, "text/html");

        wordExplainEditor.setVisible(false);
        wordExplainEditor.setDisable(true);

        saveEditButton.setVisible(false);
        saveEditButton.setDisable(true);

        deleteButton.setVisible(true);
        deleteButton.setDisable(false);

        favoriteButton.setVisible(true);
        favoriteButton.setDisable(false);

        editButton.setImage(editImage);
    }

    public void onSaveEditClick() {
        String newExplain = wordExplainEditor.getHtmlText().replace(" dir=\"ltr\"><head></head" +
                "><body contenteditable=\"true\">", ">");
        newExplain = newExplain.replace("</body>", "");
        newExplain = newExplain.replace("<html>", "");
        newExplain = newExplain.replace("</html>", "");

        DictionaryApplication.dictionary.updateInDictionary(wordTargetLabel.getText(), newExplain);

        showNotification("Edit", "Word edited successfully!");
        disableEditView();
    }

    public void onRemoveClick() {
        String wordTarget = wordTargetLabel.getText();
        if (wordTarget.isEmpty()) {
            showNotification("Delete", "Cannot delete a non-existent word!");
            return;
        }

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Delete '" + wordTargetLabel.getText() + "'?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            DictionaryApplication.dictionary.removeFromDictionary(wordTarget);
            SearchHistory.removeFromHistory(wordTarget);
            FavoriteWords.removeFavorite(wordTarget);

            showNotification("Remove", "Word removed successfully!");
            updateAutocompleteList();
            favoriteButton.setImage(starImage);
            wordTargetLabel.setText("");
            wordExplainView.getEngine().loadContent("");
        }
    }

    protected void showNotification(String title, String content) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle(title);
        notification.setHeaderText(content);
        notification.setContentText(null);
        notification.showAndWait();
    }
}