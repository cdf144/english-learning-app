package org.nora.dictionary.management;

import org.nora.dictionary.entities.Word;

import java.util.Comparator;

public class WordPrefixComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        int prefixLength = Math.min(w2.getTarget().length(), w1.getTarget().length());
        return w1.getTarget().substring(0, prefixLength).compareTo(w2.getTarget());
    }
}
