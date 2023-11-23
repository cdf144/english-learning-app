package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.nora.dictionary.DictionaryApplication;

import java.io.IOException;
import java.util.Objects;

public class GameChooseController {
    @FXML
    private Button shuffleGameButton;
    @FXML
    private Button guessWordGameButton;
    @FXML
    private Button enterGameButton;
    @FXML
    private Label gameRuleLabel;
    @FXML
    private BorderPane gamePane;

    @FXML
    private BorderPane guessWordGamePane;
    @FXML
    private BorderPane shuffleGamePane;

    private final String guessWordRule = "Guess Word: You will be given a picture and 4 words "
            + "(answers). Choose the appropiate word that accurately describes the picture given.\n"
            + "Scoring in this game is \"Combo\" based, meaning your score will be how many "
            + "questions you have answered correctly in a row. If you answer a question wrong, "
            + "your score will be resetted to 0.";

    private final String shuffleRule = "Shuffle: You will be given characters separated by forward "
            + "slashes (\"/\") that originally form a word. Type the original word in the box "
            + "(case insensitive) and press the ENTER key on your keyboard to answer.";

    @FXML
    public void onGuessGameButtonClick() {
        gameRuleLabel.setText(guessWordRule);
        enterGameButton.setDisable(false);
    }
    @FXML
    public void onShuffleGameButtonClick() {
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
    }

    public void setGamePane(BorderPane borderPane) {
        gamePane.setTop(borderPane.getTop());
        gamePane.setLeft(borderPane.getLeft());
        gamePane.setCenter(borderPane.getCenter());
        gamePane.setRight(borderPane.getRight());
        gamePane.setBottom(borderPane.getBottom());
    }
}
