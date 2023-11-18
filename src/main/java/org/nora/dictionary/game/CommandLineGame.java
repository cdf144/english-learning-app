package org.nora.dictionary.game;

import org.nora.dictionary.entities.Word;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CommandLineGame {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printChars(List<Character> list) {
        for (char c : list) {
            System.out.print(" / " + c);
        }
        System.out.println();
    }

    public static void startGame(List<Word> wordList) {
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
                String target = wordList.get(r).getTarget();
                String explain = wordList.get(r).getExplain();

                printChars(Core.shuffle(target));
                System.out.println("Word meaning: " + explain);
                System.out.print("Your answer: ");
                scanner.nextLine();

                int tries = 3;
                while (tries > 0) {
                    String answer = scanner.nextLine();
                    if (answer.equals(target)) {
                        System.out.println("Correct!");
                        break;
                    } else {
                        System.out.print("Incorrect! Try again: ");
                        tries--;

                        if (tries == 0) {
                            System.out.println("The correct word is '" + target + "'");
                            System.out.println("You have failed!");
                        }
                    }
                }
            }

            printMenu();
        }
    }

    public static void printMenu() {
        System.out.println("  [0] Exit");
        System.out.println("  [1] Play");
    }
}
