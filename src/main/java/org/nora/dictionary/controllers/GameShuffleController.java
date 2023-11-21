package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GameShuffleController {

    @FXML
    protected TextField questionField;
    @FXML
    protected TextField answerField;
    @FXML
    protected TextField correctField;
    @FXML
    protected Label scoreLabel;
    @FXML
    protected Label highScoreLabel;

    private List<String> wordList;
    private int score = 0;
    private int highScore = 0;
    protected String correctAnswer;

    public static final String PATH_SHUFFLE_GAME_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "GuessGame.txt";

    public static final String PATH_GUESS_GAME_HIGH_SCORE_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "shuffleGameHighScore.txt";

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

    public void initialize() {
        this.wordList = generateWordList(PATH_SHUFFLE_GAME_TXT);
        loadNextQuestion();
        scoreLabel.setText("0");
        updateHighScoreIfNeeded();
        highScoreLabel.setText(Integer.toString(highScore));

        answerField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                checkAnswer();
            }
        });

    }

    private void loadNextQuestion() {
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        correctAnswer = wordList.get(index);

        List<String> tempWordList = new ArrayList<>(wordList);
        tempWordList.remove(correctAnswer);
        String ques = generateRandomCharacter(correctAnswer);
        questionField.setText(ques);
        answerField.setText("");
        updateHighScoreIfNeeded();
    }
    public static String generateRandomCharacter(String s) {
        int n = s.length();
        Random random = new Random();
        String tmpRes = "";
        String res = "";
        List<Integer> tmp = new ArrayList<>();

        while (tmp.size() < n) {
            int tmpIndex = random.nextInt(n);
            if (!tmp.contains(tmpIndex)) {
                tmp.add(tmpIndex);
                tmpRes += s.charAt(tmpIndex) + " / ";
            }
        }
        for (int i = 0; i < tmpRes.length() - 3; i++) {
            res += tmpRes.charAt(i);
        }
        return res;
    }

    private void checkAnswer() {
        String userAnswer = answerField.getText();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            score += 5;
            scoreLabel.setText(String.valueOf(score));
            loadNextQuestion();
        } else {
            score -= 10;
            scoreLabel.setText(String.valueOf(score));
            loadNextQuestion();
        }
    }

    public void checkAndUpdateHighScore(int currentScore) {
        try {
            File highScoreFile = new File(PATH_GUESS_GAME_HIGH_SCORE_TXT);
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
            e.printStackTrace();
        }
    }

    public void updateHighScoreIfNeeded() {
        checkAndUpdateHighScore(score);
        highScoreLabel.setText(Integer.toString(highScore));
    }
}

