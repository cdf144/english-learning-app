package org.nora.dictionary.management;

interface IDictionaryManagement {
    String dictionaryLookup(String wordTarget);
    void dictionarySearcher(String wordTarget); // save Search results to List
    boolean wordExist(String wordTarget);
    void addToDictionary(String wordTarget, String wordExplain);
    boolean removeFromDictionary(String wordTarget); // boolean for if removed successfully
    void updateInDictionary(String wordTarget, String wordExplain);
}
