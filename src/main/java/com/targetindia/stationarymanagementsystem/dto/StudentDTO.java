package com.targetindia.stationarymanagementsystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO {
    private Integer studentId;
    private String studentName;
    private String studentEmail;
    private Date dateOfBirth;
    private String studentPassword;
}
