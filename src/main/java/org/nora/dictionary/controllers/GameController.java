package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.nora.dictionary.DictionaryApplication;

import java.io.IOException;
import java.util.Objects;

public class GameController {
    @FXML
    private Label titleLabel;
    @FXML
    private Button shuffleGameButton;
    @FXML
    private Button guessWordGameButton;
    @FXML
    private BorderPane gamePane;
    @FXML
    private BorderPane guessWordGamePane;
    @FXML
    private BorderPane shuffleGamePane;

    @FXML
    public void onStartGuessGameButtonClick() throws IOException {
        guessWordGamePane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("gameguessword.fxml")
                )
        );
        setGamePane(guessWordGamePane);
    }

    @FXML
    public void onStartShuffleGameButtonClick() throws IOException {
        shuffleGamePane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("gameshuffle.fxml")
                )
        );
        setGamePane(shuffleGamePane);
    }

    private void setGamePane(BorderPane borderPane) {
        gamePane.setTop(borderPane.getTop());
        gamePane.setLeft(borderPane.getLeft());
        gamePane.setCenter(borderPane.getCenter());
        gamePane.setRight(borderPane.getRight());
        gamePane.setBottom(borderPane.getBottom());
    }
}