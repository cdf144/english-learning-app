package org.nora.dictionary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.entities.Word;

public class SearcherController {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> autocompleteList;
    @FXML
    private Label wordTarget;
    @FXML
    private WebView wordExplain;

    private final ObservableList<String> autocompleteWordList = FXCollections.observableArrayList();

    public void updateAutocompleteList() {
        autocompleteWordList.clear();

        if (searchField.getText().isEmpty()) {
            for (Word word : DictionaryApplication.dictionary.getDictionary().getWordList()) {
                autocompleteWordList.add(word.getTarget());
            }
        } else {
            DictionaryApplication.dictionary.dictionarySearcher(searchField.getText());
            for (Word word :
                    DictionaryApplication.dictionary.getDictionary().getSearchResultList()) {
                autocompleteWordList.add(word.getTarget());
            }
        }

        autocompleteList.setItems(autocompleteWordList);
    }

    public void searchFieldOnKeyReleased() {
        updateAutocompleteList();
    }
}