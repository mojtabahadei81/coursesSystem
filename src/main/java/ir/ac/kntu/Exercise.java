package ir.ac.kntu;

import java.time.LocalDateTime;
import java.util.*;

public class Exercise {

    private String name;

    private String generalDescription;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime delayedSendingTime;

    private double delayCoefficient;

    private ArrayList<Question> questions;

    private Map<User, HashMap<LocalDateTime,HashMap<Question,Answer>>> answersMap;

    private Map<User, HashMap<Question, Answer>> lastAnswerForEachStudent;

    private Map<Question, Answer> correctAnswers;

    private LocalDateTime creationTime;


    public Exercise(String name, String generalDescription, LocalDateTime startDate, LocalDateTime endDate,
                    double delayCoefficient, LocalDateTime delayedSendingTime) {
        creationTime = LocalDateTime.now();
        if(startDate.isAfter(endDate) || endDate.isAfter(delayedSendingTime)){
            System.out.println("The dates entered are incorrect.");
            return;
        }
        this.name = name;
        this.generalDescription = generalDescription;
        this.startTime = startDate;
        this.endTime = endDate;
        this.delayedSendingTime = delayedSendingTime;
        this.delayCoefficient = delayCoefficient;
        questions = new ArrayList<>();
        answersMap = new HashMap<>();
        correctAnswers = new HashMap<>();
        lastAnswerForEachStudent = new HashMap<>();
    }

    public Exercise(Exercise a) {
        this.name = a.name;
        this.generalDescription = a.generalDescription;
        this.startTime = a.startTime;
        this.endTime = a.endTime;
        this.delayCoefficient = a.delayCoefficient;
        this.delayedSendingTime = a.delayedSendingTime;
        questions = a.getQuestions();
        answersMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public LocalDateTime getStartDate() {
        return LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(),
                startTime.getHour(), startTime.getMinute());
    }

    public void setStartDate(LocalDateTime startDate) {
        if(startDate.isAfter(creationTime) && startDate.isBefore(endTime)){
            this.startTime = startDate;
        }
    }

    public LocalDateTime getEndDate() {
        return LocalDateTime.of(endTime.getYear(), endTime.getMonth(), endTime.getDayOfMonth(),
                endTime.getHour(), endTime.getMinute());
    }

    public void setEndDate(LocalDateTime endDate) {
        if(endDate.isAfter(startTime) && endDate.isBefore(delayedSendingTime)){
            this.endTime = endDate;
        }
    }

    public double getDelayCoefficient() {
        return delayCoefficient;
    }

    public void setDelayCoefficient(double delayCoefficient) {
        this.delayCoefficient = delayCoefficient;
    }

    public LocalDateTime getDelayedSendingTime() {
        return LocalDateTime.of(delayedSendingTime.getYear(), delayedSendingTime.getMonth(), delayedSendingTime.getDayOfMonth(),
                delayedSendingTime.getHour(), delayedSendingTime.getMinute());
    }

    public void setDelayedSendingTime(LocalDateTime delayedSendingTime) {
        if(delayedSendingTime.isAfter(endTime)){
            this.delayedSendingTime = delayedSendingTime;
        }
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> copyOfQuestions = new ArrayList<>();
        for (Question a : questions) {
            copyOfQuestions.add(new Question(a));
        }
        return copyOfQuestions;
    }

    public void addQuestions(ArrayList<Question> questions){
        for (Question a: questions) {
            if (!questions.contains(a)) {
                questions.add(a);
            }
        }
    }

    public void addQuestion(Question question) {
        for (Question q : questions) {
            if (q.equals(question)) {
                System.out.println("this question added to this exercise before.");
                return;
            } else if (q.getName().equals(question.getName())) {
                System.out.println("There is another question with this name in the questions of this exercise.\n" +
                        "please rename the question and enter it again");
                return;
            }
        }
        questions.add(question);
    }

    public void answerToTheExercise(HashMap<Question, Answer> answers, User student) {
        LocalDateTime todayDate = LocalDateTime.now();
        if(todayDate.isAfter(delayedSendingTime)) {
            System.out.println("The exercise deadline has expired");
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        if (this.answersMap.containsKey(student)) {
            this.answersMap.get(student).put(now,answers);
        } else {
            HashMap<LocalDateTime, HashMap<Question, Answer>> innerMap = new HashMap<>();
            innerMap.put(now, answers);
            this.answersMap.put(student, innerMap);
        }
        lastAnswerForEachStudent.remove(student);
        lastAnswerForEachStudent.put(student, answers);
    }

    public HashMap<Question, Answer> getLastAnswerOfAStudent(User student) {
        return lastAnswerForEachStudent.get(student);
    }

    public void setCorrectAnswerOfAQuestion(Question question, Answer answer) {
        correctAnswers.remove(question);
        correctAnswers.put(question, answer);
    }

    public void deleteAQuestion(Question question) {
        questions.remove(question);
        correctAnswers.remove(question);
    }

    public HashMap<LocalDateTime, HashMap<Question, Answer>> getAnswerListOfAStudent(User student) {
        if (!answersMap.containsKey(student)){
            System.out.println("You did not answer to this exercise.");
            return null;
        } else {
            HashMap<LocalDateTime, HashMap<Question, Answer>> copyMap = new HashMap<>();
            for (Map.Entry<LocalDateTime, HashMap<Question, Answer>> entry : answersMap.get(student).entrySet()) {
                copyMap.put(entry.getKey(), entry.getValue());
            }
            return copyMap;
        }
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "\n     name: " + name +
                "\n     general description:" + generalDescription +
                "\n     start date: " + startTime +
                "\n     end date: " + endTime +
                "\n     delayedSendingTime: " + delayedSendingTime +
                "\n     delayCoefficient: " + delayCoefficient + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercise)) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        return Objects.equals(name, exercise.name) && Objects.equals(generalDescription, exercise.generalDescription) && Objects.equals(startTime, exercise.startTime) && Objects.equals(endTime, exercise.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, generalDescription, startTime, endTime);
    }
}