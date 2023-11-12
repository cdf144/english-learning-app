package org.nora.dictionary.management;

import java.io.*;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nora.dictionary.entities.Dictionary;
import org.nora.dictionary.entities.Word;

public class DictionaryManagement {
    protected Dictionary dictionary;

    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    public static final String PATH_DICTIONARYMANAGEMENT_LOG = System.getProperty("user.dir")
            + File.separator + "log"
            + File.separator + "logDictionaryManagement.log";

    public static final String PATH_DICTIONARY_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "dictionaries.txt";

    private static final FileHandler logFileHandler;
    static {
        try {
            logFileHandler = new FileHandler(PATH_DICTIONARYMANAGEMENT_LOG, false);
            logFileHandler.setLevel(Level.INFO);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException e) {
            throw new RuntimeException("Could not initalize DictionaryManagement log FileHandler!", e);
        }
    }

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
     * Đọc dữ liệu từ file .txt và sau đó in ra danh
     * sách từ trong từ điển theo thứ tự được sort.
     * Mỗi lần đọc dữ liệu, wordList sẽ được ghi đè.
     *
     * @param filePath String path đến file .txt
     * @throws IOException Ngoại lệ được throw nếu FileHandler
     *                     bị lỗi.
     */
    public void readFromFile(String filePath) throws IOException {
        FileReader fileReader;
        BufferedReader bufferedReader;

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw e;
        }

        dictionary.getWordList().clear();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.split("\t");
            Word word = new Word(words[0].toLowerCase(), words[1]);
            dictionary.addWord(word);
        }

        dictionary.sortWordList();

        bufferedReader.close();
        fileReader.close();
        LOGGER.info("Dictionary read and sorted.");
    }

    /**
     * In ra word_explain của wordTarget đầu vào.
     * Thông báo nếu không có từ nào trùng khớp được tìm thấy
     */
    public String dictionaryLookup(String wordTarget) {
        Word wordFind = new Word(wordTarget.toLowerCase(), null);

        int index = dictionary.findWord(wordFind);

        if (index < 0) {
            return "No word exist!";
        } else {
            return dictionary.getWordList().get(index).getWord_explain();
        }
    }

    /**
     * Đọc dữ liệu từ wordList.
     * Thêm các Words thỏa mãn vào searchResultList
     * In ra danh sách các Word trong searchResultList giống như hàm showAllWord
     */
    public void dictionarySearcher(String wordTarget) {
        dictionary.getSearchResultList().clear();
        Word wordFind = new Word(wordTarget.toLowerCase(), null);

        int index = dictionary.findWordWithPrefix(wordFind);
        if (index < 0) {
            return;
        }

        int startIndex = index;
        int endIndex = index;

        while (startIndex > 0
                && dictionary.getWordList().get(startIndex - 1).getWord_target().startsWith(wordTarget)
        ) {
            startIndex--;
        }

        while (endIndex < dictionary.getWordList().size()
                && dictionary.getWordList().get(endIndex + 1).getWord_target().startsWith(wordTarget)
        ) {
            endIndex++;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            dictionary.getSearchResultList().add(dictionary.getWordList().get(i));
        }
    }

    /**
     * Kiểm tra xem đầu vào là wordTarget đã có trong wordList chưa.
     * @param word wordTarget để tìm
     * @return Word chứa wordTarget có trong wordList hay không.
     */
    public boolean wordExist(String word) {
        int index = dictionary.findWord(new Word(word.toLowerCase(), null));
        return index >= 0;
    }

    /**
     * Thêm một từ vào wordList.
     * Nhập word_target và word_explain
     */
    public void addToDictionary(String wordTarget, String wordExplain) {
        Word newWord = new Word(wordTarget.toLowerCase(), wordExplain);
        dictionary.insertWord(newWord);
    }

    /**
     * Xóa một từ trong wordList.
     * Nhập word_target hoặc word_explain của từ cần xóa
     */
    public void removeFromDictionary() {
        System.out.println("Enter word_target or word_explain you want to remove:");
        String find = scanner.nextLine();
        Word wordFindTarget = new Word(find.toLowerCase(), null);
        Word wordFindExplain = new Word(null, find);

        int targetIndex = dictionary.findWord(wordFindTarget);
        int explainIndex = dictionary.findWordExplain(wordFindExplain);

        if (targetIndex < 0 && explainIndex < 0) {
            System.out.println("No word exist!");
        } else {
            dictionary.getWordList().remove(explainIndex < 0 ? targetIndex : explainIndex);
            System.out.println("REMOVED!");
        }
    }

    /**
     * Sửa một từ trong wordList.
     * Nhập từ cần sửa nghĩa và nghĩa sau khi sửa
     */
    public void updateInDictionary() {
        System.out.println("Update: Enter word you want to update:");
        String wordTarget = scanner.nextLine();
        Word newWord = new Word(wordTarget.toLowerCase(), null);

        int index = dictionary.findWord(newWord);

        if (index < 0) {
            System.out.println("No word exist!");
            return;
        } else {
            System.out.println("Update: Enter your changed word_explain: ");
            String newWordExplain = scanner.nextLine();
            newWord.setWord_explain(newWordExplain);
            dictionary.getWordList().set(index, newWord);
        }

        System.out.println("UPDATED!");
    }

    /**
     * Xuất wordList ra file.
     * @throws IOException Được ném nếu có lỗi xảy ra với FileWriter
     */
    public void exportToFile() throws IOException {
        FileWriter fileWriter;
        fileWriter = new FileWriter(DictionaryManagement.PATH_DICTIONARY_FILE);

        StringBuilder content = new StringBuilder();
        for (Word word : dictionary.getWordList()) {
            content.append(word.getWord_target()).append("\t");
            content.append(word.getWord_explain()).append("\n");
        }

        fileWriter.write(content.toString());
        fileWriter.close();
        System.out.println("Completed!");
    }

}
