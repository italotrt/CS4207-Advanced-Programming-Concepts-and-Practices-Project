import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public static void main(String[] args) {
    // Init modules
    Course cs101 = new Course("CS101", 50);
    Course cs201 = new Course("CS201", 50, new String[]{"CS101"});
    Course cs301 = new Course("CS301", 50, new String[]{"CS101", "CS201"});
    Course cs202 = new Course("CS202", 50, new String[]{"CS101"});
    ArrayList<Course> courses = new ArrayList<>(Arrays.asList(cs101, cs201, cs202, cs301));

    // Init Students
    ArrayList<Student> students = new ArrayList<>();
    for (int i = 0; i < 65; i++) {
        students.add(new Student(i));
    }

    // Init System
    EnrollmentSystem enrollmentSystem = new EnrollmentSystem();
//    enrollmentSystem.addStudents(students);
//    enrollmentSystem.addCourses(courses);

    // Parallelism
    List<CompletableFuture<Boolean>> futures = enrollmentSystem.getStudents().stream()
            .map(student -> CompletableFuture.supplyAsync(() -> enrollmentSystem.enrollStudentToCourse(student, cs101)))
            .toList();

    CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    allFutures.join();



    // Evaluate
    for (Student s : enrollmentSystem.getStudents()) {
        System.out.printf("Student: %2d - %s %n", s.getId(), s.getCompletedModules().toString());
    }

    for (Integer i : enrollmentSystem.getOverbookedStudents(cs101)) {
        System.out.printf("Overbooked Student: %2d %n", i);
    }

    for (int i = 0; i < 5; i++ ){
        enrollmentSystem.enrollFirstStudentInQueue(cs101);
    }

    for (Student s : enrollmentSystem.getStudents()) {
        System.out.printf("Student: %2d - %s %n", s.getId(), s.getCompletedModules().toString());
    }

    for (Integer i : enrollmentSystem.getOverbookedStudents(cs101)) {
        System.out.printf("Overbooked Student: %2d %n", i);
    }
}