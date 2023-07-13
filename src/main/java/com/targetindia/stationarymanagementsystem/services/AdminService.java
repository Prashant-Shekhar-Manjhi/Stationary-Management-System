package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.AdminLoginDto;
import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Admin admin){
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        try{
            adminRepository.save(admin);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Admin> getAllAdmin(){
        try{
            return adminRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    //Admin Login...
    public Admin adminLogin(AdminLoginDto loginDto){
        try{
            Admin fetchedAdminByEmail = adminRepository.findByAdminEmail(loginDto.getAdminEmail());
            if(fetchedAdminByEmail != null){
                String password = loginDto.getAdminPassword();
                String encodedPassword = fetchedAdminByEmail.getAdminPassword();
                if(passwordEncoder.matches(password, encodedPassword)){
                    Optional<Admin> fetchedAdminByEmailAndPassword = adminRepository.findOneByAdminEmailAndAdminPassword(loginDto.getAdminEmail(), encodedPassword);
                    if(fetchedAdminByEmailAndPassword.isPresent())
                        return fetchedAdminByEmailAndPassword.get();
                    else return  null;
                }
                throw new Exception("Incorrect Password");
            }
            else throw new Exception("Incorrect Email");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
