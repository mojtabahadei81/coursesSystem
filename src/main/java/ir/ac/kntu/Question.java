package ir.ac.kntu;

import java.util.Objects;

public class Question {

    public enum QuestionType{TEST, ESSAY, EMPTY, SHORT_ANSWER}

    public enum DifficultyLevel{EASY, MEDIUM, HARD, VERY_HARD}

    private String name;

    private int score;

    private String questionText;

    private DifficultyLevel difficultyLevel;

    private QuestionType questionType;

    private Answer correctAnswer = null;

    public Question(String name, int score, String questionText, DifficultyLevel difficultyLevel, QuestionType questionType) {
        this.name = name;
        this.score = score;
        this.questionText = questionText;
        this.difficultyLevel = difficultyLevel;
        this.questionType = questionType;
    }

    public Question(Question a) {
        this.name = a.name;
        this.score = a.score;
        this.questionText = a.questionText;
        this.difficultyLevel = a.difficultyLevel;
        this.questionType = a.questionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "---------------------------------------------------------------------------------" + "\n" +
                name + " | difficulty level:" + difficultyLevel + " | score:"+ score + "\n" +
                questionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(questionText, question.questionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText);
    }
}
