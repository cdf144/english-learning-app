package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameGuessWordController {

    @FXML
    protected ImageView imageView;

    @FXML
    protected Button buttonA;

    @FXML
    protected Button buttonB;

    @FXML
    protected Button buttonC;

    @FXML
    protected Button buttonD;

    @FXML
    protected Label scoreLabel;

    private List<String> wordList;
    private List<String> imageList;
    private int score = 0;

    public static final String PATH_GUESS_GAME_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "GuessGame.txt";

    public static final String PATH_GUESS_GAME_IMAGE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "GuessGameImage";

    private List<String> generateWordList(String filePath) {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    private List<String> generateImageList(String folderPath, List<String> wordList) {
        List<String> images = new ArrayList<>();

        for (String word : wordList) {
            String imagePath = folderPath + "/" + word + ".jpg";
            images.add(imagePath);
        }

        return images;
    }

    public void initialize() {
        this.wordList = generateWordList(PATH_GUESS_GAME_TXT);
        this.imageList = generateImageList(PATH_GUESS_GAME_IMAGE, this.wordList);
        loadNextQuestion();
        scoreLabel.setText("0");
    }

    private void loadNextQuestion() {
        Random random = new Random();
        int index = random.nextInt(imageList.size());

        String imagePath = imageList.get(index);
        String correctAnswer = wordList.get(index);

        Image image = new Image(new File(imagePath).toURI().toString());
        imageView.setImage(image);

        List<String> answers = new ArrayList<>();
        answers.add(correctAnswer);

        List<String> tempWordList = new ArrayList<>(wordList);
        tempWordList.remove(correctAnswer);

        Collections.shuffle(tempWordList);

        for (int i = 0; i < 3; i++) {
            answers.add(tempWordList.get(i));
        }

        Collections.shuffle(answers);

        buttonA.setText(answers.get(0));
        buttonB.setText(answers.get(1));
        buttonC.setText(answers.get(2));
        buttonD.setText(answers.get(3));
    }
}