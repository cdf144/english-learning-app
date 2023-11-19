package org.nora.dictionary.game.GuessWord;

import java.util.*;

public class GuessWordGame {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
        List<String> imagePaths = Arrays.asList("one.png", "two.png", "three.png", "four.png", "five.png", "six.png", "seven.png", "eight.png", "nine.png", "ten.png");

        int score = 0;
        List<String> options = Arrays.asList("A", "B", "C", "D");

        Random random = new Random();

        for (int i = 0; i < words.size(); i++) {
            System.out.println("Câu hỏi: Đây là hình ảnh của từ gì?");
            System.out.println("Ảnh: " + imagePaths.get(i));

            int correctIndex = random.nextInt(4);
            int optionIndex = 0;

            for (int j = 0; j < 4; j++) {
                if (j == correctIndex) {
                    System.out.println(options.get(optionIndex) + ". " + words.get(i));
                } else {
                    int randomWordIndex;
                    do {
                        randomWordIndex = random.nextInt(words.size());
                    } while (randomWordIndex == i);

                    System.out.println(options.get(optionIndex) + ". " + words.get(randomWordIndex));
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
}