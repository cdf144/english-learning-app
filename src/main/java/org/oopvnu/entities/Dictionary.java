package org.oopvnu.entities;

import org.oopvnu.management.WordComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private List<Word> wordList;

    public Dictionary() {
        wordList = new ArrayList<>();
    }

    public void addWord(Word word) {
        wordList.add(word);
    }

    public void removeWord(Word word) {
        wordList.remove(word);
    }

    public List<Word> listWord() {
        Collections.sort(wordList, new WordComparator());
        return wordList;
    }
}
