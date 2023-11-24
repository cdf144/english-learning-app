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
import org.nora.dictionary.game.Shuffle.ShuffleCore;
import org.nora.dictionary.management.DictionaryManagement;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class GameShuffleController extends ShuffleCore implements Initializable {
    @FXML
    private Label questionLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label hintLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label highScoreLabel;
    @FXML
    private Label warningLabel;

    private DictionaryManagement dictionary;
    private int DICTIONARY_SIZE;
    private int score = 0;
    private int highScore = 0;

    public static final String PATH_SHUFFLE_GAME_HIGH_SCORE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "shuffleGameHighScore.txt";
    public static final Pattern INVALID_CHARACTERS = Pattern.compile(
            "[^a-zA-Z]"
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

    public void onHintLabelClicked() {
        if (!hintLabel.getText().isEmpty()) {
            return;
        }

        hintLabel.getStyleClass().add("clicked");
        hintLabel.setText(generateHintWord(correctAnswer));
    }

    public void onAnswerFieldTyped() {
        String userAnswer = answerField.getText().trim();
        if (INVALID_CHARACTERS.matcher(userAnswer).find()) {
            warningLabel.setVisible(true);
            answerField.setOnKeyPressed(null);
        } else {
            warningLabel.setVisible(false);
            answerField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER && !answerField.getText().trim().isEmpty()) {
                    checkAnswer();
                }
            });
        }
    }

    private String generateHintWord(String word) {
        int numReplacement = word.length() / 2;
        if (word.length() > 7) {
            numReplacement--;
        }
        if (word.length() > 11) {
            numReplacement--;
        }

        Random random = new Random();
        Set<Integer> randomIndices = new HashSet<>();
        while (randomIndices.size() < numReplacement) {
            int r = random.nextInt(word.length());
            randomIndices.add(r);
        }

        StringBuilder hint = new StringBuilder(word);
        char[] hintWord = word.toCharArray();
        for (int index : randomIndices) {
            hintWord[index] = '_';
            hint.setCharAt(index, '_');
        }

        return hint.toString();
    }

    public String generateRandomCharacter(String s) {
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
                correctAnswer.length() < 4
                || INVALID_CHARACTERS.matcher(correctAnswer).find()
                || DictionaryApplication.dictionary.dictionaryLookupDesc(correctAnswer) == null
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
            delay = 3;

            scoreLabel.setText(String.valueOf(score));
            questionLabel.getStyleClass().add("wrong");
        }

        questionLabel.setText(correctAnswer);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> {
            loadNextQuestion();
            questionLabel.getStyleClass().removeAll("right");
            questionLabel.getStyleClass().removeAll("wrong");
            hintLabel.setText("");
            hintLabel.getStyleClass().removeAll("clicked");
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

