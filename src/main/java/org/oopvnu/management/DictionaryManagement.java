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

public class DictionaryManagement extends Dictionary {
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
     * Mở file .txt và đọc dữ liệu từ file gồm các
     * từ tiếng Anh và giải nghĩa tiếng Việt được
     * phân cách bởi 1 dấu tab.
     * @param filename String đường dẫn đến file .txt
     * @throws IOException Ngoại lệ được throw nếu FileHandler
     *                     hoặc FileReader bị lỗi
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
     * Mở file .txt và đọc dữ liệu từ file gồm các
     * từ tiếng Anh và giải nghĩa tiếng Việt được
     * phân cách bởi 1 dấu tab.
     * @param fileName String đường dẫn đến file .txt
     * @throws IOException Ngoại lệ được throw nếu FileHandler
     *                     hoặc FileReader bị lỗi
     */
    public static void dictionaryLookup(String fileName) throws IOException {
        int count = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word target (in English): ");
        String wordTarget = sc.nextLine();

        FileHandler fileHandler = new FileHandler("log.txt");
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String englishWord = parts[0].trim();
                    String vietnameseWord = parts[1].trim();

                    if (wordTarget.equalsIgnoreCase(englishWord)) {
                        count++;
                        System.out.println("The Vietnamese translation is: " + vietnameseWord);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        if (count == 0) {
            System.out.println("No word exists in the dictionary!");
        }
    }

    public static void dictionarySearcher(String fileName) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word (in English): ");
        String wordTarget = sc.nextLine();

        FileHandler fileHandler = new FileHandler("log.txt");
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String englishWord = parts[0].trim();
                    String vietnameseWord = parts[1].trim();

                    if (englishWord.toLowerCase().startsWith(wordTarget.toLowerCase())) {
                        System.out.printf("%s    |%s\t\n", englishWord, vietnameseWord);
                    }
                }
            }
            reader.close();
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
