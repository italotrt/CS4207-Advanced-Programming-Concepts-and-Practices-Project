// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract Chat {
    string public message;

    constructor(string memory initialMessage) payable {
        message = initialMessage;
    }

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

    mapping(string => Student) public students;
    mapping(string => Mentor) public mentors;
    mapping(string => Message) public messages;

    event StudentAdded(string studentId);

    function addStudent(string memory name, string memory studentId) public payable {
        students[studentId] = Student(studentId = studentId, name = name);
    }

    function addMentor(string memory name, string memory programmingLanguage) public payable {
        mentors[name] = Mentor(name, programmingLanguage);
    }

}
