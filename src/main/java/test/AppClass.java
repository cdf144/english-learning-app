package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import test.GoogleTransAPI;
import test.GoogleTransAPI.LANGUAGE;

public class AppClass {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Enter text to translate (enter 0 to stop): ");
                String textToTranslate = reader.readLine();

                if (textToTranslate.equals("0")) {
                    System.out.println("Program stopped.");
                    break;
                }

                String translation = translateWithInternetCheck(textToTranslate);
                System.out.println("Translation: " + translation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String translateWithInternetCheck(String textToTranslate) {
        try {
            return GoogleTransAPI.translate(textToTranslate, LANGUAGE.ENGLISH, LANGUAGE.VIETNAMESE);
        } catch (IOException e) {
            System.out.println("No Internet");
            return "Error during translation";
        }
    }
}