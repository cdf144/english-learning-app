package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nora.dictionary.DictionaryApplication;

import java.io.IOException;
import java.util.Objects;

public class GameController {
    @FXML
    public void onStartGuessGameButtonClick() throws IOException {
        Parent root;
        root = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("gameguessword.fxml")
                )
        );
        Stage stage = new Stage();
        stage.setTitle("Guess Word Game");
        stage.setScene(new Scene(root, 1100, 705));
        stage.show();
    }

    @FXML
    public void onStartShuffleGameButtonClick() throws IOException {
        Parent root;
        root = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("shufflegame.fxml")
                )
        );
        Stage stage = new Stage();
        stage.setTitle("Shuffle Game");
        stage.setScene(new Scene(root, 1100, 705));
        stage.show();
    }
}