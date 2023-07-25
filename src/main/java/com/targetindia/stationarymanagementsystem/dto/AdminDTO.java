package com.targetindia.stationarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private String adminName;
    private String adminEmail;
    private String adminPassword;
    private Date dateOfBirth;
}
