package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.AdminDTO;
import com.targetindia.stationarymanagementsystem.dto.AdminLoginDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AdminValidatorTest {
    private AdminValidator adminValidator = new AdminValidator();

    @Test
    public void isAdminValidTestValidAdminDTO() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName("saicharan");
        adminDTO.setAdminEmail("saicharan@example.com");
        adminDTO.setAdminPassword("secretpassword");

        boolean result = adminValidator.isAdminValid(adminDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void isAdminValidTestInvalidEmailAdminDTO() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName("saicharan");
        adminDTO.setAdminEmail("");
        adminDTO.setAdminPassword("secretpassword");

        boolean result = adminValidator.isAdminValid(adminDTO);
        Assertions.assertFalse(result);
    }
    @Test
    public void isAdminValidTestInvalidNameAdminDTO() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName("");
        adminDTO.setAdminEmail("saicharna@gmail.com");
        adminDTO.setAdminPassword("secretpassword");

        boolean result = adminValidator.isAdminValid(adminDTO);
        Assertions.assertFalse(result);
    }
    @Test
    public void isAdminValidTestInvalidPasswordAdminDTO() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminName("saicharan");
        adminDTO.setAdminEmail("saicharan@gmail.com");
        adminDTO.setAdminPassword("");
        boolean result = adminValidator.isAdminValid(adminDTO);
        Assertions.assertFalse(result);
    }

    @Test
    public void isAdminCredentialValidValidAdminLoginDTOTest() {
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        adminLoginDTO.setAdminEmail("saicharan@example.com");
        adminLoginDTO.setAdminPassword("secretpassword");

        boolean result = adminValidator.isAdminCredentialValid(adminLoginDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void isAdminCredentialValidInvalidAdminLoginDTOTest() {
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        adminLoginDTO.setAdminEmail("");
        adminLoginDTO.setAdminPassword("secretpassword");

        boolean result = adminValidator.isAdminCredentialValid(adminLoginDTO);
        Assertions.assertFalse(result);
    }
}
