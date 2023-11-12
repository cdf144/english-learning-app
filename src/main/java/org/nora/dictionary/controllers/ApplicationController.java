package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.management.DictionaryManagement;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    @FXML
    private Button gameTab;

    @FXML
    private Button searchTab;

    @FXML
    private BorderPane mainBorderPane;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DictionaryApplication.dictionary.readFromFile(DictionaryManagement.PATH_DICTIONARY_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSearcher() throws IOException {
        BorderPane searcherPane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("searcher.fxml")
                )
        );

        mainBorderPane.setTop(searcherPane.getTop());
        mainBorderPane.setLeft(searcherPane.getLeft());
        mainBorderPane.setCenter(searcherPane.getCenter());
        mainBorderPane.setRight(searcherPane.getRight());
        mainBorderPane.setBottom(searcherPane.getBottom());
    }

    public void loadGame() throws IOException {
        BorderPane gamePane = FXMLLoader.load(
                Objects.requireNonNull(DictionaryApplication.class.getResource("game.fxml"))
        );

        mainBorderPane.setTop(gamePane.getTop());
        mainBorderPane.setLeft(gamePane.getLeft());
        mainBorderPane.setCenter(gamePane.getCenter());
        mainBorderPane.setRight(gamePane.getRight());
        mainBorderPane.setBottom(gamePane.getBottom());
    }
}
