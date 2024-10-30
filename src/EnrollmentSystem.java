import java.util.*;


public class EnrollmentSystem {
    private static final double OVERBOOK_LIMIT = 0.2;
    private final ArrayList<Student> students;
    private final ArrayList<Course> courses;
    private final HashMap<Course, ArrayDeque<Integer>> overbookedStudents = new HashMap<>();
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

        if (success){
            return success;
        }

        synchronized (course){
            if(!isOverbookedFull(course)){
                addStudentToOverbookedQue(student.getId(), course);
//                student.addCourse(course);
                success = true;
            } else {
                System.out.printf("Course %s is full %n", course.getModuleCode());
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

    public Queue<Integer> getOverbookedStudents(Course course){
        return this.overbookedStudents.get(course);
    }


    public synchronized boolean isOverbookedFull(Course course){
        ArrayDeque<Integer> studentOverbookedQueue = this.overbookedStudents.computeIfAbsent(course, k -> new ArrayDeque<>());

        return studentOverbookedQueue.size() > (int) (course.getStudentSlots() * OVERBOOK_LIMIT);
    }

    public void addStudentToOverbookedQue(Integer studentId, Course course){
        ArrayDeque<Integer> studentOverbookedQueue = this.overbookedStudents.computeIfAbsent(course, k -> new ArrayDeque<>());

        studentOverbookedQueue.add(studentId);
    }

    public void enrollFirstStudentInQueue(Course course){
        ArrayDeque<Integer> studentOverbookedQueue = this.overbookedStudents.get(course);
        Integer studentId = studentOverbookedQueue.poll();
        this.students.stream()
                .filter(s -> {
                    assert studentId != null;
                    return s.getId() == studentId;
                })
                .findFirst()
                .ifPresent(student -> student.addCourse(course));
    }

}
