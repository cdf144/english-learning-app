package org.nora.dictionary.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class GameShuffleController implements Initializable {
    @FXML
    private Label questionLabel;
    @FXML
    protected TextField answerField;
    @FXML
    protected Label scoreLabel;
    @FXML
    protected Label highScoreLabel;
    protected String correctAnswer;
    private List<String> wordList;
    private int score = 0;
    private int highScore = 0;

    public static final String PATH_SHUFFLE_GAME_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "guessGame.txt";
    public static final String PATH_SHUFFLE_GAME_HIGH_SCORE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "shuffleGameHighScore.txt";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.wordList = generateWordList(PATH_SHUFFLE_GAME_TXT);
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

    private List<String> generateWordList(String filePath) {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return words;
    }

    private void loadNextQuestion() {
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        correctAnswer = wordList.get(index);

        List<String> tempWordList = new ArrayList<>(wordList);
        tempWordList.remove(correctAnswer);
        String ques = generateRandomCharacter(correctAnswer);
        questionLabel.setText(ques);
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
            questionLabel.setStyle("-fx-background-color: green;");
            questionLabel.setText("Correct");
        } else {
            score = 0;
            delay = 1.5;

            scoreLabel.setText(String.valueOf(score));
            questionLabel.setStyle("-fx-background-color: red;");
            questionLabel.setText(correctAnswer);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> {
            loadNextQuestion();
            questionLabel.setStyle("-fx-background-color: white;");
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

