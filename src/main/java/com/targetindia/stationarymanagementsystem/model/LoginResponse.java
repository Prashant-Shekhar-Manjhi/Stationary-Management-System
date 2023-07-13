package com.targetindia.stationarymanagementsystem.model;

import com.targetindia.stationarymanagementsystem.dto.AdminResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String message;
    private Boolean status;
    private Date date = new Date();
    private AdminResponseDto admin;

    public LoginResponse(String message, Boolean status, AdminResponseDto admin) {
        this.message = message;
        this.status = status;
        this.admin = admin;
    }

    public LoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
