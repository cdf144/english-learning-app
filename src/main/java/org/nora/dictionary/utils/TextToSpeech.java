package org.nora.dictionary.utils;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

public class TextToSpeech {
    private static Synthesizer synthesizer;

    public static void speak(String word) {
        if (word.isEmpty()) {
            return;
        }

        try {
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"
            );

            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();

            synthesizer.speakPlainText(word, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public static void deallocateSynthesizer() {
        if (synthesizer == null) {
            return;
        }

        try {
            synthesizer.deallocate();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
