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

    public void addWord(Word word) {
        wordList.add(word);
    }

    public void removeWord(Word word) {
        wordList.remove(word);
    }

    /**
     * Trả về wordList
     * @return wordList
     */
    public List<Word> getWordList() {
        return wordList;
    }

    /**
     * Sắp xếp Word trong wordList.
     */
    public void sortWordList() {
        wordList.sort(new WordComparator());
    }
}
