package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.AdminLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Admin admin) throws DaoException {
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        try{
            adminRepository.save(admin);
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }

    //Admin Login...
    public Admin adminLogin(AdminLoginDTO loginDto) throws DaoException {
        try{
            Admin fetchedAdminByEmail = adminRepository.findByAdminEmail(loginDto.getAdminEmail());
            if(fetchedAdminByEmail != null){
                String password = loginDto.getAdminPassword();
                String encodedPassword = fetchedAdminByEmail.getAdminPassword();
                if(passwordEncoder.matches(password, encodedPassword)) return fetchedAdminByEmail;
                else throw new DaoException("Incorrect Password");
            }
            else throw new DaoException("Incorrect Email");
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }

    }
}
