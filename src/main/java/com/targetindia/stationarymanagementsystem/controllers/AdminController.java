package com.targetindia.stationarymanagementsystem.controllers;

import com.targetindia.stationarymanagementsystem.dto.AdminLoginDto;
import com.targetindia.stationarymanagementsystem.dto.AdminResponseDto;
import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.model.LoginResponse;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity handleRegister(@RequestBody Admin admin){
        try{
            adminService.register(admin);
            return ResponseEntity.ok(new Message("Registration Successful"));
        }catch (Exception e){
            return ResponseEntity.status(400).body(new Message("Registration Failed"));
        }

    }

    @GetMapping(produces = "application/json")
    public ResponseEntity handleGetAllAdmins(){
        try {
            List<Admin> result = adminService.getAllAdmin();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(404).body("List of Admins not Found!");
        }
    }

    @PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity handleAdminLogin(@RequestBody AdminLoginDto loginDto){
        try{
            Admin admin = adminService.adminLogin(loginDto);
            if(admin == null) return  ResponseEntity.status(401).body(new LoginResponse("Incorrect email and password", false));
            else {
                AdminResponseDto  adminResponseDto = new AdminResponseDto(
                        admin.getAdminId(),
                        admin.getAdminName(),
                        admin.getAdminEmail()
                );
                return ResponseEntity.ok(new LoginResponse("Login Successful", true ,adminResponseDto));
            }
        }catch (Exception e){
            return ResponseEntity.status(401).body(new LoginResponse("Incorrect email or password", false));
        }
    }

}
