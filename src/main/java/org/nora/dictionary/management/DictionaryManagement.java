package org.nora.dictionary.management;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nora.dictionary.entities.Dictionary;
import org.nora.dictionary.entities.Word;

public class DictionaryManagement {
    Dictionary dictionary;
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    public static final String PATH_DICTIONARY_FILE = "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "dictionaries.txt";

    public static final String PATH_READFROMFILE_LOG = "log"
            + File.separator + "logReadFromFile.log";

    public static final String PATH_INSERTFROMFILE_LOG = "log"
            + File.separator + "logInsertFromFile.log";

    public static final String PATH_EXPORTTOFILE_LOG = "log"
            + File.separator + "logExportToFile.log";

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
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

            dictionary.addWord(new Word(word_target, word_explain));
            System.out.println();
        }
    }

    /**
     * Đọc dữ liệu từ file .txt và sau đó in ra danh
     * sách từ trong từ điển theo thứ tự được sort.
     *
     * @param filePath String path đến file .txt
     * @throws IOException Ngoại lệ được throw nếu FileHandler
     *                     bị lỗi.
     */
    public void readFromFile(String filePath) throws IOException {
        FileHandler fileHandler = new FileHandler(PATH_READFROMFILE_LOG, false);
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);

        try {
            insertFromFile(filePath);
            dictionary.sortWordList();
            LOGGER.info("Dictionary read and sorted.");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Mở file .txt và đọc dữ liệu từ file gồm các
     * từ tiếng Anh và giải nghĩa tiếng Việt được
     * phân cách bởi 1 dấu tab.
     * @param filePath String đường dẫn đến file .txt
     * @throws IOException Ngoại lệ được throw nếu FileHandler
     *                     hoặc FileReader bị lỗi
     */
    public void insertFromFile(String filePath) throws IOException {
        FileHandler fileHandler = new FileHandler(PATH_INSERTFROMFILE_LOG, false);
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);

        FileReader fileReader = new FileReader(filePath);
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
    }

    /**
     * Nhập một từ chính xác cần tìm và in ra word_explain.
     * Thông báo nếu không có từ nào trùng khớp được tìm thấy
     */
    public void dictionaryLookup() {
        System.out.print("Lookup: Enter your word target: ");
        String word_target = scanner.next();
        Word wordFind = new Word(word_target, null);

        int index = Collections.binarySearch(
                dictionary.getWordList(),
                wordFind,
                new WordComparator()
        );

        if (index < 0) {
            System.out.println("No word exist!");
        } else {
            System.out.println(
                    dictionary.getWordList().get(index).getWord_explain()
            );
        }
    }

    /**
     * Đọc dữ liệu từ wordList.
     * Thêm các Words thỏa mãn vào resWordList
     * In ra danh sách các Word trong resWordList giống như hàm showAllWord
     */
    public void dictionarySearcher() {
        System.out.print("Searcher: Enter your word: ");
        String wordTarget = scanner.nextLine().toLowerCase();

        dictionary.getSearchResultList().clear();
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().toLowerCase().startsWith(wordTarget)) {
                dictionary.getSearchResultList().add(word);
            }
        }

        if (dictionary.getSearchResultList().isEmpty()) {
            System.out.println("No word exist!");
            return;
        }

        System.out.printf("%-3s | %-15s | %-20s%n", "No", "English", "Vietnamese");
        int wordCounter = 1;
        for (Word word : dictionary.getSearchResultList()) {
            System.out.printf(
                    "%-3s | %-15s | %-20s%n",
                    wordCounter++,
                    word.getWord_target(),
                    word.getWord_explain()
            );
        }

    }

    /**
     * Thêm một từ vào wordList.
     * Nhập word_target và word_explain
     */
    public void addFromCommandline() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Add: Enter new word_target: ");
        String word_target = sc.nextLine();

        System.out.println("Add: Enter this word_explain: ");
        String word_explain = sc.nextLine();

        Word newWord = new Word(word_target, word_explain);
        dictionary.getWordList().add(newWord);
        System.out.println("ADDED!");
    }

    /**
     * Xóa một từ trong wordList.
     * Nhập word_target hoặc word_explain của từ cần xóa
     */
    public void removeFromCommandline() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter word_target or word_explain you want to remove: ");
        String word_target = sc.nextLine();

        boolean check = false;
        for (int i = 0; i < dictionary.getWordList().size(); i++) {
            if (word_target.equalsIgnoreCase(dictionary.getWordList().get(i).getWord_target())
                || word_target.equalsIgnoreCase(dictionary.getWordList().get(i).getWord_explain())
            ) {
                dictionary.getWordList().remove(dictionary.getWordList().get(i));
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("No word exist!");
        } else {
            System.out.println("REMOVED!");
        }
    }

    /**
     * Sua mot tu trong wordList.
     * Nhập từ cần sửa nghĩa và nghĩa sau khi sửa
     */
    public void updateFromCommandLine() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Update: Enter word you want to update: ");
        String word_target = sc.nextLine();

        for (int i = 0; i < dictionary.getWordList().size(); i++) {
            if (word_target.equalsIgnoreCase(dictionary.getWordList().get(i).getWord_target())) {
                System.out.println("Update: Enter your changed word_explain: ");
                String newWordExplain = sc.nextLine();
                dictionary.getWordList().set(i, new Word(word_target, newWordExplain));
            }
        }
        System.out.println("UPDATED!");
    }

    /**
     * Xuất wordList ra file.
     * @throws IOException Được ném nếu có lỗi xảy ra với FileWriter
     */
    public void exportToFile() throws IOException {
        FileHandler fileHandler = new FileHandler(PATH_EXPORTTOFILE_LOG, false);
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(DictionaryManagement.PATH_DICTIONARY_FILE);

            dictionary.sortWordList();
            StringBuilder content = new StringBuilder();
            for (Word word : dictionary.getWordList()) {
                content.append(word.getWord_target()).append("\t");
                content.append(word.getWord_explain()).append("\n");
            }

            fileWriter.write(content.toString());
            fileWriter.close();
            System.out.println("Completed!");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
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
