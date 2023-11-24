package org.nora.dictionary;

import org.nora.dictionary.commandline.DictionaryCommandline;
import org.nora.dictionary.management.DictionaryManagement;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryCommandLine {
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());

    public static final String PATH_DICTIONARYMANAGEMENT_LOG = System.getProperty("user.dir")
            + File.separator + "log"
            + File.separator + "logDictionaryCommandLine.log";

    private static final FileHandler logFileHandler;
    static {
        try {
            logFileHandler = new FileHandler(PATH_DICTIONARYMANAGEMENT_LOG, false);
            logFileHandler.setLevel(Level.INFO);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException e) {
            throw new RuntimeException("Could not initalize DictionaryCommandLine log FileHandler!", e);
        }
    }

    DictionaryCommandline dictionaryCommandline;

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }

    /**
     * Bản command line nguyên thuỷ có chức năng nhập
     * từ và giải nghĩa của nó từ command line và sau
     * đó in ra những từ được nhập và giải nghĩa Việt.
     */
    public void dictionaryBasic() {
        dictionaryCommandline.insertFromCommandline();
        dictionaryCommandline.showAllWords();
    }

    /**
     * Driver method.
     * @param args Thông số đầu vào
     */
    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();

        // dictionaryCommandLine.dictionaryBasic();
        System.out.println("Welcome to My Application!");
        dictionaryCommandLine.dictionaryCommandline.dictionaryAdvanced();
    }
}
