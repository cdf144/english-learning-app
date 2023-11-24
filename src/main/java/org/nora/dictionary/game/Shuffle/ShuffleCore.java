package org.nora.dictionary.game.Shuffle;

public abstract class ShuffleCore {
    protected String correctAnswer;

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public abstract String generateRandomCharacter(String s);
}
