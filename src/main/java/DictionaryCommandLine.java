import org.oopvnu.DictionaryCommandline;
import org.oopvnu.management.DictionaryManagement;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DictionaryCommandLine {
    DictionaryCommandline dictionaryCommandline;

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }
    public void dictionaryBasic() {
        dictionaryCommandline.getDictionaryManagement().insertFromCommandline();
        dictionaryCommandline.showAllWords();
    }
    public void readFromFileTest(String filename) throws IOException, FileNotFoundException {
        try {
            dictionaryCommandline.getDictionaryManagement().insertFromFile(filename);
            dictionaryCommandline.showAllWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String filename = "src\\main\\java\\org\\oopvnu\\management/dictionaries.txt";

        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();

//        dictionaryCommandLine.dictionaryBasic();
        dictionaryCommandLine.readFromFileTest(filename);
    }
}
