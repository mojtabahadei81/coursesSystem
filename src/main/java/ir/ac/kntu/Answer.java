package ir.ac.kntu;

import java.time.LocalDateTime;
import java.util.Date;

public class Answer {

    private final String usernameOfTransmitterUser;

    private final Question theQuestion;

    private final LocalDateTime postageDate;

    private double delayCoefficient;

    private double score;

    private double scoreIncludingDelay;

    private String answerText;

    public Answer(String usernameOfTransmitterUser, Question theQuestion, double delayCoefficient, String answerText) {
        this.usernameOfTransmitterUser = usernameOfTransmitterUser;
        this.theQuestion = theQuestion;
        this.postageDate = LocalDateTime.now();
        this.delayCoefficient = delayCoefficient;
        this.answerText = answerText;
    }

    public String getUsernameOfTransmitterUser() {
        return usernameOfTransmitterUser;
    }

    public Question getTheQuestion() {
        return theQuestion;
    }

    public LocalDateTime getPostageDate() {
        return postageDate;
    }

    public double getDelayCoefficient() {
        return delayCoefficient;
    }

    public void setDelayCoefficient(double delayCoefficient) {
        this.delayCoefficient = delayCoefficient;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScoreIncludingDelay() {
        return scoreIncludingDelay;
    }

    public void setScoreIncludingDelay(double scoreIncludingDelay) {
        this.scoreIncludingDelay = scoreIncludingDelay;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "answer: " +  answerText;
    }
}
