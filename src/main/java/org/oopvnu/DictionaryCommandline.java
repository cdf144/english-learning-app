package org.oopvnu;

import org.oopvnu.management.DictionaryManagement;
import org.oopvnu.entities.Word;

import java.util.Scanner;

public class DictionaryCommandline {
    private final DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    /**
     * In tất cả các từ trong từ điển.
     */

    /**
     * .
     */
    public void dictionaryAdvanced() {
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
                break;
            case 2:
                dictionaryManagement.removeFromCommandline();
                break;
            case 3:
                dictionaryManagement.updateFromCommandLine();
                break;
            case 4:
                showAllWords();
                break;
            case 5:
                dictionaryManagement.dictionaryLookup();
                break;
            case 6:
                dictionaryManagement.dictionarySearcher();
                break;
            case 7:
                System.out.println("lam gi đa co game ma choi :)))");
                break;
            case 8:
//                dictionaryManagement.insertFromFile();
                break;
            case 9:
                System.out.println("Chua viet ham này heheh :)))");
                break;
            default:
                System.out.println("ENTER A NUMBER FROM 1 TO 9, PLEASE!");
                subDictionaryAdvanced();
        }
    }

    public void subDictionaryAdvanced() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 0:
                break;
            case 1:
                dictionaryManagement.addFromCommandline();
                break;
            case 2:
                dictionaryManagement.removeFromCommandline();
                break;
            case 3:
                dictionaryManagement.updateFromCommandLine();
                break;
            case 4:
                showAllWords();
                break;
            case 5:
                dictionaryManagement.dictionaryLookup();
                break;
            case 6:
                dictionaryManagement.dictionarySearcher();
                break;
            case 7:
                System.out.println("lam gi đa co game ma choi :)))");
                break;
            case 8:
//                dictionaryManagement.insertFromFile();
                break;
            case 9:
                System.out.println("Chua viet ham này heheh :)))");
                break;
            default:
                System.out.println("ENTER A NUMBER FROM 1 TO 9, PLEASE!");
                subDictionaryAdvanced();
        }
    }

    public void showAllWords() {
        System.out.printf("%-3s | %-15s | %-20s%n", "No", "English", "Vietnamese");
        int i = 1;

        dictionaryManagement.sortWordList();

        for (Word word : dictionaryManagement.getListWord()) {
            System.out.printf("%-3s | %-15s | %-20s%n", i++, word.getWord_target(), word.getWord_explain());
        }
    }
}
