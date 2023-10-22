import org.oopvnu.DictionaryCommandline;

import java.io.File;
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
    public void readFromFileTest(String filename) throws IOException {
        try {
            dictionaryCommandline.getDictionaryManagement().insertFromFile(filename);
            dictionaryCommandline.getDictionaryManagement().getDictionary().sortWordList();
            dictionaryCommandline.showAllWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File file;
        String filename = "src"
                        + File.separator + "main"
                        + File.separator + "resources"
                        + File.separator + "dictionaries.txt"
        ;

        String workingDir = System.getProperty("user.dir");
        file = new File(workingDir, filename);

        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();

//        dictionaryCommandLine.dictionaryBasic();
        dictionaryCommandLine.readFromFileTest(file.getAbsolutePath());
    }
}
