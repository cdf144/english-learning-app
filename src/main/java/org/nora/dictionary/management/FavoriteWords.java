package org.nora.dictionary.management;

import org.nora.dictionary.utils.IOFileList;

import java.io.*;
import java.util.ArrayList;
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
}
