package org.nora.dictionary.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadFileNames {
    public static List<String> filePaths;

    public static final String PATH_GUESS_GAME_IMAGE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "GuessGameImage";

    public static void listFilesInFolder(File folder) {
        if (filePaths == null) {
            filePaths = new ArrayList<>();
        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            filePaths.add(file.getAbsolutePath());
        }
    }

    public static void listFilesInFolder(File folder, List<String> pathList) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            pathList.add(file.getAbsolutePath());
        }
    }
}
