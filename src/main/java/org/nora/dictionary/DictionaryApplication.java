package org.nora.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nora.dictionary.management.DictionaryManagementSQLite;
import org.nora.dictionary.management.FavoriteWords;
import org.nora.dictionary.management.SearchHistory;
import org.nora.dictionary.utils.GoogleTranslateAPI;
import org.nora.dictionary.utils.GoogleVoiceAPI;
import org.nora.dictionary.utils.TextToSpeech;

import java.io.File;
import java.io.IOException;

public class DictionaryApplication extends Application {
    public static DictionaryManagementSQLite dictionary;

    public static final String PATH_UTILITY_ICONS_FOLDER = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "org"
            + File.separator + "nora"
            + File.separator + "dictionary"
            + File.separator + "icons"
            + File.separator + "utilities" + File.separator;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        dictionary = new DictionaryManagementSQLite();
        FXMLLoader fxmlLoader = new FXMLLoader(
                DictionaryApplication.class.getResource("application.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        SearchHistory.loadSearchHistory();
        FavoriteWords.loadFavoriteWords();
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setTitle("Nora Dictionary");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            TextToSpeech.shutDown();
            GoogleVoiceAPI.shutdownExecutorService();
            GoogleTranslateAPI.shutdownExecutorService();
            try {
                SearchHistory.saveSearchHistory();
                FavoriteWords.saveFavoriteWords();
                dictionary.closeConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
