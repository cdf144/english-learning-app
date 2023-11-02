package org.oopvnu;

import org.oopvnu.entities.Word;
import org.oopvnu.management.DictionaryManagement;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryCommandline {
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());

    private final DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }


    private void initLogFile() throws IOException {
        FileHandler fileHandler = new FileHandler("logSubAdvanced.txt");
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);
    }

    /**
     * Menu.
     * Nhập n là số thứ tự của lựa chọn
     * Nếu n không hợp lệ yêu cầu nhập lại
     */
    public void dictionaryAdvanced() throws IOException {
        initLogFile();

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to My Application!");
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
        System.out.println("Your action: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 0:
                break;
            case 1:
                dictionaryManagement.addFromCommandline();
                subDictionaryAdvanced();
                break;
            case 2:
                dictionaryManagement.removeFromCommandline();
                subDictionaryAdvanced();
                break;
            case 3:
                dictionaryManagement.updateFromCommandLine();
                subDictionaryAdvanced();
                break;
            case 4:
                showAllWords();
                subDictionaryAdvanced();
                break;
            case 5:
                dictionaryManagement.dictionaryLookup();
                subDictionaryAdvanced();
                break;
            case 6:
                dictionaryManagement.dictionarySearcher();
                subDictionaryAdvanced();
                break;
            case 7:
                System.out.println("lam gi đa co game ma choi :)))");
                subDictionaryAdvanced();
                break;
            case 8:
                try {
                    dictionaryManagement.readFromFile(DictionaryManagement.PATH_TO_DICTIONARY_FILE);
                    subDictionaryAdvanced();
                    LOGGER.info("No I/O error occured.");
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
                break;
            case 9:
                try {
                    dictionaryManagement.dictionaryExportToFile();
                    subDictionaryAdvanced();
                    LOGGER.info("No I/O error occured.");
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
                break;
            default:
                System.out.println("ENTER A NUMBER FROM 1 TO 9, PLEASE!");
                subDictionaryAdvanced();
        }
    }

    /**
     * Menu.
     * Nhập n là số thứ tự của lựa chọn
     * Nếu n không hợp lệ yêu cầu nhập lại
     */
    public void subDictionaryAdvanced() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Your action: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 0:
                break;
            case 1:
                dictionaryManagement.addFromCommandline();
                subDictionaryAdvanced();
                break;
            case 2:
                dictionaryManagement.removeFromCommandline();
                subDictionaryAdvanced();
                break;
            case 3:
                dictionaryManagement.updateFromCommandLine();
                subDictionaryAdvanced();
                break;
            case 4:
                showAllWords();
                subDictionaryAdvanced();
                break;
            case 5:
                dictionaryManagement.dictionaryLookup();
                subDictionaryAdvanced();
                break;
            case 6:
                dictionaryManagement.dictionarySearcher();
                subDictionaryAdvanced();
                break;
            case 7:
                System.out.println("lam gi đa co game ma choi :)))");
                subDictionaryAdvanced();
                break;
            case 8:
                try {
                    dictionaryManagement.readFromFile(DictionaryManagement.PATH_TO_DICTIONARY_FILE);
                    LOGGER.info("No I/O error occured.");
                    subDictionaryAdvanced();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
                break;
            case 9:
                try {
                    dictionaryManagement.dictionaryExportToFile();
                    LOGGER.info("No I/O error occured.");
                    subDictionaryAdvanced();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
                break;
            default:
                System.out.println("ENTER A NUMBER FROM 1 TO 9, PLEASE!");
                subDictionaryAdvanced();
        }
    }

    /**
     * In tất cả các từ trong từ điển.
     */
    public void showAllWords() {
        System.out.printf("%-3s | %-15s | %-20s%n", "No", "English", "Vietnamese");
        int i = 1;

        dictionaryManagement.sortWordList();

        for (Word word : dictionaryManagement.getListWord()) {
            System.out.printf(
                    "%-3s | %-15s | %-20s%n",
                    i++,
                    word.getWord_target(),
                    word.getWord_explain()
            );
        }
    }
}
