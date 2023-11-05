package org.nora.dictionary.management;

import org.nora.dictionary.entities.Word;

import java.util.Comparator;

public class WordPrefixComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        int prefixLength = w2.getWord_target().length();
        return w1.getWord_target().substring(0, prefixLength).compareTo(w2.getWord_target());
    }
}
