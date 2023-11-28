package org.nora.dictionary.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.nora.dictionary.utils.IOFileList;

import java.io.*;
import java.net.URL;
import java.util.*;

public class GameGuessWordController implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonB;
    @FXML
    private Button buttonC;
    @FXML
    private Button buttonD;
    @FXML
    private Label comboLabel;
    @FXML
    private Label longestComboLabel;

    private List<String> wordList;
    private List<String> imageList;
    private int combo = 0;
    private int longestCombo = 0;
    private String correctAnswer;

    private final List<Integer> recentlyUsedQuestions = new ArrayList<>();
    private static final int MINIMUM_GAP = 100;

    public static final String PATH_GUESS_GAME_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "guessGame.txt";

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

    private void generateWordList() throws IOException {
        IOFileList.loadFileToList(PATH_GUESS_GAME_TXT, wordList);
    }

    private void generateImageList(List<String> wordList) {
        for (String word : wordList) {
            String imagePath = PATH_GUESS_GAME_IMAGE + File.separator + word + ".jpg";
            imageList.add(imagePath);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.wordList = new ArrayList<>();
        this.imageList = new ArrayList<>();

        try {
            generateWordList();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        generateImageList(this.wordList);

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

        double delay;
        if (isCorrect) {
            combo++;
            delay = 0.75;

            clickedButton.getStyleClass().add("right");
            for (Button button : Arrays.asList(buttonA, buttonB, buttonC, buttonD)) {
                if (!button.equals(clickedButton)) {
                    button.setVisible(false);
                }
            }
        } else {
            combo = 0;
            delay = 1.5;

            clickedButton.getStyleClass().add("wrong");

            Button correctButton = null;
            for (Button button : Arrays.asList(buttonA, buttonB, buttonC, buttonD)) {
                if (correctAnswer.equalsIgnoreCase(button.getText())) {
                    correctButton = button;
                    break;
                }
            }

            if (correctButton != null) {
                correctButton.getStyleClass().add("right");
            }

            for (Button button : Arrays.asList(buttonA, buttonB, buttonC, buttonD)) {
                if (!button.equals(clickedButton) && !button.equals(correctButton)) {
                    button.setVisible(false);
                }
            }
        }

        comboLabel.setText(String.valueOf(combo));
        disableButtons();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> {
            loadNextQuestion();
            resetButtonColors();
            enableButtons();
        }));
        timeline.play();
    }

    private void resetButtonColors() {
        buttonA.getStyleClass().removeAll("wrong");
        buttonA.getStyleClass().removeAll("right");
        buttonB.getStyleClass().removeAll("wrong");
        buttonB.getStyleClass().removeAll("right");
        buttonC.getStyleClass().removeAll("wrong");
        buttonC.getStyleClass().removeAll("right");
        buttonD.getStyleClass().removeAll("wrong");
        buttonD.getStyleClass().removeAll("right");

        buttonA.setVisible(true);
        buttonB.setVisible(true);
        buttonC.setVisible(true);
        buttonD.setVisible(true);
    }

    private void disableButtons() {
        buttonA.setOnAction(null);
        buttonB.setOnAction(null);
        buttonC.setOnAction(null);
        buttonD.setOnAction(null);
    }

    private void enableButtons() {
        buttonA.setOnAction(this::handleAnswerButtonClick);
        buttonB.setOnAction(this::handleAnswerButtonClick);
        buttonC.setOnAction(this::handleAnswerButtonClick);
        buttonD.setOnAction(this::handleAnswerButtonClick);
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
            System.err.println(e.toString());
        }
    }

    public void updateLongestComboIfNeeded() {
        checkAndUpdateLongestCombo(combo);
        longestComboLabel.setText(Integer.toString(longestCombo));
    }
}