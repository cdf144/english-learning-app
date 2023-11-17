package test;

import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;

public class TestAudio {
    public static void main(String[] args) {
        AudioGoogleAPI audioAPI = AudioGoogleAPI.getInstance();
        // Nhập từ cần audio ở đây >>
        String textToSpeak = "Welcome";
        String language = "en";

        try {
            InputStream audioStream = audioAPI.getAudio(textToSpeak, language);
            audioAPI.play(audioStream);
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
            System.out.println("Error occurred while playing audio.");
        }
    }
}