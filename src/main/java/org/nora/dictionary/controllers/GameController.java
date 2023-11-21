package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.nora.dictionary.DictionaryApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private BorderPane gameFatherPane;
    @FXML
    private Button enterGameButton;
    @FXML
    private Label gameRuleLabel;
    @FXML
    private Button quitGameButton;

    @FXML
    private BorderPane gameChoosePane;
    @FXML
    private BorderPane guessWordGamePane;
    @FXML
    private BorderPane shuffleGamePane;

    private final String guessWordRule = "Guess Word: You will be given a picture and 4 words "
            + "(answers). Choose the appropiate word that accurately describes the picture given. "
            + "You start with 10 points. For each correct answer you will be given 5 points. For "
            + "each wrong answer you will be deducted 10 points.";

    private final String shuffleRule = "Shuffle: You will be given characters separated by forward "
            + "slashes (\"/\") that originally form a word. Type the original word in the box "
            + "(case insensitive) and press the ENTER key on your keyboard to answer.";

    @FXML
    public void onGuessGameButtonClick() throws IOException {
        gameRuleLabel.setText(guessWordRule);
        enterGameButton.setDisable(false);
    }

    @FXML
    public void onShuffleGameButtonClick() throws IOException {
        gameRuleLabel.setText(shuffleRule);
        enterGameButton.setDisable(false);
    }

    @FXML
    public void onEnterGameButtonClick() throws IOException {
        if (gameRuleLabel.getText().contains("Shuffle:")) {
            shuffleGamePane = FXMLLoader.load(
                    Objects.requireNonNull(
                            DictionaryApplication.class.getResource("gameshuffle.fxml")
                    )
            );

            setGamePane(shuffleGamePane);
        } else {
            guessWordGamePane = FXMLLoader.load(
                    Objects.requireNonNull(
                            DictionaryApplication.class.getResource("gameguessword.fxml")
                    )
            );

            setGamePane(guessWordGamePane);
        }

        quitGameButton.setDisable(false);
        quitGameButton.setVisible(true);
    }

    private void setGamePane(BorderPane borderPane) {
        gamePane.setTop(borderPane.getTop());
        gamePane.setLeft(borderPane.getLeft());
        gamePane.setCenter(borderPane.getCenter());
        gamePane.setRight(borderPane.getRight());
        gamePane.setBottom(borderPane.getBottom());
    }

    private void setGameFatherPane(BorderPane borderPane) {
        gameFatherPane.setTop(borderPane.getTop());
        gameFatherPane.setLeft(borderPane.getLeft());
        gameFatherPane.setCenter(borderPane.getCenter());
        gameFatherPane.setRight(borderPane.getRight());
        gameFatherPane.setBottom(borderPane.getBottom());
    }

    public void onQuitGameButtonClick() throws IOException {
        gameChoosePane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("gamechoose.fxml")
                )
        );

        quitGameButton.setDisable(true);
        quitGameButton.setVisible(false);
        setGamePane(gameChoosePane);
    }
}