import java.util.ArrayList;
import java.util.List;

public class Course {
    private String moduleCode;
    private ArrayList<String> prerequisiteModuleCodes = new ArrayList<>();
    private int studentSlotsRemaining = 30;

    public Course(){

    }

    public Course(String moduleCode){
        this.moduleCode = moduleCode;
        this.prerequisiteModuleCodes = new ArrayList<>();
    }

    public Course(String moduleCode, String[] prerequisiteModuleCodes){
        this.moduleCode = moduleCode;
        this.prerequisiteModuleCodes = new ArrayList<>(List.of(prerequisiteModuleCodes));
    }

    public Course(String moduleCode, ArrayList<String > prerequisiteModuleCodes){
        this.moduleCode = moduleCode;
        this.prerequisiteModuleCodes = prerequisiteModuleCodes;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public ArrayList<String> getPrerequisiteModuleCodes() {
        return prerequisiteModuleCodes;
    }

    public void setPrerequisiteModuleCodes(ArrayList<String> prerequisiteModuleCodes) {
        this.prerequisiteModuleCodes = prerequisiteModuleCodes;
    }

    public synchronized void decrementStudentSlots(){
        this.studentSlotsRemaining--;
    }

    public synchronized boolean isThereAvailableSlots(){
        return this.studentSlotsRemaining > 0;
    }

    public int getStudentSlotsRemaining() {
        return studentSlotsRemaining;
    }

}
