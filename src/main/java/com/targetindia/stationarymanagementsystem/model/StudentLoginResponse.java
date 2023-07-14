package com.targetindia.stationarymanagementsystem.model;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginResponse {
    private String message;
    private Boolean status;
    private Date date = new Date();

    private StudentDTO student;

    public StudentLoginResponse(String message, Boolean status, StudentDTO student) {
        this.message = message;
        this.status = status;
        this.student = student;
    }

    public StudentLoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
