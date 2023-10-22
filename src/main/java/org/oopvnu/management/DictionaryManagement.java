package org.oopvnu.management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.oopvnu.entities.Dictionary;
import org.oopvnu.entities.Word;

public class DictionaryManagement {
    private final Dictionary dictionary;
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());

    private final Scanner scanner = new Scanner(System.in);

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary() {
        return dictionary;
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
     * Mở file txt va doc du lieu tu tep.
     */
    public void insertFromFile(String filename) throws IOException {
        FileHandler fileHandler = new FileHandler("log.txt");
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\t");
                Word word = new Word(words[0], words[1]);
                dictionary.addWord(word);
            }

            bufferedReader.close();
            fileReader.close();
            LOGGER.info("Successfully read from file.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }


    /**
     * Trả về wordList của Dictionary qua việc
     * gọi phương thức listWord của Dictionary.
     * @return wordList của Dictionary
     */
    public List<Word> getListWord() {
        return dictionary.getWordList();
    }
}
