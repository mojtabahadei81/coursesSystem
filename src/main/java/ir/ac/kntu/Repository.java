package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;

public class Repository {

    private static Repository singleInstance = null;

    private ArrayList<User> users;

    private ArrayList<Course> openCourses;

    private ArrayList<Course> courses;

    private ArrayList<PrivateCourse> privateCourses;

    private ArrayList<PrivateCourse> openPrivateCourses;

    private ArrayList<Course> allCourses;

    private ArrayList<Question> questionBank;

    private HashMap<String, User> usernameToUser;

    private Repository() {
        users = new ArrayList<>();
        openCourses = new ArrayList<>();
        courses = new ArrayList<>();
        privateCourses = new ArrayList<>();
        openPrivateCourses = new ArrayList<>();
        allCourses = new ArrayList<>();
        usernameToUser = new HashMap<>();
        questionBank = new ArrayList<>();
    }

    public void addUser(User a) {
        int flag = 0;
        if (a == null){
            return;
        }
        for (User b : users){
            if (b.equals(a)){
                System.out.println("this User created before");
                flag = 1;
            }
            if(b.getUserName().equals(a.getUserName())) {
                System.out.println("This username has already been used");
                flag = 1;
            }
            if(b.getEmail().equals(a.getEmail())) {
                System.out.println("This email has already been used");
                flag = 1;
            }
            if(b.getNationalId().equals(a.getNationalId())){
                System.out.println("This national id has already been used");
                flag = 1;
            }
            if(b.getMobileNumber().equals(a.getMobileNumber())){
                System.out.println("This mobile number has already been used");
                flag = 1;
            }
            if (flag == 1){
                return;
            }
        }
        users.add(a);
        usernameToUser.put(a.getUserName(), a);
    }

    public void addCourse(Course a) {
        for (Course b : allCourses){
            if (a.equals(b)){
                System.out.println("this course created before");
            }
        }
        if (a.isOpen()){
            openCourses.add(a);
        }
        courses.add(a);
        allCourses.add(a);
    }

    public void addPrivateCourse(PrivateCourse a){
        for (Course b : allCourses){
            if (b.equals(a)){
                System.out.println("this course created before");
                return;
            }
        }
        if (a.isOpen()){
            openPrivateCourses.add(a);
        }
        privateCourses.add(a);
        allCourses.add(a);
    }

    public void deleteAClass(Course classToBeDeleted) {
        privateCourses.remove(classToBeDeleted);
        openPrivateCourses.remove(classToBeDeleted);
        courses.remove(classToBeDeleted);
        openCourses.remove(classToBeDeleted);

    }

    public ArrayList<User> getUsers() {
        ArrayList<User> copyOfUsers = new ArrayList<>();
        for (User a : users){
            copyOfUsers.add(new User(a));
        }
        return copyOfUsers;
    }

    public ArrayList<Course> getOpenCourses() {
        ArrayList<Course> copyOfOpenCourses = new ArrayList<>();
        for (Course a : openCourses){
            copyOfOpenCourses.add(new Course(a));
        }
        return copyOfOpenCourses;
    }

    public ArrayList<PrivateCourse> getOpenPrivateCourses() {
        ArrayList<PrivateCourse> copyOfPrivateCourses = new ArrayList<>();
        for (PrivateCourse a : openPrivateCourses){
            copyOfPrivateCourses.add(new PrivateCourse(a));
        }
        return copyOfPrivateCourses;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> copyOfCourses = new ArrayList<>();
        for (Course a : allCourses){
            copyOfCourses.add(new Course(a));
        }
        return copyOfCourses;
    }

    public User getUser(User user) {
        return usernameToUser.get(user.getUserName());
    }

    public PrivateCourse getPrivateCourse(PrivateCourse privateCourse) {
        for (PrivateCourse a : openPrivateCourses) {
            if (a.equals(privateCourse)) {
                return a;
            }
        }
        return null;
    }

    public Course getPublicCourse(Course course) {
        for (Course a : openCourses) {
            if (a.equals(course)) {
                return a;
            }
        }
        return null;
    }

    public Course getCourse(Course course) {
        for (Course a : allCourses) {
            if (a.equals(course)) {
                return a;
            }
        }
        return null;
    }

    public void addQuestionToQuestionBank(Question question) {
        for (Question q : questionBank) {
            if(q.equals(question)) {
                System.out.println("this question added to questionBank before.");
                return;
            }
        }
        questionBank.add(question);
    }

    public ArrayList<Question> getQuestionBank() {
        ArrayList<Question> copyOfQuestionBank = new ArrayList<>();
        for (Question a : questionBank){
            copyOfQuestionBank.add(new Question(a));
        }
        return copyOfQuestionBank;
    }

    public static Repository getInstance() {
        if (singleInstance == null) {
            singleInstance = new Repository();
        }
        return singleInstance;
    }
}
