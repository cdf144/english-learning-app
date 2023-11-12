package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.nora.dictionary.DictionaryApplication;

public class SearcherController {

    @FXML
    private Button searchButton;

    @FXML
    private Label wordExplainLabel;

    @FXML
    private TextField wordSearchField;

    public void searchWord() {
        if (!wordSearchField.getText().isEmpty()) {
            String word = wordSearchField.getText();
            DictionaryApplication.dictionary.dictionarySearcher(word);
            if (!DictionaryApplication.dictionary.getDictionary().getSearchResultList().isEmpty()) {
                wordExplainLabel.setText(
                        DictionaryApplication.dictionary.getDictionary().getSearchResultList().get(0).getWord_explain()
                );
            } else {
                wordExplainLabel.setText("No word exists!");
            }
        }
    }
}