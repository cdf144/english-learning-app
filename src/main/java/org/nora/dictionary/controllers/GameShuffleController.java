package org.nora.dictionary.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private UtilsController utils;

    private int score = 0;
    private int highScore = 0;

    private final List<String> recentlyUsedQuestions = new ArrayList<>();
    private static final int MINIMUM_GAP = 50;
    private static final int CHAR_LIMIT = 30;

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
        this.utils = new UtilsController();

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
        answerField.setOnKeyTyped(this::onAnswerFieldTyped);
    }

    public void onHintLabelClicked() {
        if (!hintLabel.getText().isEmpty()) {
            return;
        }

        hintLabel.getStyleClass().add("clicked");
        hintLabel.setText(generateHintWord(correctAnswer));
    }

    public void onAnswerFieldTyped(KeyEvent event) {
        String userAnswer = answerField.getText();
        if (userAnswer.length() > CHAR_LIMIT) {
            utils.showNotification("Warning", "Answer character limit reached (30 characters)");
            answerField.setText(userAnswer.substring(0, CHAR_LIMIT));
        }

        userAnswer = userAnswer.trim();
        if (INVALID_CHARACTERS.matcher(userAnswer).find()) {
            warningLabel.setVisible(true);
            answerField.setOnKeyPressed(null);
        } else {
            warningLabel.setVisible(false);
            answerField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER && !answerField.getText().trim().isEmpty()) {
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

    @Override
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

        for (int i = 1; i < charIndexList.size(); i++) {
            if (charIndexList.get(i).compareTo(charIndexList.get(i - 1)) < 0) {
                return shuffled.substring(0, shuffled.length() - 3);
            }
        }

        return generateRandomCharacter(s);
    }

    private void loadNextQuestion() {
        Random random = new Random();

        if (recentlyUsedQuestions.size() > MINIMUM_GAP) {
            recentlyUsedQuestions.remove(0);
        }

        int index;
        do {
            index = random.nextInt(DICTIONARY_SIZE);
            correctAnswer = this.dictionary.getDictionary().getWordList().get(index).getTarget();
        } while (
                correctAnswer.length() < 4
                || INVALID_CHARACTERS.matcher(correctAnswer).find()
                || DictionaryApplication.dictionary.dictionaryLookupDesc(correctAnswer) == null
                || recentlyUsedQuestions.contains(correctAnswer)
        );

        recentlyUsedQuestions.add(correctAnswer);

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
        answerField.setOnKeyPressed(null);
        answerField.setOnKeyTyped(null);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> {
            loadNextQuestion();

            answerField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER && !answerField.getText().trim().isEmpty()) {
                    checkAnswer();
                }
            });
            answerField.setOnKeyTyped(this::onAnswerFieldTyped);

            questionLabel.getStyleClass().removeAll("right");
            questionLabel.getStyleClass().removeAll("wrong");
            hintLabel.setText("");
            hintLabel.getStyleClass().removeAll("clicked");
        }));
        timeline.play();
    }

    private void checkAndUpdateHighScore(int currentScore) {
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

    private void updateHighScoreIfNeeded() {
        checkAndUpdateHighScore(score);
        highScoreLabel.setText(Integer.toString(highScore));
    }
}

