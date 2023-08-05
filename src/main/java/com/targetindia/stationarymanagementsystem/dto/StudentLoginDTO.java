package com.targetindia.stationarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginDTO {
    private String studentEmail;
    private String studentPassword;
}
