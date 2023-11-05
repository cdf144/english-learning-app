package org.nora.dictionary.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    protected List<Word> wordList;
    protected List<Word> searchResultList;

    public Dictionary() {
        wordList = new ArrayList<>();
        searchResultList = new ArrayList<>();
    }

    public Dictionary(ArrayList<Word> wordList) {
        this.wordList = wordList;
        searchResultList = new ArrayList<>();
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public void setSearchResultList(List<Word> searchResultList) {
        this.searchResultList = searchResultList;
    }

    public List<Word> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(ArrayList<Word> searchResultList) {
        this.searchResultList = searchResultList;
    }

    public void addWord(Word word) {
        wordList.add(word);
    }

    public void removeWord(Word word) {
        wordList.remove(word);
    }

    public void sortWordList() {
        Collections.sort(wordList);
    }
}
