package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.AdminDTO;
import com.targetindia.stationarymanagementsystem.dto.AdminLoginDTO;

public class AdminValidator {

    public static  Boolean isAdminValid(AdminDTO adminDTO){
        if(adminDTO.getAdminName() == null || adminDTO.getAdminName().isBlank()){
            return false;
        }

        if(adminDTO.getAdminEmail() == null || adminDTO.getAdminEmail().isBlank()){
            return false;
        }

        if(adminDTO.getAdminPassword() == null || adminDTO.getAdminPassword().isBlank()){
            return false;
        }
        return true;
    }

    public static Boolean isAdminCredentialValid(AdminLoginDTO adminLoginDTO){
        if (adminLoginDTO.getAdminEmail() == null || adminLoginDTO.getAdminEmail().isBlank()) return false;
        if (adminLoginDTO.getAdminPassword() == null || adminLoginDTO.getAdminPassword().isBlank()) return false;
        return true;
    }
}
