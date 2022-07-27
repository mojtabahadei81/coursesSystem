package ir.ac.kntu;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Repository repository = Repository.getInstance();
        User user1 = new User("mojtaba", "mojtabah81", "m.hade.1381@gmail.com", "13811121", "0025366408", "09035873180");
        User user2 = new User("zahra", "zahra86", "z.hadei.1386@gmail.com", "13861012", "0025340409", "09224676159");
        User user3 = new User("mostafa", "mostafa56", "mostafa.hadei@gmail.com", "40010303", "0026874551", "+989015299906");
        repository.addUser(user1);
        repository.addUser(user2);
        repository.addUser(user3);
        Course course1 = new Course("math", "ebn_e_sina", "1400", true, "it is important class",user1);
        Course course2 = new Course("shimi", "ebn_e_sina", "1400", true, "it is important class",user1);
        PrivateCourse course4 = new PrivateCourse("english", "avecina", "1400", true, "it is important class", user2, "12345");
        repository.addCourse(course1);
        repository.addCourse(course2);
        repository.addPrivateCourse(course4);
        user1.createCourse(course1);
        user1.createCourse(course2);
        user2.createCourse(course4);
        LocalDateTime localDateTime1 = LocalDateTime.of(2022, 5, 8, 0, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2022, 9, 10, 0, 0);
        LocalDateTime localDateTime3 = LocalDateTime.of(2022, 9, 11, 0, 0);
        Exercise exercise1 = new Exercise("tamrin1", "adslas", localDateTime1, localDateTime2, 0.8, localDateTime3);
        course1.addAnExercise(exercise1);
        course1.addAnExercise(new Exercise("tamrin2", "akjsdg", localDateTime1, localDateTime2, 0.8, localDateTime3));
        course1.addAnExercise(new Exercise("tamrin3", "aljksd akjshd", localDateTime1, localDateTime2, 0.8, localDateTime3));
        repository.addQuestionToQuestionBank(new Question("question1", 20, "asl dh a shjd", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question2", 21, "asl dh a", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question3", 22, "asl dh", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question4", 23, "asl", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question5", 24, "asl dh a shjd saln md", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question6", 25, "asl dh a shjd asd", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question7", 25, "asl dh a shjd asd kjgi", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question8", 25, "asl dh a shjd asd otftg", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question9", 25, "asl dh a shjd asd pppp", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question10", 25, "asl dh a shjd asd hhhh", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        repository.addQuestionToQuestionBank(new Question("question11", 25, "asl dh a shjd asd oooo", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        exercise1.addQuestion(new Question("question1", 20, "asl dh a shjd", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        exercise1.addQuestion(new Question("question2", 21, "asl dh a", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        exercise1.addQuestion(new Question("question3", 22, "asl dh", Question.DifficultyLevel.EASY, Question.QuestionType.EMPTY));
        course1.addAnStudent(user2);
        course2.addAnStudent(user3);
        UserInterface userInterface = new UserInterface();
        userInterface.startProgram();
    }
}