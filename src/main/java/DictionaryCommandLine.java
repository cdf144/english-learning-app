import org.oopvnu.DictionaryCommandline;
import org.oopvnu.management.DictionaryManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryCommandLine {
    DictionaryCommandline dictionaryCommandline;
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }

    /**
     * Bản command line nguyên thuỷ có chức năng nhập
     * từ và giải nghĩa của nó từ command line và sau
     * đó in ra những từ được nhập và giải nghĩa Việt.
     */
    public void dictionaryBasic() {
        dictionaryCommandline.getDictionaryManagement().insertFromCommandline();
        dictionaryCommandline.showAllWords();
    }

    /**
     * Đọc dữ liệu từ file .txt và sau đó in ra danh
     * sách từ trong từ điển theo thứ tự được sort.
     *
     * @param filename String path đến file .txt
     * @throws IOException Ngoại lệ được throw nếu FileHandler
     *                     bị lỗi.
     */
    public void readFromFile(String filename) throws IOException {
        FileHandler fileHandler = new FileHandler("log.txt");
        fileHandler.setLevel(Level.INFO);
        LOGGER.addHandler(fileHandler);
        try {
            dictionaryCommandline.getDictionaryManagement().insertFromFile(filename);
            dictionaryCommandline.getDictionaryManagement().sortWordList();
            LOGGER.info("All operation succeeded.");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Driver method.
     * @param args Thông số đầu vào
     * @throws IOException Được ném nếu không tìm thấy
     *                     file tại path được đặt
     */
    public static void main(String[] args) throws IOException {
        File file;
        String filename = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "dictionaries.txt";

        String workingDir = System.getProperty("user.dir");
        file = new File(workingDir, filename);

        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();

//        dictionaryCommandLine.dictionaryBasic();
        dictionaryCommandLine.readFromFile(file.getAbsolutePath());
        dictionaryCommandLine.dictionaryCommandline.getDictionaryManagement().dictionaryLookup();
        dictionaryCommandLine.dictionaryCommandline.getDictionaryManagement().dictionarySearcher();
    }
}
