import java.util.ArrayList;
import java.util.HashSet;


public class EnrollmentSystem {
    private final ArrayList<Student> students;
    private final ArrayList<Course> courses;
    private Integer studentIdTracker = 0;

    public EnrollmentSystem(){
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void addStudent(Student s){
        this.students.add(s);
        if(s.getId() == -1){
            s.setId(studentIdTracker);
            studentIdTracker++;
        }
    }

    public void addCourse(Course c){
        this.courses.add(c);
    }

    public void addStudents(ArrayList<Student> students){
        students.forEach(this::addStudent);
    }

    public void addCourses(ArrayList<Course> courses){
        this.courses.addAll(courses);
    }

    public boolean enrollStudentToCourse(Student student, Course course){
        boolean success = false;
        System.out.println("Enrolling student " + student.getId() + " to course " + course.getModuleCode());

        if (!isStudentEligible(student, course)){
            return success;
        }

        synchronized (course){
            if (course.isThereAvailableSlots()){
                course.decrementStudentSlots();
                student.addCourse(course);
                success = true;
            }
        }

        return success;
    }

    public boolean isStudentEligible(Student student, Course course){
        boolean eligibility = false;
        ArrayList<String> requiredModuleCodes = course.getPrerequisiteModuleCodes();
        ArrayList<String> studentCompletedCodes = new ArrayList<> (student.getCompletedModules());

        studentCompletedCodes.retainAll(requiredModuleCodes);

        if( studentCompletedCodes.size() == requiredModuleCodes.size()){
            eligibility = true;
        }

        return eligibility;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

}
