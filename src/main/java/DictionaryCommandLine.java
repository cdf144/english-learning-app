import org.oopvnu.DictionaryCommandline;

public class DictionaryCommandLine {
    DictionaryCommandline dictionaryCommandline;

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }
    public void dictionaryBasic() {
        dictionaryCommandline.getDictionaryManagement().insertFromCommandline();
        dictionaryCommandline.showAllWords();
    }

    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
        dictionaryCommandLine.dictionaryBasic();
    }
}
