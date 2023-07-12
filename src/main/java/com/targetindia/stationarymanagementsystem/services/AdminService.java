package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void register(Admin admin){
        try{
            adminRepository.save(admin);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
}
