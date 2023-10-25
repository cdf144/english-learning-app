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
     * Đọc dữ liệu từ wordList.
     * Nhập từ cần tìm và in ra word_explain
     * Thông báo nếu không có từ nào trùng khớp được tìm thấy
     */
    public static void dictionaryLookup() {
        int wordCounter = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word target: ");
        String word_target = sc.nextLine();
        for (Word word : wordList) {
            if (word_target.equalsIgnoreCase(word.getWord_target())) {
                wordCounter++;
                System.out.println("The word explain is: ");
                System.out.println(word.getWord_explain());
            }
        }
        if (wordCounter == 0) System.out.println("No word exist!");
    }

    /**
     * Đọc dữ liệu từ wordList.
     * Thêm các Words thỏa mãn vào resWordList
     * In ra danh sách các Word trong resWordList giống như hàm showAllWord
     */
    public static void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word: ");
        String wordTarget = sc.nextLine();
        resWordList.clear();
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getWord_target().toLowerCase().startsWith(wordTarget.toLowerCase())) {
                resWordList.add(wordList.get(i));
            }
        }
        System.out.printf("%-3s | %-15s | %-20s%n", "No", "English", "Vietnamese");
        int wordCounter = 1;
        for (Word word : resWordList) {
            System.out.printf("%-3s | %-15s | %-20s%n", wordCounter++, word.getWord_target(), word.getWord_explain());
        }

    }

    /**
     * Trả về wordList của Dictionary qua việc.
     * gọi phương thức listWord của Dictionary.
     * @return wordList của Dictionary
     */
    public List<Word> getListWord() {
        return dictionary.getWordList();
    }
}
