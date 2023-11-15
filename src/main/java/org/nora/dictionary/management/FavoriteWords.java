package org.nora.dictionary.management;

import org.nora.dictionary.utils.IOFileList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteWords {
    public static List<String> favoriteWords;

    public static final String PATH_FAVORITES_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "favorites.txt";

    public static void loadFavoriteWords() throws IOException {
        if (favoriteWords == null) {
            favoriteWords = new ArrayList<>();
        }
        IOFileList.loadFileToList(PATH_FAVORITES_FILE, favoriteWords);
    }

    public static void saveFavoriteWords() throws IOException {
        IOFileList.saveListToFile(PATH_FAVORITES_FILE, favoriteWords);
    }

    public static boolean inFavorites(String word) {
        if (favoriteWords.isEmpty()) {
            return false;
        }
        return Collections.binarySearch(favoriteWords, word) >= 0;
    }

    public static void insertFavorite(String word) {
        favoriteWords.add(word);

        int i = favoriteWords.size() - 2;
        while (i >= 0 && favoriteWords.get(i).compareTo(word) > 0) {
            favoriteWords.set(i + 1, favoriteWords.get(i));
            i--;
        }

        favoriteWords.set(i + 1, word);
    }

    public static void removeFavorite(String word) {
        favoriteWords.remove(word);
    }
}
