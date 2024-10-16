import java.util.ArrayList;

public class Student {
    private int id = -1;
    private ArrayList<String> completedModuleCodes = new ArrayList<>();

    public Student(){

    }

    public Student(int id){
        this.id = id;
    }

    public Student(int id, ArrayList<String> completedModuleCodes) {
        this.id = id;
        this.completedModuleCodes = completedModuleCodes;
    }

    public void addCourse(Course course){
        this.completedModuleCodes.add(course.getModuleCode());
    }

    public void addCourseCode(String courseCode){
        this.completedModuleCodes.add(courseCode);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getCompletedModules() {
        return completedModuleCodes;
    }

    public void setCompletedModules(ArrayList<String> completedCourseCodes) {
        this.completedModuleCodes = completedCourseCodes;
    }
}
