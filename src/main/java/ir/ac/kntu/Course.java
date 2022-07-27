package ir.ac.kntu;

import java.util.*;

public class Course {

    private String name;

    private String instituteName;

    private final String presenterName;

    private String academicYear;

    private boolean isOpen;

    private String courseDescription;

    private final User presenter;

    private ArrayList<User> students;

    private ArrayList<Exercise> exercises;

    private Map<User, HashMap<Exercise, HashMap<Question, Double>>> studentScores;

    public Course(String name, String instituteName,
                  String academicYear, boolean isOpen, String courseDescription, User presenter) {
        this.name = name;
        this.instituteName = instituteName;
        this.presenterName = presenter.getName();
        this.academicYear = academicYear;
        this.isOpen = isOpen;
        this.courseDescription = courseDescription;
        this.presenter = presenter;
        students = new ArrayList<>();
        exercises = new ArrayList<>();
    }

    public Course(Course a) {
        this.name = a.name;
        this.instituteName = a.instituteName;
        this.presenterName = a.presenter.getName();
        this.academicYear = a.academicYear;
        this.isOpen = a.isOpen;
        this.courseDescription = a.courseDescription;
        this.presenter = a.presenter;
        this.students = a.students;
        this.exercises = a.exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public User getPresenter() {
        return new User(presenter);
    }

    public ArrayList<User> getStudents() {
        ArrayList<User> copyOfStudents = new ArrayList<>();
        for (User a : students) {
            copyOfStudents.add(new User(a));
        }
        return copyOfStudents;
    }

    public ArrayList<Exercise> getExercises() {
        ArrayList<Exercise> copyOfExercises = new ArrayList<>();
        for (Exercise a : exercises) {
            copyOfExercises.add(new Exercise(a));
        }
        return copyOfExercises;
    }

    public Exercise getAnExercise(Exercise exercise) {
        for (Exercise e : exercises) {
            if (e.equals(exercise)) {
                return e;
            }
        }
        return null;
    }

    public void addAnStudent(User student) {
        if (student.equals(presenter)) {
            System.out.println("You have created this class yourself. You can not enroll as a student in the class.");
            return;
        }
        for (User a : students) {
            if (student.equals(a)) {
                System.out.println("you enrolled in this class before");
                return;
            }
        }
        students.add(student);
    }

    public void addAnExercise(Exercise exercise) {
        if (exercise == null) {
            return;
        }
        for (Exercise e : exercises) {
            if (e.equals(exercise)) {
                System.out.println("this exercise add to the class before.");
                return;
            } else if (e.getName().equals(exercise.getName())) {
                System.out.println("The name entered has already been registered for another exercise.\n" +
                        "Please choose another name and recreate the exercise.");
                return;
            }
        }
        exercises.add(exercise);
    }

    public void deleteAnExercise(String name) {
        for (Exercise e : exercises) {
            if (name.equals(e.getName())) {
                exercises.remove(e);
                return;
            }
        }
        System.out.println("There is no exercise with this name.");
    }

    @Override
    public String toString() {
        return "Course{" +
                "\n    name='" + name + '\'' +
                "\n    nameOfEducationalInstitution='" + instituteName + '\'' +
                "\n    presenterName='" + presenterName + '\'' +
                "\n    academicYear='" + academicYear + '\'' +
                "\n    isOpen=" + isOpen +
                "\n    courseDescription='" + courseDescription + '\'' + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(instituteName, course.instituteName) && Objects.equals(presenterName, course.presenterName) && presenter.equals(course.presenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instituteName, presenterName, presenter);
    }
}