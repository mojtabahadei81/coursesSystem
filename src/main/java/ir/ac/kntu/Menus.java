package ir.ac.kntu;

public class Menus {
    public static void printStartMenu() {
        System.out.println("""
                1: login
                2: signup
                if you have an account enter 1 otherwise enter 2 to create an account""");
    }

    public static void printUserMenu() {
        System.out.println("""
                1: search user
                2: search course
                3: create a class
                4: delete a class
                5: enroll in a class
                6: enter a class
                7: view questionBank
                8: add question to questionBank
                9: log out""");
    }

    public static void printPresenterCourseMenu() {
        System.out.println("""
                1: add a student in this class.
                2: create an exercise for this class.
                3: delete an exercise.
                4: enter an exercise.
                5: exit from this course.""");
    }

    public static void printStudentCourseMenu() {
        System.out.println("""
                1: enter an exercise.
                2: View the started exercises.
                3: exit from this course.""");
    }

    public static void printPresenterExerciseMenu() {
        System.out.println("""
                1: view scoreboard.
                2: create and add question.
                3: add question from question bank.
                4: record correct answers.
                5: edit this exercise.
                6: delete question.
                7: grade the answers of students.
                8: exit from this exercise.""");
    }

    public static void printStudentExerciseMenu() {
        System.out.println("""
                1: view scoreboard.
                2: view and answer questions of this exercise.
                3: view uploaded answers.
                4: exit from this exercise.""");
    }

    public static void courseEditMenu() {}

}
