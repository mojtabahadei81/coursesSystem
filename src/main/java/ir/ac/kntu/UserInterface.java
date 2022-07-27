package ir.ac.kntu;

import java.time.LocalDateTime;
import java.util.*;

public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    private final Repository repository = Repository.getInstance();

    private User loggedUser = null;

    private Course loggedCourse = null;

    private Exercise loggedExercise = null;

    private LocalDateTime loginTime = null;

    public enum StartMenu {LOGIN, SIGNUP}

    public void startProgram() {
        Menus.printStartMenu();
        int userInput = scanner.nextInt();
        if (userInput < 1 || userInput > 2) {
            System.out.println("The number entered is incorrect.");
            startProgram();
        } else {
            StartMenu[] startMenus = StartMenu.values();
            StartMenu option;
            option = startMenus[userInput - 1];
            switch (option) {
                case LOGIN -> login();
                case SIGNUP -> signup();
                default -> {
                }
            }
        }
    }

    public void login() {
        String username;
        String password;
        System.out.println("please enter your username and password");
        System.out.print("username: ");
        username = scanner.next();
        User switchedUser = null;
        for (User s : repository.getUsers()) {
            if (username.equals(s.getUserName())) {
                switchedUser = s;
                break;
            }
        }
        if (switchedUser == null) {
            System.out.println("The username entered is incorrect");
            startProgram();
            return;
        }
        System.out.print("password: ");
        password = scanner.next();
        if (password.equals(switchedUser.getPassword())) {
            loggedUser = repository.getUser(switchedUser);
            loginTime = LocalDateTime.now();
            System.out.println("you are logged in.\nlogin Time: " + loginTime);
            displayAndPerformLoggedInUserOptions();
        } else {
            startProgram();
        }
    }

    public void signup() {
        System.out.println("please enter the requested information.");
        System.out.print("name: ");
        String name = scanner.next();
        System.out.print("username: ");
        String userName = scanner.next();
        System.out.print("nationalId: ");
        String nationalId = scanner.next();
        if (!User.isCorrectNationalId(nationalId)) {
            System.out.println("entered nationalId is incorrect. The national code must be 10 digits long");
            startProgram();
            return;
        }
        System.out.print("mobile number: ");
        String mobileNumber = scanner.next();
        if (!User.isCorrectMobileNumber(mobileNumber)) {
            System.out.println("entered mobile number is incorrect.\n" + "The mobile number must follow one of the following two patterns");
            System.out.println("(+989*********) or (09*********)");
            displayAndPerformLoggedInUserOptions();
            return;
        }
        System.out.print("email: ");
        String email = scanner.next();
        if (!User.isCorrectEmail(email)) {
            System.out.println("entered email is incorrect. The email must follow (.+@.+(.com|.ir))");
            displayAndPerformLoggedInUserOptions();
            return;
        }
        System.out.print("password: ");
        String password = scanner.next();
        System.out.println(new User(name, userName, email, password, nationalId, mobileNumber));
        repository.addUser(new User(name, userName, email, password, nationalId, mobileNumber));
        startProgram();
    }

    enum LoggedInUserOption {
        SEARCH_USER, SEARCH_COURSE, CREATE_A_COURSE, DELETE_A_COURSE, ENROLL,
        ENTER_A_CLASS, VIEW_QUESTIONBANK, ADD_QUESTION_TO_QUESTIONBANK, EXIT
    }

    public void displayAndPerformLoggedInUserOptions() {
        Menus.printUserMenu();
        int userInput = scanner.nextInt();
        if (userInput > 9 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
            displayAndPerformLoggedInUserOptions();
        } else {
            LoggedInUserOption[] options = LoggedInUserOption.values();
            LoggedInUserOption option;
            option = options[userInput - 1];
            switch (option) {
                case SEARCH_USER -> searchUser();
                case SEARCH_COURSE -> searchCourse();
                case CREATE_A_COURSE -> createACourse();
                case DELETE_A_COURSE -> deleteAClass();
                case ENROLL -> enrollInACourse();
                case ENTER_A_CLASS -> enterAClass();
                case VIEW_QUESTIONBANK -> viewQuestionBank();
                case ADD_QUESTION_TO_QUESTIONBANK -> createAndAddQuestionToQuestionBank();
                case EXIT -> {
                    loggedUser = null;
                    loginTime = null;
                    startProgram();
                }
                default -> {
                }
            }
        }
    }

    public void viewQuestionBank() {
        boolean continues = true;
        int j = 0;
        do {
            for (int i = j; i < j + 10; i++) {
                if (i == repository.getQuestionBank().size()) {
                    System.out.println("We reached the end of the question bank questions.\n press ENTER to continue.");
                    scanner.nextLine();
                    scanner.nextLine();
                    displayAndPerformLoggedInUserOptions();
                    return;
                }
                System.out.println((i + 1) + ":    " + repository.getQuestionBank().get(i));
            }
            j += 10;
            System.out.println("Want to see the next 10 questions? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                continues = false;
            }
        } while (continues);
        displayAndPerformLoggedInUserOptions();
    }

    public void createAndAddQuestionToQuestionBank() {
        boolean continues = true;
        do {
            Question question = createQuestion();
            if (question != null) {
                repository.addQuestionToQuestionBank(question);
            }
            System.out.println("want to create another question? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                continues = false;
            }
        } while (continues);
        displayAndPerformLoggedInUserOptions();
    }

    public void searchUser() {
        System.out.print("please enter nationalId or email to search user: ");
        String string = scanner.next();
        if (!User.isCorrectNationalId(string) && !User.isCorrectEmail(string)) {
            System.out.println("The word you entered is neither an email nor a national code");
        }
        for (User s : repository.getUsers()) {
            if (string.equals(s.getNationalId())) {
                System.out.println(s);
                displayAndPerformLoggedInUserOptions();
                return;
            }
        }
        for (User s : repository.getUsers()) {
            if (string.equals(s.getEmail())) {
                System.out.println(s);
                displayAndPerformLoggedInUserOptions();
                return;
            }
        }
        System.out.println("There is no user with this email or nationalId");
        displayAndPerformLoggedInUserOptions();
    }

    public void searchCourse() {
        System.out.print("please enter (course name) or (institute name) or (presenter name) to search course: ");
        String string = scanner.next();
        int flag = 0;
        for (Course s : repository.getOpenCourses()) {
            if (string.equals(s.getName())) {
                System.out.println(s);
                flag = 1;
            }
        }
        for (Course s : repository.getOpenCourses()) {
            if (string.equals(s.getPresenterName())) {
                System.out.println(s);
                flag = 1;
            }
        }
        for (Course s : repository.getOpenCourses()) {
            if (string.equals(s.getInstituteName())) {
                System.out.println(s);
                flag = 1;
            }
        }
        if (flag == 0) {
            System.out.println("There is no course with the information you entered");
        }
        displayAndPerformLoggedInUserOptions();
    }

    public void createACourse() {
        System.out.println("Please enter the requested information: ");
        System.out.print("name: ");
        String name = scanner.next();
        System.out.print("name of educational institution: ");
        String instituteName = scanner.next();
        String academicYear = String.valueOf(loginTime.getYear());
        System.out.println("your class is open? if is open type (yes). if isn't type (no)");
        String string = scanner.next();
        boolean isOpen = string.equals("yes");
        System.out.println("course description: ");
        scanner.nextLine();
        String courseDescription = scanner.nextLine();
        User presenter = loggedUser;
        System.out.println("your class is private? if is private type (yes) and then enter password. if isn't type (no)");
        String string2 = scanner.next();
        if (string2.equals("yes")) {
            String password;
            System.out.print("password: ");
            password = scanner.next();
            repository.addPrivateCourse(new PrivateCourse(name, instituteName, academicYear, isOpen, courseDescription, presenter, password));
            loggedUser.createPrivateCourse(new PrivateCourse(name, instituteName, academicYear, isOpen, courseDescription, presenter, password));
        } else {
            repository.addCourse(new Course(name, instituteName, academicYear, isOpen, courseDescription, presenter));
            loggedUser.createCourse(new Course(name, instituteName, academicYear, isOpen, courseDescription, presenter));
        }
        //System.out.println(loggedUser.getAllOwnerClasses().get(loggedUser.getAllOwnerClasses().size()-1));
        displayAndPerformLoggedInUserOptions();
    }

    public void deleteAClass() {
        System.out.println("please enter (class name) and (institute name) and (academic year) to delete class.");
        System.out.print("class name: ");
        String className = scanner.next();
        System.out.print("institute name: ");
        String instituteName = scanner.next();
        System.out.print("academic year: ");
        String academicYear = scanner.next();
        for (Course a : loggedUser.getAllOwnerClasses()) {
            if (a.getName().equals(className) && a.getInstituteName().equals(instituteName) && a.getAcademicYear().equals(academicYear)) {
                loggedUser.deleteAClass(a);
                repository.deleteAClass(a);
                displayAndPerformLoggedInUserOptions();
                return;
            }
        }
        System.out.println("There are no courses with these specifications");
        displayAndPerformLoggedInUserOptions();
    }

    public void enrollInACourse() {
        System.out.println("enter (class name) and (institute name) and (academic year) to enroll in the course.");
        System.out.print("class name: ");
        String className = scanner.next();
        System.out.print("institute name: ");
        String instituteName = scanner.next();
        System.out.print("academic year: ");
        String academicYear = scanner.next();
        boolean isCreatedPrivateCourse = enrollInPrivateCourse(className, instituteName, academicYear);
        boolean isCreatedCourse = enrollInPublicCourse(className, instituteName, academicYear);
        if (!isCreatedPrivateCourse && !isCreatedCourse) {
            System.out.println("There are no classes with these information.");
        }
        displayAndPerformLoggedInUserOptions();
    }

    public boolean enrollInPrivateCourse(String className, String instituteName, String academicYear) {
        for (PrivateCourse a : repository.getOpenPrivateCourses()) {
            if (a.getName().equals(className) && a.getInstituteName().equals(instituteName) && a.getAcademicYear().equals(academicYear)) {
                System.out.println("enter room password to enroll in this class");
                String string = scanner.next();
                if (a.enteredPasswordIsCorrect(string)) {
                    repository.getPrivateCourse(a).addAnStudent(loggedUser);
                    loggedUser.enrollInAClass(a);
                } else {
                    System.out.println("password you entered isn't correct.");
                }
                return true;
            }
        }
        return false;
    }

    public boolean enrollInPublicCourse(String className, String instituteName, String academicYear) {
        for (Course a : repository.getOpenCourses()) {
            if (a.getName().equals(className) && a.getInstituteName().equals(instituteName) && a.getAcademicYear().equals(academicYear)) {
                repository.getPublicCourse(a).addAnStudent(loggedUser);
                loggedUser.enrollInAClass(a);
                displayAndPerformLoggedInUserOptions();
                return true;
            }
        }
        return false;
    }

    public void enterAClass() {
        System.out.println("enter (class name) and (institute name) and (academic year) to enroll in the course");
        System.out.print("class name: ");
        String className = scanner.next();
        System.out.print("institute name: ");
        String instituteName = scanner.next();
        System.out.print("academic year: ");
        String academicYear = scanner.next();
        for (Course a : repository.getAllCourses()) {
            if (a.getName().equals(className) && a.getInstituteName().equals(instituteName) && a.getAcademicYear().equals(academicYear)) {
                if (loggedUser.equals(a.getPresenter())) {
                    loggedCourse = repository.getCourse(a);
                    System.out.println("You have entered the " + className + " class as a presenter.");
                    displayAndPerformEnteredUserInClassAsPresenterOptions();
                    return;
                }
                for (User b : a.getStudents()) {
                    if (b.equals(loggedUser)) {
                        loggedCourse = repository.getCourse(a);
                        System.out.println("You have entered the " + className + " class as a student.");
                        displayAndPerformEnteredUserInClassAsStudentOptions();
                        return;
                    }
                }
                System.out.println("You are not enrolled in this class");
                displayAndPerformLoggedInUserOptions();
                return;
            }
        }
        System.out.println("There are no classes with these information");
        displayAndPerformLoggedInUserOptions();
    }

    public enum EnteredCoursePresenterOptions {ADD_STUDENT, ADD_EXERCISE, DELETE_EXERCISE, ENTER_EXERCISE, GRADE_THE_ANSWERS, EXIT_FROM_THIS_COURSE}

    public void displayAndPerformEnteredUserInClassAsPresenterOptions() {
        Menus.printPresenterCourseMenu();
        int userInput = scanner.nextInt();
        if (userInput > 5 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
            displayAndPerformEnteredUserInClassAsPresenterOptions();
        } else {
            EnteredCoursePresenterOptions[] options = EnteredCoursePresenterOptions.values();
            EnteredCoursePresenterOptions option;
            option = options[userInput - 1];
            switch (option) {
                case ADD_STUDENT -> addStudentInThisClass();
                case ADD_EXERCISE -> createAndAddAnExercise();
                case DELETE_EXERCISE -> deleteAnExercise();
                case ENTER_EXERCISE -> enterAnExerciseAsPresenter();
                case EXIT_FROM_THIS_COURSE -> {
                    loggedCourse = null;
                    displayAndPerformLoggedInUserOptions();
                }
                default -> {
                }
            }
        }
    }

    public void addStudentInThisClass() {
        System.out.println("please enter the email of the student you want to add to the class.");
        String email = scanner.next();
        if (!User.isCorrectEmail(email)) {
            System.out.println("the email you entered is incorrect.");
            displayAndPerformEnteredUserInClassAsPresenterOptions();
            return;
        }
        for (User a : repository.getUsers()) {
            if (a.getEmail().equals(email)) {
                if (a.equals(loggedUser)) {
                    System.out.println("This email is yours. You can not enroll yourself in this class as a student.");
                } else if (loggedCourse.getStudents().contains(a)) {
                    System.out.println("This student is already enrolled in the class.");
                } else {
                    loggedCourse.addAnStudent(a);
                }
                displayAndPerformEnteredUserInClassAsPresenterOptions();
            }
        }
    }

    public void createAndAddAnExercise() {
        System.out.print("name: ");
        String name = scanner.next();
        System.out.println("general description: ");
        scanner.nextLine();
        String generalDescription = scanner.nextLine();
        System.out.print("delay coefficient: ");
        double delayCoefficient = scanner.nextDouble();
        System.out.println("please enter start date of this exercise");
        LocalDateTime startTime = getTimeFromInput();
        System.out.println("please enter end date of this exercise");
        LocalDateTime endTime = getTimeFromInput();
        System.out.println("please enter end date With delay of this exercise");
        LocalDateTime endTimeWithDelay = getTimeFromInput();
        Exercise exercise = new Exercise(name, generalDescription, startTime, endTime, delayCoefficient, endTimeWithDelay);
        if (exercise != null) {
            loggedCourse.addAnExercise(exercise);
        }
        displayAndPerformEnteredUserInClassAsPresenterOptions();
    }

    public LocalDateTime getTimeFromInput() {
        LocalDateTime time = LocalDateTime.now();
        System.out.print("month: ");
        int month = scanner.nextInt();
        System.out.print("day: ");
        int day = scanner.nextInt();
        System.out.print("hour: ");
        int hour = scanner.nextInt();
        System.out.print("minute: ");
        int minute = scanner.nextInt();
        return LocalDateTime.of(time.getYear(), month, day, hour, minute);
    }

    public void deleteAnExercise() {
        System.out.print("please enter name of exercise: ");
        String name = scanner.next();
        loggedCourse.deleteAnExercise(name);
        displayAndPerformEnteredUserInClassAsPresenterOptions();
    }

    public void enterAnExerciseAsPresenter() {
        System.out.println("Enter the name of the exercise you want to enter.");
        String name = scanner.next();
        for (Exercise a : loggedCourse.getExercises()) {
            if (name.equals(a.getName())) {
                loggedExercise = loggedCourse.getAnExercise(a);
                System.out.println("you entered in " + name + " exercise.");
                displayAndPerformExercisePresenter();
                return;
            }
        }
        System.out.println("You do not have an exercise with this name in this class.");
        displayAndPerformEnteredUserInClassAsPresenterOptions();
    }

    public enum ExerciseAndPresenter {
        VIEW_SCOREBOARD, CREATE_AND_ADD_QUESTION, ADD_QUESTION_FROM_QUESTIONBANK,
        RECORD_CORRECT_ANSWERS, EDIT_EXERCISE, DELETE_QUESTION, GRADE_THE_ANSWERS, EXIT_EXERCISE
    }

    public void displayAndPerformExercisePresenter() {
        Menus.printPresenterExerciseMenu();
        int userInput = scanner.nextInt();
        if (userInput > 8 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
            displayAndPerformExercisePresenter();
        } else {
            ExerciseAndPresenter[] options = ExerciseAndPresenter.values();
            ExerciseAndPresenter option;
            option = options[userInput - 1];
            switch (option) {
                case VIEW_SCOREBOARD -> addStudentInThisClass();
                case CREATE_AND_ADD_QUESTION -> createAndAddQuestion();
                case ADD_QUESTION_FROM_QUESTIONBANK -> addQuestionFromQuestionBank();
                case RECORD_CORRECT_ANSWERS -> recordCorrectAnswers();
                case EDIT_EXERCISE -> editTheExercise();
                case DELETE_QUESTION -> deleteQuestion();
                case GRADE_THE_ANSWERS -> gradeTheAnswers();
                case EXIT_EXERCISE -> {
                    loggedExercise = null;
                    displayAndPerformEnteredUserInClassAsPresenterOptions();
                }
                default -> {
                }
            }
        }
    }

    public void editTheExercise() {

    }

    public void gradeTheAnswers() {
        System.out.println("Which student's answers do you want to grade?");
        for (User u : loggedCourse.getStudents()) {
            System.out.println(u.getUserName());
        }
        System.out.println("enter username of switched student.");
        String username = scanner.next();
        User switchedUser = null;
        for (User u : loggedCourse.getStudents()) {
            if (u.getUserName().equals(username)) {
                switchedUser = u;
                break;
            }
        }
        if (switchedUser == null) {
            System.out.println("The username you entered does not belong to the students in this class.");
            displayAndPerformExercisePresenter();
            return;
        }
        HashMap<Question, Answer> lastAnswerOfSwitchedStudent = loggedExercise.getLastAnswerOfAStudent(switchedUser);
        System.out.println("last answer of this student: ");
        ArrayList<Answer> answers = new ArrayList<>();
        for (Map.Entry<Question, Answer> entry : lastAnswerOfSwitchedStudent.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            answers.add(entry.getValue());
        }
        System.out.println("Enter the score of each question in order from top to bottom.");
        for (int i = 0; i < answers.size(); i++) {
            System.out.print((i + 1) + ": (from 0 to " + answers.get(i).getTheQuestion().getScore() + ") ");
            int score = scanner.nextInt();
            answers.get(i).setScore(score);
        }
        displayAndPerformExercisePresenter();
    }

    public void createAndAddQuestion() {
        boolean continues = true;
        do {
            Question question = createQuestion();
            if (question != null) {
                loggedExercise.addQuestion(question);
                System.out.println("also do you want to add this question to the question bank? (yes|no)");
                String confirmation = scanner.next();
                if (confirmation.equals("yes")) {
                    repository.addQuestionToQuestionBank(question);
                }
            }
            System.out.println("want to create another question? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                continues = false;
            }
        } while (continues);
        displayAndPerformExercisePresenter();
    }

    public Question createQuestion() {
        System.out.println("please enter the requested information.");
        System.out.print("name: ");
        String questionName = scanner.next();
        System.out.print("score: ");
        int score = scanner.nextInt();
        System.out.print("question text: ");
        scanner.nextLine();
        String questionText = scanner.nextLine();
        System.out.println("question level? (1.veryHard | 2.hard | 3.medium | 4.easy)");
        int questionLevel = scanner.nextInt();
        if (questionLevel > 4 || questionLevel < 0) {
            System.out.println("The number entered is incorrect");
            return null;
        }
        System.out.println("question type? (1.test | 2.essay | 3.empty | 4.shortAnswer)");
        int questionType = scanner.nextInt();
        if (questionType > 4 || questionType < 0) {
            System.out.println("The number entered is incorrect");
            return null;
        }
        Question.DifficultyLevel[] difficultyLevels = Question.DifficultyLevel.values();
        Question.DifficultyLevel difficultyLevel = difficultyLevels[questionLevel - 1];
        Question.QuestionType[] questionTypes = Question.QuestionType.values();
        Question.QuestionType questionType1 = questionTypes[questionType - 1];
        return new Question(questionName, score, questionText, difficultyLevel, questionType1);
    }

    public void addQuestionFromQuestionBank() {
        ArrayList<Question> questionBank = repository.getQuestionBank();
        boolean continues = true;
        int j = 0;
        int flag = 0;
        do {
            for (int i = j; i < j + 10; i++) {
                if (i == questionBank.size()) {
                    flag = 1;
                    break;
                }
                System.out.println((i + 1) + ":    " + questionBank.get(i));
            }
            chooseAQuestion();
            if (flag == 1) {
                System.out.println("We reached the end of the question bank questions.");
                break;
            }
            j += 10;
            System.out.println("Want to see the next 10 questions? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                continues = false;
            }
        } while (continues);
        displayAndPerformExercisePresenter();
    }

    public void chooseAQuestion() {
        do {
            System.out.println("Do you want to choose a question? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                break;
            }
            System.out.println("Enter the question number you want to select.");
            int questionNumber = scanner.nextInt();
            loggedExercise.addQuestion(repository.getQuestionBank().get(questionNumber - 1));
        } while (true);
    }

    public void recordCorrectAnswers() {
        if (loggedExercise.getQuestions().size() == 0) {
            System.out.println("there are no question for this exercise.");
            return;
        }
        for (int i = 0; i < loggedExercise.getQuestions().size(); i++) {
            System.out.println((i + 1) + ":  " + loggedExercise.getQuestions().get(i));
        }
        boolean continues = true;
        do {
            System.out.print("Which question do you want to record the answer to? ");
            int questionNumber = scanner.nextInt();
            if (questionNumber > loggedExercise.getQuestions().size() || questionNumber < 1) {
                System.out.println("The number entered is incorrect");
                continue;
            }
            recordCorrectAnswerForThisQuestion(loggedExercise.getQuestions().get(questionNumber - 1));
            System.out.println("want to record answer of another question? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                continues = false;
            }
        } while (continues);
        displayAndPerformExercisePresenter();
    }

    public void recordCorrectAnswerForThisQuestion(Question question) {
        System.out.println("enter correct answer text: ");
        scanner.nextLine();
        String answerText = scanner.nextLine();
        Answer answer = new Answer(loggedUser.getUserName(), question, loggedExercise.getDelayCoefficient(), answerText);
        question.setCorrectAnswer(answer);
        loggedExercise.setCorrectAnswerOfAQuestion(question, answer);
    }

    public void deleteQuestion() {
        for (int i = 0; i < loggedExercise.getQuestions().size(); i++) {
            System.out.println(i + ":  " + loggedExercise.getQuestions().get(i));
        }
        boolean continues = true;
        do {
            System.out.print("Which question do you want to delete it? ");
            int questionNumber = scanner.nextInt();
            if (questionNumber > loggedExercise.getQuestions().size() || questionNumber < 1) {
                System.out.println("The number entered is incorrect");
                continue;
            }
            loggedExercise.deleteAQuestion(loggedExercise.getQuestions().get(questionNumber - 1));
            System.out.println("want to delete another question? (yes|no)");
            String string = scanner.next();
            if (!string.equals("yes")) {
                continues = false;
            }
        } while (continues);
        displayAndPerformExercisePresenter();
    }

    public enum EnteredCourseStudentOptions {ENTER_EXERCISE, VIEW_STARTED_EXERCISES, EXIT_FROM_THIS_COURSE}

    public void displayAndPerformEnteredUserInClassAsStudentOptions() {
        Menus.printStudentCourseMenu();
        int userInput = scanner.nextInt();
        if (userInput > 3 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
            displayAndPerformEnteredUserInClassAsStudentOptions();
        } else {
            EnteredCourseStudentOptions[] options = EnteredCourseStudentOptions.values();
            EnteredCourseStudentOptions option;
            option = options[userInput - 1];
            switch (option) {
                case ENTER_EXERCISE -> enterAnExerciseAsStudent();
                case VIEW_STARTED_EXERCISES -> viewStartedExercises();
                case EXIT_FROM_THIS_COURSE -> {
                    loggedCourse = null;
                    displayAndPerformLoggedInUserOptions();
                }
                default -> {
                }
            }
        }
    }

    public void enterAnExerciseAsStudent() {
        System.out.println("Enter the name of the exercise you want to enter.");
        String name = scanner.next();
        for (Exercise a : loggedCourse.getExercises()) {
            if (name.equals(a.getName())) {
                loggedExercise = loggedCourse.getAnExercise(a);
                System.out.println("you entered in " + name + " exercise.");
                displayAndPerformExerciseStudent();
                return;
            }
        }
        System.out.println("You do not have an exercise with this name in this class.");
        displayAndPerformEnteredUserInClassAsStudentOptions();
    }

    public void viewStartedExercises() {
        LocalDateTime now = LocalDateTime.now();
        for (Exercise e : loggedCourse.getExercises()) {
            if (now.isAfter(e.getStartDate()) && now.isBefore(e.getEndDate())) {
                System.out.println(e);
            } else if (now.isAfter(e.getEndDate()) && now.isBefore(e.getDelayedSendingTime())) {
                System.out.println("((extra time))  " + e);
            }
        }
        displayAndPerformEnteredUserInClassAsStudentOptions();
    }

    public enum ExerciseAndStudent {VIEW_SCOREBOARD, VIEW_AND_ANSWER_QUESTIONS, VIEW_UPLOADED_ANSWERS, EXIT_EXERCISE}

    public void displayAndPerformExerciseStudent() {
        Menus.printStudentExerciseMenu();
        int userInput = scanner.nextInt();
        if (userInput > 4 || userInput < 1) {
            System.out.println("The number entered is incorrect.");
            displayAndPerformExerciseStudent();
        } else {
            ExerciseAndStudent[] options = ExerciseAndStudent.values();
            ExerciseAndStudent option;
            option = options[userInput - 1];
            switch (option) {
                //case VIEW_SCOREBOARD -> viewScoreboard();
                case VIEW_AND_ANSWER_QUESTIONS -> viewAndAnswerQuestions();
                case VIEW_UPLOADED_ANSWERS -> viewUploadedAnswers();
                case EXIT_EXERCISE -> {
                    loggedExercise = null;
                    displayAndPerformEnteredUserInClassAsStudentOptions();
                }
                default -> {
                }
            }
        }
    }

    public void viewAndAnswerQuestions() {
        if (loggedExercise.getQuestions().size() == 0) {
            System.out.println("No questions have been added to this exercise yet.");
            return;
        }
        for (int i = 0; i < loggedExercise.getQuestions().size(); i++) {
            System.out.println((i + 1) + ":  " + loggedExercise.getQuestions().get(i));
        }
        HashMap<Question, Answer> answerMap = new HashMap<>();
        System.out.println("Please answer the questions in order.\n " +
                "If you do not know the answer to the question, write in the answer section, (i do not know).");
        scanner.nextLine();
        for (int i = 0; i < loggedExercise.getQuestions().size(); i++) {
            System.out.print((i + 1) + ": ");
            String answerText = scanner.nextLine();
            Answer answer = new Answer(loggedUser.getUserName(),
                    new Question(loggedExercise.getQuestions().get(i)), loggedExercise.getDelayCoefficient(), answerText);
            answerMap.put(new Question(loggedExercise.getQuestions().get(i)), answer);
        }
        loggedExercise.answerToTheExercise(answerMap, loggedUser);
        displayAndPerformExerciseStudent();
    }

    public void viewUploadedAnswers() {
        System.out.println("answers are sorted by submission date.");
        HashMap<LocalDateTime, HashMap<Question, Answer>> answersList = loggedExercise.getAnswerListOfAStudent(new User(loggedUser));
        ArrayList<LocalDateTime> times = new ArrayList<>();
        for (Map.Entry<LocalDateTime, HashMap<Question, Answer>> entry : answersList.entrySet()) {
            times.add(entry.getKey());
        }
        Collections.sort(times);
        ArrayList<HashMap<Question, Answer>> sortedAnswers = new ArrayList<>();
        for (LocalDateTime l : times) {
            sortedAnswers.add(answersList.get(l));
        }
        for (int i = 0; i < sortedAnswers.size(); i++) {
            System.out.println("\nanswer sent " + (i + 1) + ": ");
            for (Map.Entry<Question, Answer> entry : sortedAnswers.get(i).entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
        }
        displayAndPerformExerciseStudent();
    }
}