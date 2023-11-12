module english.learning.app {
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.logging;

    opens org.nora.dictionary to javafx.fxml;
    exports org.nora.dictionary;
}