package org.nora.dictionary.management;

import org.nora.dictionary.entities.Word;

import java.util.Comparator;

/**
 * Comparator for Word class
 */
public class WordComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        return w1.getTarget().compareTo(w2.getTarget());
    }
}
