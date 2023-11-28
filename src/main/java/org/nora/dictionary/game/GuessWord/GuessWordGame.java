package org.nora.dictionary.game.GuessWord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GuessWordGame {

    private final List<String> wordList;
    private final List<String> imageList;
    private int score;

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

    public GuessWordGame() {
        this.wordList = generateWordList();
        this.imageList = generateImageList(this.wordList);
        this.score = 0;
    }

    private List<String> generateWordList() {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_GUESS_GAME_TXT))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return words;
    }

    private List<String> generateImageList(List<String> wordList) {
        List<String> images = new ArrayList<>();

        for (String word : wordList) {
            String imagePath = PATH_GUESS_GAME_IMAGE + "/" + word + ".jpg";
            images.add(imagePath);
        }

        return images;
    }

    public void startGame() {
        List<String> options = List.of("A", "B", "C", "D");
        Random random = new Random();

        for (int i = 0; i < wordList.size(); i++) {
            System.out.println("Câu hỏi: Đây là hình ảnh của từ gì?");
            System.out.println("Ảnh: " + imageList.get(i));

            int correctIndex = random.nextInt(4);
            int optionIndex = 0;

            for (int j = 0; j < 4; j++) {
                if (j == correctIndex) {
                    System.out.println(options.get(optionIndex) + ". " + wordList.get(i));
                } else {
                    int randomWordIndex;
                    do {
                        randomWordIndex = random.nextInt(wordList.size());
                    } while (randomWordIndex == i);

                    System.out.println(options.get(optionIndex) + ". " + wordList.get(randomWordIndex));
                }
                optionIndex++;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập câu trả lời (A/B/C/D): ");
            String userAnswer = scanner.nextLine().toUpperCase();

            if (userAnswer.equals(options.get(correctIndex))) {
                score += 5;
                System.out.println("Chính xác! Điểm của bạn: " + score);
            } else {
                score -= 10;
                System.out.println("Sai rồi! Điểm của bạn: " + score);
            }
        }

        System.out.println("Trò chơi kết thúc. Điểm số cuối cùng của bạn: " + score);
    }

    public static void main(String[] args) {
        GuessWordGame game = new GuessWordGame();
        game.startGame();
    }
}