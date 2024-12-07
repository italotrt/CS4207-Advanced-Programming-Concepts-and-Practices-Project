// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract Chat {
    // Data structure for student
    struct Student {
        string studentId;
        string name;
    }

    // Data structure for message
    struct Message {
        string message;
        string programmingLanguage;
        Student student;
    }

    // Mappers
    mapping(string => Student) public students;
    mapping(string => bool) public existingStudents;

    // Arrays
    string[] public studentIds;
    Message[] public messages;

    // Event emitters for frontend
    event StudentAdded(string studentId);
    event MessageAdded(string message, string programmingLanguage, string studentId);


    function addStudent(string memory name, string memory studentId) public payable {
        // Validate the student exists within our blockchain
        require(!existingStudents[studentId], "Student ID already exists on the blockchain");

        // Add student struct to the list
        students[studentId] = Student(studentId, name);
        existingStudents[studentId] = true; // Hashmap to check if student exists
        studentIds.push(studentId); // append

        emit StudentAdded(studentId); // Emit the event
    }

    function sendMessage(string memory message, string memory programmingLanguage, string memory studentId) public payable {
        // Validate student exists within our blockchain
        require(existingStudents[studentId], "Student does not exist!");

        Student storage student = students[studentId]; // Get the student
        messages.push(Message(message, programmingLanguage, student)); // Append the message
        emit MessageAdded(message, programmingLanguage, studentId); // Emit the event
    }
}
