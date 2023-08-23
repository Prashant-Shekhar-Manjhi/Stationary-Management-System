package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.AdminLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerTest() throws Exception {
        Admin admin = new Admin(1, "admin", "admin@gmail.com", "123456", new Date());
        when(adminRepository.save(admin)).thenReturn(null);

        assertDoesNotThrow(() -> adminService.register(admin));

    }
    @Test
    public void registerErrorTest() throws Exception {
        Admin admin = new Admin(1, "admin", "admin@gmail.com", "123456", new Date());
        when(adminRepository.save(admin)).thenAnswer(invocation -> {
            throw new DaoException("Error saving admin");
        });


        assertThrows(DaoException.class,()->adminService.register(admin));
    }

    @Test
    public void loginSuccessTest() throws Exception {
        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@gmail.com", "123456");

        Admin testAdmin = new Admin();
        testAdmin.setAdminEmail("admin@gmail.com");
        String encodedPassword = passwordEncoder.encode("123456");
        testAdmin.setAdminPassword(encodedPassword);

        when(adminRepository.findByAdminEmail("admin@gmail.com")).thenReturn(testAdmin);
        when(passwordEncoder.matches(loginDTO.getAdminPassword(), testAdmin.getAdminPassword())).thenReturn(true);

        Admin loggedInAdmin = adminService.adminLogin(loginDTO);

        assertEquals(testAdmin, loggedInAdmin);
    }

    @Test
    public void loginFailureIncorrectEmailTest() {
        AdminLoginDTO loginDTO = new AdminLoginDTO("invalid@gmail.com", "123456");

        when(adminRepository.findByAdminEmail("invalid@gmail.com")).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> adminService.adminLogin(loginDTO));
    }

    @Test
    public void loginFailureIncorrectPasswordTest() {

        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@gmail.com", "wrongpassword");

        Admin testAdmin = new Admin();
        testAdmin.setAdminEmail("admin@gmail.com");
        String encodedPassword = passwordEncoder.encode("123456");
        testAdmin.setAdminPassword(encodedPassword);

        when(adminRepository.findByAdminEmail("admin@gmail.com")).thenReturn(testAdmin);
        when(passwordEncoder.matches(loginDTO.getAdminPassword(), testAdmin.getAdminPassword())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> adminService.adminLogin(loginDTO));
    }

    @Test
    public void loginFailureDaoExceptionTest() {

        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@gmail.com", "123456");


        when(adminRepository.findByAdminEmail("admin@gmail.com")).thenThrow(new RuntimeException("Database Error"));

        assertThrows(DaoException.class, () -> adminService.adminLogin(loginDTO));
    }
}
