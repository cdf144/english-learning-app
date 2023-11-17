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

public class ApplicationController implements Initializable {
    @FXML
    private Button searchTab;
    @FXML
    private Button googleTranslateTab;
    @FXML
    private Button favoritesTab;
    @FXML
    private Button historyTab;
    @FXML
    private Button addToDictTab;
    @FXML
    private Button gameTab;
    @FXML
    private Button settingsTab;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private BorderPane searcherPane;
    @FXML
    private BorderPane googleTranslatePane;
    @FXML
    private BorderPane favoritesPane;
    @FXML
    private BorderPane historyPane;
    @FXML
    private BorderPane addToDictPane;
    @FXML
    private BorderPane gamePane;
    @FXML
    private BorderPane settingsPane;

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
            loadSearcher();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMainBorderPane(BorderPane borderPane) {
        mainBorderPane.setTop(borderPane.getTop());
        mainBorderPane.setLeft(borderPane.getLeft());
        mainBorderPane.setCenter(borderPane.getCenter());
        mainBorderPane.setRight(borderPane.getRight());
        mainBorderPane.setBottom(borderPane.getBottom());
    }

    private void resetActiveStatusAllButtons() {
        searchTab.getStyleClass().removeAll("active");
        googleTranslateTab.getStyleClass().removeAll("active");
        favoritesTab.getStyleClass().removeAll("active");
        historyTab.getStyleClass().removeAll("active");
        addToDictTab.getStyleClass().removeAll("active");
        gameTab.getStyleClass().removeAll("active");
        settingsTab.getStyleClass().removeAll("active");
    }

    public void loadSearcher() throws IOException {
        resetActiveStatusAllButtons();
        searchTab.getStyleClass().add("active");
        searcherPane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("searcher.fxml")
                )
        );
        setMainBorderPane(searcherPane);
    }

    public void loadGoogleTranslate() throws IOException {
        resetActiveStatusAllButtons();
        googleTranslateTab.getStyleClass().add("active");
        googleTranslatePane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("googletranslate.fxml")
                )
        );
        setMainBorderPane(googleTranslatePane);
    }

    public void loadFavorites() throws IOException {
        resetActiveStatusAllButtons();
        favoritesTab.getStyleClass().add("active");
        favoritesPane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("favorites.fxml")
                )
        );
        setMainBorderPane(favoritesPane);
    }

    public void loadHistory() throws IOException {
        resetActiveStatusAllButtons();
        historyTab.getStyleClass().add("active");
        historyPane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("history.fxml")
                )
        );
        setMainBorderPane(historyPane);
    }

    public void loadAddToDict() throws IOException {
        resetActiveStatusAllButtons();
        addToDictTab.getStyleClass().add("active");
        addToDictPane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("add.fxml")
                )
        );
        setMainBorderPane(addToDictPane);
    }

    public void loadGame() throws IOException {
        resetActiveStatusAllButtons();
        gameTab.getStyleClass().add("active");
        gamePane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("game.fxml")
                )
        );
        setMainBorderPane(gamePane);
    }

    public void loadSettings() throws IOException {
        resetActiveStatusAllButtons();
        settingsTab.getStyleClass().add("active");
        settingsPane = FXMLLoader.load(
                Objects.requireNonNull(
                        DictionaryApplication.class.getResource("settings.fxml")
                )
        );
        setMainBorderPane(settingsPane);
    }
}
