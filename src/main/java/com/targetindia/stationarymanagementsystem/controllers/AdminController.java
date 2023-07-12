package com.targetindia.stationarymanagementsystem.controllers;

import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.controllers.model.Message;
import com.targetindia.stationarymanagementsystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Registration Failed"));
        }

    }

    @GetMapping(produces = "application/json")
    public ResponseEntity handleGetAllAdmins(){
        List<Admin> result = adminService.getAllAdmin();

        return ResponseEntity.ok(result);
    }

}
