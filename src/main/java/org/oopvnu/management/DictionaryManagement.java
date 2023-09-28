package org.oopvnu.management;

import org.oopvnu.entities.Dictionary;
import org.oopvnu.entities.Word;

import java.util.List;
import java.util.Scanner;
public class DictionaryManagement {
    private final Dictionary dictionary;

    private final Scanner scanner = new Scanner(System.in);

    public DictionaryManagement() {
        dictionary = new Dictionary();
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

            System.out.println("Nhập từ tiếng Anh:");
            String word_target = scanner.nextLine();

            System.out.println("Nhập nghĩa tiếng Việt:");
            String word_explain = scanner.nextLine();

            dictionary.addWord(new Word(word_target, word_explain));
            System.out.println();
        }
    }

    /**
     * Trả về wordList của Dictionary qua việc
     * gọi phương thức listWord của Dictionary.
     * @return wordList của Dictionary
     */
    public List<Word> getListWord() {
        return dictionary.listWord();
    }
}
