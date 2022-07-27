package ir.ac.kntu;

public class PrivateCourse extends Course{
    private String password;

    public PrivateCourse(String name, String instituteName,
                         String academicYear, boolean open, String courseDescription, User presenter, String password) {
        super(name, instituteName, academicYear, open, courseDescription, presenter);
        this.password = password;
    }

    public PrivateCourse(PrivateCourse a) {
        super(a.getName(), a.getInstituteName(), a.getAcademicYear(), a.isOpen(), a.getCourseDescription(), a.getPresenter());
        this.password = a.password;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public boolean enteredPasswordIsCorrect(String password){
        boolean isCorrect = password.equals(this.password);
        return isCorrect;
    }

    @Override
    public String toString() {
        return "Course{" +
                "\n    name='" + super.getName() + '\'' +
                "\n    nameOfEducationalInstitution='" + super.getInstituteName() + '\'' +
                "\n    presenterName='" + super.getPresenterName() + '\'' +
                "\n    academicYear='" + super.getAcademicYear() + '\'' +
                "\n    isOpen=" + super.isOpen() +
                "\n    courseDescription='" + super.getCourseDescription() + '\'' +
                "\n    this class is private" + "\n" +
                '}';
    }
}
