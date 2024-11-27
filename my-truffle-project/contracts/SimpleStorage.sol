// SPDX-License-Identifier: MIT
pragma solidity 0.8.0;


contract EnrollmentContract{
    uint constant private OVERBOOK_LIMIT = 20;
    string public message;

    constructor(string memory initialMessage) {
        message = initialMessage;
    }

    struct Student{
        string id;
        string name;
        string[] completedModules;
        string[] enrolledModules;
    }

    struct Course {
        string moduleCode;
        uint studentSlots;
        uint slotsRemaining;
        string[] prerequisiteModules;
    }

    mapping(string => Student) public students; // Hashmap of String => Student
    mapping(string => Course) public courses;
    mapping(string => string[]) public overbookedStudents; // Course code => StudentId[]

    event Enrolled(string studentId, string courseCode);
    event Overbooked(string courseCode, string studentId);
    event EnrollmentFailed(string studentId, string courseCode);


    function addStudent(string memory name, string memory studentId) public {
        students[studentId] = Student(studentId, name, new string[](0), new string[](0));
    }

    function addCourse(string memory moduleCode, uint slots, string[] memory prerequisites) public {
//        require(courses[moduleCode].studentSlots == 0, "Course already exists");
        courses[moduleCode] = Course(moduleCode, slots, slots, prerequisites);
    }

    function enrollStudent(string memory studentId, string memory courseCode) public  {
        require(bytes(students[studentId].id).length > 0, "Student does not exist");
        require(courses[courseCode].studentSlots > 0, "Course does not exist");

        // Looks for the student in the blockchain
        Student storage student = students[studentId];
        Course storage course = courses[courseCode];

        if(!isStudentEligible(studentId, courseCode)){
            emit EnrollmentFailed(studentId, courseCode);
            return;
        }

        if (course.slotsRemaining > 0){
            student.enrolledModules.push(courseCode);
            course.slotsRemaining--;
            emit Enrolled(studentId, courseCode);
        } else{
            // Overbooked
            uint overbookedLimit = (course.studentSlots * OVERBOOK_LIMIT) / 100;
            if(overbookedStudents[courseCode].length < overbookedLimit){
                overbookedStudents[courseCode].push(studentId);
                emit Overbooked(courseCode, studentId);
            } else {
                emit EnrollmentFailed(studentId, courseCode);
            }
        }
    }

    function isStudentEligible(string memory studentId, string memory courseCode) private view returns (bool) {
        Course storage course = courses[courseCode];
        Student storage student = students[studentId];

        for(uint i = 0; i < course.prerequisiteModules.length; i++){
            bool hasCompletedModule = false;
            for (uint j = 0; j < student.completedModules.length;  j++){
                if(keccak256(bytes(student.completedModules[j])) == keccak256(bytes(course.prerequisiteModules[i]))){
                    hasCompletedModule = true;
                    break;
                }
            }

            if(!hasCompletedModule){
                return false;
            }
        }

        return true;
    }

    function enrollFirstStudentInQueue(string memory courseCode) public {
        require(overbookedStudents[courseCode].length > 0, "No students in queue");

        string memory studentId = overbookedStudents[courseCode][0];
        Student storage student = students[studentId];
        Course storage course = courses[courseCode];

        // Enroll student and remove from queue
        student.enrolledModules.push(courseCode);
        course.slotsRemaining--;
        overbookedStudents[courseCode].pop();
        emit Enrolled(studentId, courseCode);
    }
}
