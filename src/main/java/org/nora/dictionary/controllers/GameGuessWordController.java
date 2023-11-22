package org.nora.dictionary.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;

import javafx.util.Duration;

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
    protected Label comboLabel;

    @FXML
    protected Label longestComboLabel;

    private List<String> wordList;
    private List<String> imageList;
    private int combo = 0;
    private int longestCombo = 0;
    protected String correctAnswer;

    private List<Integer> recentlyUsedQuestions = new ArrayList<>();
    private static final int MINIMUM_GAP = 30;

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

    public static final String PATH_GUESS_GAME_LONGEST_COMBO_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "guessGameLongestCombo.txt";

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
        comboLabel.setText("0");
        updateLongestComboIfNeeded();
        longestComboLabel.setText(Integer.toString(longestCombo));

        buttonA.setOnAction(this::handleAnswerButtonClick);
        buttonB.setOnAction(this::handleAnswerButtonClick);
        buttonC.setOnAction(this::handleAnswerButtonClick);
        buttonD.setOnAction(this::handleAnswerButtonClick);
    }

    private void loadNextQuestion() {
        Random random = new Random();
        int index;

        if (recentlyUsedQuestions.size() >= MINIMUM_GAP) {
            recentlyUsedQuestions.remove(0);
        }

        do {
            index = random.nextInt(imageList.size());
        } while (recentlyUsedQuestions.contains(index));

        recentlyUsedQuestions.add(index);

        String imagePath = imageList.get(index);
        correctAnswer = wordList.get(index);

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

        updateLongestComboIfNeeded();
    }

    @FXML
    private void handleAnswerButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String selectedAnswer = clickedButton.getText();
        boolean isCorrect = selectedAnswer.equals(correctAnswer);

        if (isCorrect) {
            combo += 1;
            clickedButton.setStyle("-fx-background-color: green;");
        } else {
            combo = 0;
            clickedButton.setStyle("-fx-background-color: red;");

            Button correctButton = null;
            for (Button button : Arrays.asList(buttonA, buttonB, buttonC, buttonD)) {
                if (correctAnswer.equalsIgnoreCase(button.getText())) {
                    correctButton = button;
                    break;
                }
            }

            if (correctButton != null) {
                correctButton.setStyle("-fx-background-color: green;");
            }
        }

        comboLabel.setText(String.valueOf(combo));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            loadNextQuestion();
            resetButtonColors();
        }));
        timeline.play();
    }

    private void resetButtonColors() {
        buttonA.setStyle("");
        buttonB.setStyle("");
        buttonC.setStyle("");
        buttonD.setStyle("");
    }


    public void checkAndUpdateLongestCombo(int currentCombo) {
        try {
            File longestComboFile = new File(PATH_GUESS_GAME_LONGEST_COMBO_TXT);
            if (!longestComboFile.exists()) {
                longestComboFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(longestComboFile));
            String longestComboString = reader.readLine();
            reader.close();

            if (longestComboString != null && !longestComboString.isEmpty()) {
                longestCombo = Integer.parseInt(longestComboString.trim());
            }

            if (currentCombo > longestCombo) {
                longestCombo = currentCombo;

                BufferedWriter writer = new BufferedWriter(new FileWriter(longestComboFile));
                writer.write(String.valueOf(longestCombo));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLongestComboIfNeeded() {
        checkAndUpdateLongestCombo(combo);
        longestComboLabel.setText(Integer.toString(longestCombo));
    }
}