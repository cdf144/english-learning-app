package org.nora.dictionary.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.nora.dictionary.DictionaryApplication;
import org.nora.dictionary.management.DictionaryManagement;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class GameShuffleController implements Initializable {
    @FXML
    private Label questionLabel;
    @FXML
    private Label descLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label highScoreLabel;

    private DictionaryManagement dictionary;
    private int DICTIONARY_SIZE;
    private String correctAnswer;
    private int score = 0;
    private int highScore = 0;

    public static final String PATH_SHUFFLE_GAME_HIGH_SCORE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "shuffleGameHighScore.txt";
    public static final Pattern INVALID_CHARACTERS = Pattern.compile(
            "[-.'\\s]"
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dictionary = new DictionaryManagement();

        try {
            this.dictionary.readFromFile(DictionaryManagement.PATH_DICTIONARY_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DICTIONARY_SIZE = this.dictionary.getDictionary().getWordList().size();

        loadNextQuestion();
        scoreLabel.setText("0");
        updateHighScoreIfNeeded();
        highScoreLabel.setText(Integer.toString(highScore));

        answerField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !answerField.getText().trim().isEmpty()) {
                checkAnswer();
            }
        });
    }

    public static String generateRandomCharacter(String s) {
        int n = s.length();
        Random random = new Random();
        StringBuilder shuffled = new StringBuilder();
        List<Integer> charIndexList = new ArrayList<>();

        while (charIndexList.size() < n) {
            int index = random.nextInt(n);
            if (!charIndexList.contains(index)) {
                charIndexList.add(index);
                shuffled.append(s.charAt(index)).append(" / ");
            }
        }

        return shuffled.substring(0, shuffled.length() - 3);
    }

    private void loadNextQuestion() {
        Random random = new Random();

        int index;
        do {
            index = random.nextInt(DICTIONARY_SIZE);
            correctAnswer = this.dictionary.getDictionary().getWordList().get(index).getTarget();
        } while (
                correctAnswer.length() < 3
                || INVALID_CHARACTERS.matcher(correctAnswer).find()
        );

        String shuffled = generateRandomCharacter(correctAnswer);
        questionLabel.setText(shuffled);
        descLabel.setText(DictionaryApplication.dictionary.dictionaryLookupDesc(correctAnswer));
        answerField.setText("");
        updateHighScoreIfNeeded();
    }

    private void checkAnswer() {
        String userAnswer = answerField.getText();
        double delay;

        if (userAnswer.trim().equalsIgnoreCase(correctAnswer)) {
            score++;
            delay = 0.75;

            scoreLabel.setText(String.valueOf(score));
            questionLabel.getStyleClass().add("right");
        } else {
            score = 0;
            delay = 1.5;

            scoreLabel.setText(String.valueOf(score));
            questionLabel.getStyleClass().add("wrong");
        }

        questionLabel.setText(correctAnswer);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> {
            loadNextQuestion();
            questionLabel.getStyleClass().removeAll("right");
            questionLabel.getStyleClass().removeAll("wrong");
        }));
        timeline.play();
    }

    public void checkAndUpdateHighScore(int currentScore) {
        try {
            File highScoreFile = new File(PATH_SHUFFLE_GAME_HIGH_SCORE);
            if (!highScoreFile.exists()) {
                highScoreFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(highScoreFile));
            String highScoreString = reader.readLine();
            reader.close();

            if (highScoreString != null && !highScoreString.isEmpty()) {
                highScore = Integer.parseInt(highScoreString.trim());
            }

            if (currentScore > highScore) {
                highScore = currentScore;

                BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile));
                writer.write(String.valueOf(highScore));
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void updateHighScoreIfNeeded() {
        checkAndUpdateHighScore(score);
        highScoreLabel.setText(Integer.toString(highScore));
    }
}

