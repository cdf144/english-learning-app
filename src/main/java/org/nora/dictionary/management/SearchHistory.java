package org.nora.dictionary.management;

import org.nora.dictionary.utils.IOFileList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SearchHistory {
    public static List<String> searchHistory;

    public static final String PATH_HISTORY_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "history.txt";

    public static void loadSearchHistory() throws IOException {
        if (searchHistory == null) {
            searchHistory = new ArrayList<>();
        }
        IOFileList.loadFileToList(PATH_HISTORY_FILE, searchHistory);
    }

    public static void saveSearchHistory() throws IOException {
        IOFileList.saveListToFile(PATH_HISTORY_FILE, searchHistory);
    }
}
