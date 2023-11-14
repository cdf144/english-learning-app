package org.nora.dictionary.utils;

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

        FileReader fileReader;
        BufferedReader bufferedReader;

        try {
            fileReader = new FileReader(PATH_HISTORY_FILE);
            bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            System.err.println(e.toString());
            throw e;
        }

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            searchHistory.add(line);
        }

        bufferedReader.close();
        fileReader.close();
    }

    public static void saveSearchHistory() throws IOException{
        FileWriter fileWriter = new FileWriter(PATH_HISTORY_FILE);

        StringBuilder content = new StringBuilder();
        for (String s : searchHistory) {
            content.append(s).append("\n");
        }

        fileWriter.write(content.toString());
        fileWriter.close();
    }
}
