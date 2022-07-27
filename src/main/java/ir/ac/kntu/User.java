package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String name;

    private String userName;

    private String email;

    private String password;

    private String nationalId;

    private String mobileNumber;

    private ArrayList<Course> ownerClasses;

    private ArrayList<PrivateCourse> ownerPrivateClasses;

    private ArrayList<Course> allOwnerClasses;

    private ArrayList<Course> enrolledClasses;

    public User(String name, String userName, String email, String password, String nationalId, String mobileNumber) {
        if(dataIsCorrect(email, nationalId, mobileNumber)){
            this.name = name;
            this.userName = userName;
            this.email = email;
            this.password = password;
            this.nationalId = nationalId;
            this.mobileNumber = mobileNumber;
            ownerClasses = new ArrayList<>();
            ownerPrivateClasses = new ArrayList<>();
            enrolledClasses = new ArrayList<>();
            allOwnerClasses = new ArrayList<>();
        }
    }

    public User(User a){
        this.name = a.name;
        this.userName = a.userName;
        this.email = a.email;
        this.password = a.password;
        this.nationalId = a.nationalId;
        this.mobileNumber = a.mobileNumber;
        this.ownerClasses = a.ownerClasses;
        this.enrolledClasses = a.enrolledClasses;
        this.allOwnerClasses = a.allOwnerClasses;
        this.ownerPrivateClasses = a.ownerPrivateClasses;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        if (isCorrectEmail(email)){
            this.email = email;
        } else {
            System.out.println("email address is not correct.");
        }
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void setNationalId(String nationalId) {
        if (isCorrectNationalId(nationalId)){
            this.nationalId = nationalId;
        } else {
            System.out.println("(national Id) is not correct.");
        }
    }

    public void setMobileNumber(String mobileNumber) {
        if (isCorrectMobileNumber(mobileNumber)){
            this.mobileNumber = mobileNumber;
        } else {
            System.out.println("mobile number is not correct.");
        }
    }

    public boolean dataIsCorrect(String email, String nationalId, String mobileNumber) {
        int flag = 0;
        if(!isCorrectEmail(email)) {
            System.out.println("email address is not correct.");
            flag = 1;
        }
        if(!isCorrectNationalId(nationalId)){
            System.out.println("(national Id) is not correct.");
            flag = 1;
        }
        if(!isCorrectMobileNumber(mobileNumber)){
            System.out.println("mobile number is not correct.");
            flag = 1;
        }
        if (flag == 0){
            return true;
        } else {
            System.out.println("please instantiate again");
            return false;
        }
    }

    public static boolean isCorrectEmail(String email){
        Pattern correctEmailPattern = Pattern.compile("^[^ ]+@[^ ]+\\.(com|ir)$");
        Matcher matcher = correctEmailPattern.matcher(email);
        return matcher.find();
    }

    public static boolean isCorrectNationalId(String nationalId){
        Pattern correctEmailPattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = correctEmailPattern.matcher(nationalId);
        return matcher.find();
    }

    public static boolean isCorrectMobileNumber(String mobileNumber){
        Pattern correctEmailPattern = Pattern.compile("^(0|\\+98)9[0-9]{9}$");
        Matcher matcher = correctEmailPattern.matcher(mobileNumber);
        return matcher.find();
    }

    public void createCourse(Course a) {
        for (Course b : allOwnerClasses){
            if (a.equals(b)){
                System.out.println("this course created before");
                return;
            }
        }
        ownerClasses.add(a);
        allOwnerClasses.add(a);
    }

    public void createPrivateCourse(PrivateCourse a) {
        for (Course b : allOwnerClasses){
            if (a.equals(b)){
                System.out.println("this course created before");
                return;
            }
        }
        ownerPrivateClasses.add(a);
        allOwnerClasses.add(a);
    }

    public ArrayList<Course> getAllOwnerClasses() {
        ArrayList<Course> copyOfCourses = new ArrayList<>();
        for (Course a : allOwnerClasses){
            copyOfCourses.add(new Course(a));
        }
        return copyOfCourses;
    }

    public void deleteAClass(Course classToBeDeleted) {
        ownerClasses.remove(classToBeDeleted);
        ownerPrivateClasses.remove(classToBeDeleted);
        allOwnerClasses.remove(classToBeDeleted);
    }

    public void enrollInAClass(Course course) {
        for (Course a : enrolledClasses){
            if (a.equals(course)) {
                return;
            }
        }
        enrolledClasses.add(course);
    }

    @Override
    public String toString() {
        return "User:{\n" +
                "    name = '" + name + '\'' +
                "\n    userName = '" + userName + '\'' +
                "\n    email = '" + email + '\'' +
                "\n    nationalId = '" + nationalId + '\'' +
                "\n    mobileNumber = '" + mobileNumber + '\''
                +"\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(userName, user.userName) && Objects.equals(nationalId, user.nationalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userName, nationalId);
    }
}
