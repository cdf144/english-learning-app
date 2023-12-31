module english.learning.app {
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires java.sql;
    requires java.logging;
    requires freetts;
    requires jlayer;

    opens org.nora.dictionary to javafx.fxml;
    opens org.nora.dictionary.controllers to javafx.fxml;
    exports org.nora.dictionary;
    exports org.nora.dictionary.entities;
    exports org.nora.dictionary.management;
    exports org.nora.dictionary.commandline;
    exports org.nora.dictionary.game.GuessWord;
    exports org.nora.dictionary.utils;
}