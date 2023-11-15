package org.nora.dictionary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private ImageView wordToSpeech;

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
}