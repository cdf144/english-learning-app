package org.nora.dictionary.entities;

import org.nora.dictionary.management.WordComparator;
import org.nora.dictionary.management.WordPrefixComparator;

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

    public List<Word> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(List<Word> searchResultList) {
        this.searchResultList = searchResultList;
    }

    public void addWord(Word word) {
        wordList.add(word);
    }

    /**
     * Thay vì chỉ thêm Word vào cuối List như addWord,
     * insert Word chèn Word vào vị trí thích hợp trong List.
     * @param word Word để chèn vào
     */
    public void insertWord(Word word) {
        wordList.add(word);

        int i = wordList.size() - 2;
        while (i >= 0 && wordList.get(i).compareTo(word) > 0) {
            wordList.set(i + 1, wordList.get(i));
            i--;
        }

        wordList.set(i + 1, word);
    }

    public void removeWord(Word word) {
        wordList.remove(word);
    }

    /**
     * Tìm và trả lại index của Word cần tìm.
     * @param word Word để tìm
     * @return index của Word cần tìm
     */
    public int findWord(Word word) {
        return Collections.binarySearch(
                wordList,
                word,
                new WordComparator()
        );
    }

    public int findWordWithPrefix(Word word) {
        return Collections.binarySearch(
                wordList,
                word,
                new WordPrefixComparator()
        );
    }

    public void sortWordList() {
        Collections.sort(wordList);
    }
}
