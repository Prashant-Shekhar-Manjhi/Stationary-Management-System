package com.targetindia.stationarymanagementsystem.model;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class StudentLoginResponse {
    private String message;
    private Boolean status;
    private Date date = new Date();

    private Student student;

    public StudentLoginResponse(String message, Boolean status, Student student) {
        this.message = message;
        this.status = status;
        this.student = student;
    }

    public StudentLoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
