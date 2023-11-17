package org.nora.dictionary.management;

import org.nora.dictionary.utils.IOFileList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchHistory {
    private static List<String> searchHistory;

    public static final String PATH_HISTORY_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "history.txt";

    public static List<String> getSearchHistory() {
        return searchHistory;
    }

    public static void loadSearchHistory() throws IOException {
        if (searchHistory == null) {
            searchHistory = new ArrayList<>();
        }
        IOFileList.loadFileToList(PATH_HISTORY_FILE, searchHistory);
    }

    public static void saveSearchHistory() throws IOException {
        IOFileList.saveListToFile(PATH_HISTORY_FILE, searchHistory);
    }

    public static void removeFromHistory(String word) {
        searchHistory.removeAll(Collections.singleton(word));
    }
}
