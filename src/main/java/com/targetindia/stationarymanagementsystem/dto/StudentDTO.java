package com.targetindia.stationarymanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class StudentDTO {
    private Integer studentId;
    private String studentName;
    private String studentEmail;
    private Date dateOfBirth;
    private String studentPassword;


    public StudentDTO(String studentName,String studentEmail,String studentPassword){
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
    }
}
