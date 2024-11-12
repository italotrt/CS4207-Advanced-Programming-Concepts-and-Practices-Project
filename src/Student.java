import java.util.ArrayList;

public class Student {
    private int id;
    private ArrayList<String> completedModuleCodes = new ArrayList<>();

    public Student(int id){
        this.id = id;
    }

    public void addCourse(Course course){
        this.completedModuleCodes.add(course.getModuleCode());
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

}
