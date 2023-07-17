package com.targetindia.stationarymanagementsystem.model;

import com.targetindia.stationarymanagementsystem.dto.AdminDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {
    private String message;
    private Boolean status;
    private Date date = new Date();
    private AdminDTO admin;

    public AdminLoginResponse(String message, Boolean status, AdminDTO admin) {
        this.message = message;
        this.status = status;
        this.admin = admin;
    }

    public AdminLoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
