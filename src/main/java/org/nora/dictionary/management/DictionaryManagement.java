package org.nora.dictionary.management;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nora.dictionary.entities.Dictionary;
import org.nora.dictionary.entities.Word;

public class DictionaryManagement {
    protected Dictionary dictionary;
    protected List<Word> searchResultList;

    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());

    public static final String PATH_DICTIONARYMANAGEMENT_LOG = System.getProperty("user.dir")
            + File.separator + "log"
            + File.separator + "logDictionaryManagement.log";

    public static final String PATH_DICTIONARY_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "dictionaryAdvanced.txt";

    public static final String PATH_DICTIONARY_HTML_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "dictionaryHTML_EV.txt";

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
        searchResultList = new ArrayList<>();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<Word> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(List<Word> searchResultList) {
        this.searchResultList = searchResultList;
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

        String splitPattern;
        String htmlStart;
        if (filePath.equals(PATH_DICTIONARY_FILE)) {
            splitPattern = "\t";
            htmlStart = "";
        } else if (filePath.equals(PATH_DICTIONARY_HTML_FILE)) {
            splitPattern = "<html>";
            htmlStart = "<html>";
        } else {
            System.out.println("Invalid file");
            return;
        }

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] wordParts = line.split(splitPattern);
            String target = wordParts[0];
            String explain = htmlStart + wordParts[1];
            Word word = new Word(target.toLowerCase(), explain);
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
            return dictionary.getWordList().get(index).getExplain();
        }
    }

    /**
     * Đọc dữ liệu từ wordList.
     * Thêm các Words thỏa mãn vào searchResultList
     * In ra danh sách các Word trong searchResultList giống như hàm showAllWord
     */
    public void dictionarySearcher(String wordTarget) {
        searchResultList.clear();
        Word wordFind = new Word(wordTarget.toLowerCase(), null);

        int index = dictionary.findWordWithPrefix(wordFind);
        if (index < 0) {
            return;
        }

        int startIndex = index;
        int endIndex = index;

        while (startIndex > 0
                && dictionary.getWordList().get(startIndex - 1).getTarget().startsWith(wordTarget)
        ) {
            startIndex--;
        }

        while (endIndex < dictionary.getWordList().size() - 1
                && dictionary.getWordList().get(endIndex + 1).getTarget().startsWith(wordTarget)
        ) {
            endIndex++;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            searchResultList.add(dictionary.getWordList().get(i));
        }
    }

    /**
     * Kiểm tra xem đầu vào là wordTarget đã có trong wordList chưa.
     * @param wordTarget wordTarget để tìm
     * @return Word chứa wordTarget có trong wordList hay không.
     */
    public boolean wordExist(String wordTarget) {
        int index = dictionary.findWord(new Word(wordTarget.toLowerCase(), null));
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
     * Nhập word_target của từ cần xóa
     */
    public boolean removeFromDictionary(String wordTarget) {
        int index = dictionary.findWord(new Word(wordTarget.toLowerCase(), null));

        if (index < 0) {
            return false;
        } else {
            dictionary.getWordList().remove(index);
            return true;
        }
    }

    /**
     * Sửa một từ trong wordList.
     * Nhập từ cần sửa nghĩa và nghĩa sau khi sửa
     */
    public void updateInDictionary(String wordTarget, String wordExplain) {
        Word newWord = new Word(wordTarget.toLowerCase(), wordExplain);
        int index = dictionary.findWord(newWord);
        dictionary.getWordList().set(index, newWord);
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
            content.append(word.getTarget()).append("\t");
            content.append(word.getExplain()).append("\n");
        }

        fileWriter.write(content.toString());
        fileWriter.close();
    }
}
