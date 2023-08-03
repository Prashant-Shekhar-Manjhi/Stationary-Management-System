package com.targetindia.stationarymanagementsystem.web.controllers;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.model.StudentLoginResponse;
import com.targetindia.stationarymanagementsystem.services.StudentService;
import com.targetindia.stationarymanagementsystem.web.validators.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/inventory/v1/student")
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private StudentValidator validator;

    //login...
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity handleStudentLogin(@RequestBody StudentLoginDTO loginDTO){
        if(!validator.isStudentCredentialValid(loginDTO)){
            return ResponseEntity.status(400).body(new Message("Invalid Inputs"));
        }
        try{
            Student student = service.studentLogin(loginDTO);
            return ResponseEntity.ok(new StudentLoginResponse("Login Successful", true, student));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StudentLoginResponse(e.getMessage(), false));
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity handleRegistration(@RequestBody StudentDTO studentDTO){
        if(!validator.isStudentValid(studentDTO)){
            return ResponseEntity.status(400).body(new Message("Invalid Inputs."));
        }
        try{
            service.studentRegistration(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Registration Successful"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Registration Failed"));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity handleGetAllStudents(){
        try{
            List<Student> studentList = service.getAllStudent();
            return ResponseEntity.ok(studentList);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Students Not Found"));
        }
    }

}
