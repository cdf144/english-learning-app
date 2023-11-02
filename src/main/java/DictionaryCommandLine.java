import org.oopvnu.DictionaryCommandline;
import org.oopvnu.management.DictionaryManagement;

import java.io.IOException;
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
     * Driver method.
     * @param args Thông số đầu vào
     */
    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();

        // dictionaryCommandLine.dictionaryBasic();
        try {
            dictionaryCommandLine.dictionaryCommandline.dictionaryAdvanced();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
