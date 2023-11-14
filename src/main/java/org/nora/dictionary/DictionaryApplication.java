package org.nora.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nora.dictionary.management.DictionaryManagement;
import org.nora.dictionary.utils.SearchHistory;
import org.nora.dictionary.utils.TextToSpeech;

import java.io.IOException;

public class DictionaryApplication extends Application {
    public static DictionaryManagement dictionary;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        dictionary = new DictionaryManagement();
        FXMLLoader fxmlLoader = new FXMLLoader(
                DictionaryApplication.class.getResource("application.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        SearchHistory.loadSearchHistory();
        primaryStage.setTitle("Nora Dictionary");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            TextToSpeech.deallocateSynthesizer();
            try {
                SearchHistory.saveSearchHistory();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
