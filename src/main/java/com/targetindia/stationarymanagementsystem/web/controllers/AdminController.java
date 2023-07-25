package com.targetindia.stationarymanagementsystem.web.controllers;

import com.targetindia.stationarymanagementsystem.dto.AdminDTO;
import com.targetindia.stationarymanagementsystem.dto.AdminLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.model.AdminLoginResponse;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.targetindia.stationarymanagementsystem.web.validators.AdminValidator.isAdminCredentialValid;
import static com.targetindia.stationarymanagementsystem.web.validators.AdminValidator.isAdminValid;

@RestController
@CrossOrigin
@RequestMapping("/inventory/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //Registration...
    @PostMapping(consumes = "application/json")
    public ResponseEntity handleRegister(@RequestBody AdminDTO adminDTO) {
        if (!isAdminValid(adminDTO)) {
            return ResponseEntity.status(400).body(new Message("Invalid Input"));
        }

        try {
            Admin admin = new Admin();
            admin.setAdminName(adminDTO.getAdminName());
            admin.setAdminEmail(adminDTO.getAdminEmail());
            admin.setAdminPassword(adminDTO.getAdminPassword());
            admin.setDateOfBirth(adminDTO.getDateOfBirth());
            adminService.register(admin);
            return ResponseEntity.status(201).body(new Message("Registration Successful"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity handleAdminLogin(@RequestBody AdminLoginDTO loginDto){

        //validation...
        if(!isAdminCredentialValid(loginDto)){
            return ResponseEntity.status(400).body(new Message("Invalid input"));
        }

        try{
            Admin admin = adminService.adminLogin(loginDto);
            return ResponseEntity.ok(new AdminLoginResponse("Login Successful", true ,admin));
        }catch (Exception e){
            return ResponseEntity.status(401).body(new AdminLoginResponse(e.getMessage(), false));
        }
    }
}
