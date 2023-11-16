package org.nora.dictionary.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleTransAPI {
    public static final String GOOGLE_TRANSLATE_URL = "http://translate.google.com/translate_a/t?";
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    private static final String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; "
            + "en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

    private static final Pattern WORD_PATTERN = Pattern.compile(
            "\\[\"(.*)\"]"
    );

    public static String translateWithInternetCheck(String textToTranslate) {
        try {
            return GoogleTransAPI.translate(textToTranslate, LANGUAGE.ENGLISH, LANGUAGE.VIETNAMESE);
        } catch (IOException e) {
            System.out.println("No Internet");
            return "Error during translation";
        }
    }

    public static String translate(String query, LANGUAGE srcLang, LANGUAGE destLang) throws IOException {
        String urlStr = generateTranslateURL(srcLang.toString(), destLang.toString(), query);
        URL url = new URL(urlStr);

        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", userAgent);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String translatedWord = "";
        Matcher matcher = WORD_PATTERN.matcher(response.toString());
        if (matcher.find()) {
            translatedWord = matcher.group(1);
        }

        return translatedWord.trim().replace("\\", "");
    }

    private static String generateTranslateURL(String sourceLanguage, String targetLanguage, String text) {
        return GOOGLE_TRANSLATE_URL + "client=gtrans" +
                "&sl=" + sourceLanguage +
                "&tl=" + targetLanguage +
                "&hl=" + targetLanguage + // The language of the UI
                "&tk=" + generateNewToken() +
                "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public enum LANGUAGE {
        ENGLISH("en"), VIETNAMESE("vi"), AUTO("auto");

        private String lang = "";

        LANGUAGE(String lang) {
            this.lang = lang;
        }

        @Override
        public String toString() {
            return this.lang;
        }
    }
}