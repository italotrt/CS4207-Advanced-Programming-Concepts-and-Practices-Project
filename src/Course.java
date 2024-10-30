import java.util.*;

public class Course {
    private final String moduleCode;
    private ArrayList<String> prerequisiteModuleCodes = new ArrayList<>();
    private int studentSlots;
    private int studentSlotsRemaining;


    public Course(String moduleCode, int studentSlots){
        this.moduleCode = moduleCode;
        this.prerequisiteModuleCodes = new ArrayList<>();
        this.studentSlots = studentSlots;
        this.studentSlotsRemaining = studentSlots;
    }

    public Course(String moduleCode, int studentSlots, String[] prerequisiteModuleCodes){
        this.moduleCode = moduleCode;
        this.prerequisiteModuleCodes = new ArrayList<>(List.of(prerequisiteModuleCodes));
        this.studentSlots = studentSlots;
        this.studentSlotsRemaining = studentSlots;
    }

    public Course(String moduleCode, int studentSlots, ArrayList<String > prerequisiteModuleCodes){
        this.moduleCode = moduleCode;
        this.prerequisiteModuleCodes = prerequisiteModuleCodes;
        this.studentSlots = studentSlots;
        this.studentSlotsRemaining = studentSlots;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public ArrayList<String> getPrerequisiteModuleCodes() {
        return prerequisiteModuleCodes;
    }

    public synchronized void decrementStudentSlots(){
        this.studentSlotsRemaining--;
    }

    public synchronized boolean isThereAvailableSlots(){
        return this.studentSlotsRemaining > 0;
    }

    public int getStudentSlots(){
        return studentSlots;
    }
}
