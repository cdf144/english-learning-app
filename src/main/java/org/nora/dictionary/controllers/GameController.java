package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.nora.dictionary.DictionaryApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private BorderPane fatherPane;
    @FXML
    private Button quitGameButton;

    @FXML
    private BorderPane gameChoosePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            onQuitGameButtonClick();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onQuitGameButtonClick() throws IOException {
        gameChoosePane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("gamechoose.fxml")
                )
        );

        setFatherPane(gameChoosePane);
    }

    private void setFatherPane(BorderPane borderPane) {
        fatherPane.setTop(borderPane.getTop());
        fatherPane.setLeft(borderPane.getLeft());
        fatherPane.setCenter(borderPane.getCenter());
        fatherPane.setRight(borderPane.getRight());
        fatherPane.setBottom(borderPane.getBottom());
    }
}