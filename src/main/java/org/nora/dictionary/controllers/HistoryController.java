package org.nora.dictionary.controllers;

import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.entities.Word;
import org.nora.dictionary.management.FavoriteWords;
import org.nora.dictionary.management.SearchHistory;

public class HistoryController extends SearcherController {
    @Override
    public void updateAutocompleteList() {
        autocompleteWordList.clear();
        autocompleteWordList.addAll(SearchHistory.getSearchHistory().reversed());
        autocompleteList.setItems(autocompleteWordList);
    }

    @Override
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

        wordExplainView.getEngine().loadContent(word.getExplain(), "text/html");
    }
}
