package org.nora.dictionary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.entities.Word;
import org.nora.dictionary.management.FavoriteWords;
import org.nora.dictionary.management.SearchHistory;
import org.nora.dictionary.utils.TextToSpeech;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SearcherController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> autocompleteList;
    @FXML
    private Label wordTargetLabel;
    @FXML
    private WebView wordExplainView;
    @FXML
    public HTMLEditor wordExplainEditor;
    @FXML
    public ImageView wordToSpeech;
    @FXML
    public ImageView favoriteButton;
    @FXML
    public ImageView saveEditButton;
    @FXML
    public ImageView editButton;
    @FXML
    public ImageView deleteButton;

    private Image starImage;
    private Image starFilledImage;
    private Image editImage;
    private Image cancelImage;

    private final ObservableList<String> autocompleteWordList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autocompleteList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onAutocompleteListClick();
            }
        });

        File star = new File(DictionaryApplication.PATH_ICONS_FOLDER + "star.png");
        starImage = new Image(star.toURI().toString());
        File starFilled = new File(DictionaryApplication.PATH_ICONS_FOLDER + "star_filled.png");
        starFilledImage = new Image(starFilled.toURI().toString());
        File edit = new File(DictionaryApplication.PATH_ICONS_FOLDER + "edit_note.png");
        editImage = new Image(edit.toURI().toString());
        File cancel = new File(DictionaryApplication.PATH_ICONS_FOLDER + "cancel.png");
        cancelImage = new Image(cancel.toURI().toString());

        updateAutocompleteList();
    }

    public void updateAutocompleteList() {
        autocompleteWordList.clear();
        DictionaryApplication.dictionary.dictionarySearcher(searchField.getText());


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

        wordTargetLabel.setText(word.getTarget());

        if (wordExplainEditor.isDisable()) {
            wordExplainView.getEngine().loadContent(word.getExplain(), "text/html");
        } else if (wordExplainView.isDisable()) {
            wordExplainEditor.setHtmlText(word.getExplain());
        }

        SearchHistory.searchHistory.add(word.getTarget());
    }

    public void onWordToSpeechClick() {
        TextToSpeech.speak(wordTargetLabel.getText());
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
        if (wordExplainEditor.isVisible()) {
            disableEditView();
        } else {
            enableEditView();
        }
    }

    public void enableEditView() {
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

    public void disableEditView() {
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
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Delete '" + wordTargetLabel.getText() + "'?",
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL
        );
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            String wordTarget = wordTargetLabel.getText();
            DictionaryApplication.dictionary.removeFromDictionary(wordTarget);
            SearchHistory.removeFromHistory(wordTarget);
            FavoriteWords.removeFavorite(wordTarget);

            showNotification("Remove", "Word removed successfully!");
            updateAutocompleteList();
            wordTargetLabel.setText("");
            wordExplainView.getEngine().loadContent("");
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