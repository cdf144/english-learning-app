module english.learning.app {
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.logging;

    opens org.nora.dictionary to javafx.fxml;
    opens org.nora.dictionary.controllers to javafx.fxml;
    exports org.nora.dictionary;
    exports org.nora.dictionary.entities;
    exports org.nora.dictionary.management;
}