package org.nora.dictionary.entities;

import org.nora.dictionary.management.WordComparator;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    protected List<Word> wordList;

    protected List<Word> resWordList;

    public Dictionary() {
        wordList = new ArrayList<>();
        resWordList = new ArrayList<>();
    }

    public Dictionary(List<Word> wordList) {
        this.wordList = wordList;
        resWordList = new ArrayList<>();
    }

    public void addWord(Word word) {
        wordList.add(word);
    }

    public void removeWord(Word word) {
        wordList.remove(word);
    }

    public List<Word> getResWordList() {
        return resWordList;
    }

    public void setResWordList(List<Word> resWordList) {
        this.resWordList = resWordList;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void sortWordList() {
        wordList.sort(new WordComparator());
    }
}
