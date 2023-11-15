package org.nora.dictionary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.entities.Word;
import org.nora.dictionary.management.SearchHistory;
import org.nora.dictionary.utils.TextToSpeech;

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

    private final ObservableList<String> autocompleteWordList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autocompleteList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                autocompleteListOnClick();
            }
        });

        updateAutocompleteList();
    }

    public void updateAutocompleteList() {
        autocompleteWordList.clear();

        if (searchField.getText().isEmpty()) {
            for (Word word : DictionaryApplication.dictionary.getDictionary().getWordList()) {
                autocompleteWordList.add(word.getTarget());
            }
        } else {
            DictionaryApplication.dictionary.dictionarySearcher(searchField.getText());
            for (Word word :
                    DictionaryApplication.dictionary.getSearchResultList()) {
                autocompleteWordList.add(word.getTarget());
            }
        }

        autocompleteList.setItems(autocompleteWordList);
    }

    public void searchFieldOnKeyTyped() {
        updateAutocompleteList();
    }

    public void autocompleteListOnClick() {
        String target = autocompleteList.getSelectionModel().getSelectedItem();
        if (target == null) {
            return;
        }

        int index =
                DictionaryApplication.dictionary.getDictionary().findWord(
                        new Word(target.toLowerCase(), null)
                );
        Word word = DictionaryApplication.dictionary.getDictionary().getWordList().get(index);

        wordTargetLabel.setText(word.getTarget());
        wordExplainView.getEngine().loadContent(word.getExplain(), "text/html");
        SearchHistory.searchHistory.add(word.getTarget());
    }

    public void onWordToSpeechClick() {
        TextToSpeech.speak(wordTargetLabel.getText());
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
    }

    public void onSaveEditClick() {
        String newExplain = wordExplainEditor.getHtmlText().replace(" dir=\"ltr\"><head></head" +
                "><body contenteditable=\"true\">", ">");
        newExplain = newExplain.replace("</body>", "");
        DictionaryApplication.dictionary.updateInDictionary(wordTargetLabel.getText(), newExplain);

        showNotification("Edit", "Word edited successfully!");
        disableEditView();
    }

    public void onRemoveClick() {
        DictionaryApplication.dictionary.removeFromDictionary(wordTargetLabel.getText());
        showNotification("Remove", "Word removed successfully!");
        updateAutocompleteList();
        wordTargetLabel.setText("");
        wordExplainView.getEngine().loadContent("");
    }

    public void showNotification(String title, String content) {
        Alert notification = new Alert(Alert.AlertType.INFORMATION);
        notification.setTitle(title);
        notification.setHeaderText(content);
        notification.setContentText(null);
        notification.showAndWait();
    }
}