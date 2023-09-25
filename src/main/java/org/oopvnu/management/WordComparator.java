package org.oopvnu.management;

import org.oopvnu.entities.Word;

import java.util.Comparator;

/**
 * Comparator for Word class
 */
public class WordComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        return w1.getWord_target().compareTo(w2.getWord_target());
    }
}
