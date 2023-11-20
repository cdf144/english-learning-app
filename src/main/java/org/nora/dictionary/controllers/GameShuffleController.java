package org.nora.dictionary.controllers;

import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class GameShuffleController {
    @FXML
    protected Label scoreLabel;

    private List<String> wordList;
    private int score = 0;
    protected String correctAnswer;

    public static final String PATH_SHUFFLE_GAME_TXT = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "dictionryAdvanced.txt";

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


    }

    private void loadNextQuestion() {
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        correctAnswer = wordList.get(index);


        List<String> answers = new ArrayList<>();
        answers.add(correctAnswer);

        List<String> tempWordList = new ArrayList<>(wordList);
        tempWordList.remove(correctAnswer);

        Collections.shuffle(tempWordList);


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
}
