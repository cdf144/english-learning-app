package org.nora.dictionary.management;

import org.nora.dictionary.entities.Word;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryCommandline extends DictionaryManagement {
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    public static final String PATH_DICTIONARYCOMMANDLINE_LOG = "log"
            + File.separator + "logDictionaryAdvanced.log";

    private static final FileHandler logFileHandler;
    static {
        try {
            logFileHandler = new FileHandler(PATH_DICTIONARYCOMMANDLINE_LOG, false);
            logFileHandler.setLevel(Level.INFO);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException e) {
            throw new RuntimeException("Could not initalize DictionaryCommandline log FileHandler!", e);
        }
    }

    public DictionaryCommandline() {
        super();
    }

    /**
     * Nhập từ mới tiếng Anh và nghĩa tiếng Việt
     * từ command line vào dictionary.
     */
    public void insertFromCommandline() {
        System.out.print("Nhập vào số lượng từ vựng (Word): ");
        int wordNumber = scanner.nextInt();
        scanner.nextLine(); // sang dòng tiếp theo

        for (int i = 1; i <= wordNumber; i++) {
            System.out.println("Từ thứ " + i);

            System.out.print("Nhập từ tiếng Anh: ");
            String word_target = scanner.nextLine();

            System.out.print("Nhập nghĩa tiếng Việt: ");
            String word_explain = scanner.nextLine();

            dictionary.addWord(new Word(word_target.toLowerCase(), word_explain));
            System.out.println();
        }

        dictionary.sortWordList();
    }

    private void printMenu() {
        System.out.println("  [0] Exit");
        System.out.println("  [1] Add");
        System.out.println("  [2] Remove");
        System.out.println("  [3] Update");
        System.out.println("  [4] Display");
        System.out.println("  [5] Lookup");
        System.out.println("  [6] Search");
        System.out.println("  [7] Game");
        System.out.println("  [8] Import from file");
        System.out.println("  [9] Export to file");
    }

    public void dictionaryAdvanced() throws IOException {
        printMenu();
        boolean exit = false;
        while (!exit) {
            System.out.print("Your action: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("PLEASE ENTER AN INTEGER!");
                scanner.next();
                continue;
            }

            String wordTarget = "";
            String wordExplain = "";
            switch (choice) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("Add: Enter new word_target:");
                    wordTarget = scanWord(wordTarget);

                    if (wordExist(wordTarget)) {
                        System.out.println("Word already exists!");
                    } else {
                        System.out.println("Add: Enter word_explain: ");
                        wordExplain = scanWord(wordExplain);
                        addToDictionary(wordTarget, wordExplain);
                        System.out.println("ADDED!");
                    }

                    break;
                case 2:
                    removeFromDictionary();
                    break;
                case 3:
                    updateInDictionary();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    System.out.println("Lookup: Enter your word target:");
                    wordTarget = scanWord(wordTarget);
                    System.out.println(dictionaryLookup(wordTarget));
                    break;
                case 6:
                    System.out.println("Searcher: Enter your (partial) word:");
                    wordTarget = scanWord(wordTarget);
                    dictionarySearcher(wordTarget);
                    printSearchResult();
                    break;
                case 7:
                    System.out.println("lam gi đa co game ma choi :)))");
                    break;
                case 8:
                    try {
                        readFromFile(DictionaryManagement.PATH_DICTIONARY_FILE);
                        LOGGER.info("No I/O error occured.");
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                    break;
                case 9:
                    try {
                        exportToFile();
                        LOGGER.info("No I/O error occured.");
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                    break;
                default:
                    System.err.println("PLEASE ENTER A NUMBER FROM 1 TO 9!");
                    break;
            }
        }
    }

    private String scanWord(String word) {
        while (word.isEmpty()) {
            word = scanner.nextLine();
        }

        return word;
    }

    private void printSearchResult() {
        if (dictionary.getSearchResultList().isEmpty()) {
            System.out.println("No word exist!");
            return;
        }

        System.out.printf("%-4s | %-18s | %-20s%n", "No", "English", "Vietnamese");
        int wordCounter = 1;
        for (Word word : dictionary.getSearchResultList()) {
            System.out.printf(
                    "%-4s | %-18s | %-20s%n",
                    wordCounter++,
                    word.getWord_target(),
                    word.getWord_explain()
            );
        }
    }

    /**
     * In tất cả các từ trong từ điển.
     */
    public void showAllWords() {
        System.out.printf("%-4s | %-18s | %-20s%n", "No", "English", "Vietnamese");

        int i = 1;
        for (Word word : dictionary.getWordList()) {
            System.out.printf(
                    "%-4s | %-18s | %-20s%n",
                    i++,
                    word.getWord_target(),
                    word.getWord_explain()
            );
        }
    }
}
