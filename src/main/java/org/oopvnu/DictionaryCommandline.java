package org.oopvnu;

import org.oopvnu.management.DictionaryManagement;
import org.oopvnu.entities.Word;

public class DictionaryCommandline {
    private final DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    /**
     * In tất cả các từ trong từ điển.
     */
    public void showAllWords() {
        System.out.printf("%-3s | %-15s | %-20s%n", "sNo", "English", "Vietnamese");
        int i = 1;

        dictionaryManagement.getDictionary().sortWordList();

        for (Word word : dictionaryManagement.getListWord()) {
            System.out.printf("%-3s | %-15s | %-20s%n", i++, word.getWord_target(), word.getWord_explain());
        }
    }
}
