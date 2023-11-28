package org.nora.dictionary.commandline;

import org.nora.dictionary.entities.Word;
import org.nora.dictionary.game.Shuffle.ShuffleCore;

import java.util.*;

public class CommandLineGame extends ShuffleCore {
    private static final Scanner scanner = new Scanner(System.in);

    public static void startGame(List<Word> wordList) {
        CommandLineGame cmdGame = new CommandLineGame();

        System.out.println("Welcome to Dictionary Command Line game!");
        printMenu();
        Random random = new Random();
        while (true) {
            System.out.print("Your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("PLEASE ENTER AN INTEGER!");
                scanner.next();
                continue;
            }

            if (choice == 0) {
                System.out.println("Exited game.");
                return;
            } else {
                int r = random.nextInt(wordList.size());

                cmdGame.setCorrectAnswer(wordList.get(r).getTarget());
                String explain = wordList.get(r).getExplain();

                printChars(cmdGame.generateRandomCharacter(cmdGame.correctAnswer));
                System.out.println("Word meaning: " + explain);
                System.out.print("Your answer: ");
                scanner.nextLine();

                int tries = 3;
                while (tries > 0) {
                    String answer = scanner.nextLine();
                    if (answer.equals(cmdGame.correctAnswer)) {
                        System.out.println("Correct!");
                        break;
                    } else {
                        System.out.print("Incorrect! Try again: ");
                        tries--;

                        if (tries == 0) {
                            System.out.println("The correct word is '" + cmdGame.correctAnswer + "'");
                            System.out.println("You have failed!");
                        }
                    }
                }
            }

            printMenu();
        }
    }

    public static void printChars(String s) {
        for (char c : s.toCharArray()) {
            System.out.print(" / " + c);
        }
        System.out.println();
    }

    public static void printMenu() {
        System.out.println("  [0] Exit");
        System.out.println("  [1] Play");
    }

    @Override
    public String generateRandomCharacter(String s) {
        List<Character> list = new ArrayList<>();
        for (char c : s.toCharArray()) {
            list.add(c);
        }

        Collections.shuffle(list);
        StringBuilder shuffled = new StringBuilder();
        for (char c : list) {
            shuffled.append(c);
        }

        return shuffled.toString();
    }
}
