// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract Chat {
    struct Student {
        string studentId;
        string name;
    }

    struct Mentor {
        string mentorName;
        string programmingLanguage;
    }

    struct Message {
        string message;
        string programmingLanguage;
        Student student;
    }

    string[] public studentIds;

    mapping(string => Student) public students;
    mapping(string => Mentor) public mentors;
    mapping(string => bool) public existingStudents;

    Message[] public messages;
    
    event StudentAdded(string studentId);
    event MessageAdded(string message, string programmingLanguage, string studentId);

    function addStudent(string memory name, string memory studentId) public payable {
        require(!existingStudents[studentId], "Student ID already exists on the blockchain");

        students[studentId] = Student(studentId, name);
        existingStudents[studentId] = true;
        studentIds.push(studentId);

        emit StudentAdded(studentId);
    }

    function addMentor(string memory name, string memory programmingLanguage) public payable {
        mentors[name] = Mentor(name, programmingLanguage);
    }

    function sendMessage(string memory message, string memory programmingLanguage, string memory studentId) public payable {
        require(existingStudents[studentId], "Student does not exist!");
        // require(students[studentId], "Student doesn't exists on the blockchain");
        Student storage student = students[studentId];
        messages.push(Message(message, programmingLanguage, student));
        emit MessageAdded(message, programmingLanguage, studentId);
    }

    function getStudentIds() public view returns (Student[] memory) {
        Student[] memory studentGet = new Student[](studentIds.length);
        for (uint i = 0; i < studentIds.length; i++) {
            studentGet[i] = students[studentIds[i]];
        }
        return studentGet;
    }

}
