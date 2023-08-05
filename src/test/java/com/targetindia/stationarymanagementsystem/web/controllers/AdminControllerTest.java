package com.targetindia.stationarymanagementsystem.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.stationarymanagementsystem.dto.AdminDTO;
import com.targetindia.stationarymanagementsystem.dto.AdminLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.services.AdminService;
import com.targetindia.stationarymanagementsystem.web.validators.AdminValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @Mock
    private AdminValidator adminValidator;

    @InjectMocks
    private AdminController adminController;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void registerValidInputTest() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        AdminDTO adminDTO = new AdminDTO("admin", "admin@example.com", "123456", sdf.parse("06-22-2000"));
        Admin admin = new Admin();
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminEmail(adminDTO.getAdminEmail());
        admin.setAdminPassword(adminDTO.getAdminPassword());
        admin.setDateOfBirth(adminDTO.getDateOfBirth());

        doNothing().when(adminService).register(admin);
        when(adminValidator.isAdminValid(any(AdminDTO.class))).thenReturn(true);
        String requestJson = objectMapper.writeValueAsString(adminDTO);

        mockMvc.perform(post("/inventory/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void registerInvalidInputTest() throws Exception {
        // Test when invalid input data is provided for registration
        AdminDTO adminDTO = new AdminDTO("", "admin@example.com", "123456", new Date());
        String requestJson = objectMapper.writeValueAsString(adminDTO);
        when(adminValidator.isAdminValid(any(AdminDTO.class))).thenReturn(false);
        mockMvc.perform(post("/inventory/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(adminService, never()).register(any(Admin.class));
    }

    @Test
    public void registerErrorTest() throws Exception {
        // Test when an error occurs during the registration process
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        AdminDTO adminDTO = new AdminDTO("admin", "admin@example.com", "123456", sdf.parse("06-22-2000"));
        Admin admin = new Admin();
        admin.setAdminName(adminDTO.getAdminName());
        admin.setAdminEmail(adminDTO.getAdminEmail());
        admin.setAdminPassword(adminDTO.getAdminPassword());
        admin.setDateOfBirth(adminDTO.getDateOfBirth());

        doThrow(new RuntimeException("Error during registration")).when(adminService).register(admin);
        when(adminValidator.isAdminValid(any(AdminDTO.class))).thenReturn(true);
        String requestJson = objectMapper.writeValueAsString(adminDTO);

        mockMvc.perform(post("/inventory/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());

        verify(adminService, times(1)).register(admin);
    }

    @Test
    public void loginValidInputTest() throws Exception {
        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@example.com", "123456");

        when(adminService.adminLogin(loginDTO)).thenReturn(new Admin());
        when(adminValidator.isAdminCredentialValid(any(AdminLoginDTO.class))).thenReturn(true);
        String reqestJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/inventory/v1/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void loginInvalidCredentialsTest() throws Exception {
        // Test when invalid admin login credentials are provided
        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@example.com", "");

        when(adminValidator.isAdminCredentialValid(any(AdminLoginDTO.class))).thenReturn(false);

        String requestJson = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/inventory/v1/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginWrongCredentialsTest() throws Exception {
        // Test when invalid admin login credentials are provided
        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@example.com", "wrongpassword");

        when(adminService.adminLogin(loginDTO)).thenAnswer((InvocationOnMock invocation) -> { throw new ItemNotFoundException("wrong password"); });

        when(adminValidator.isAdminCredentialValid(any(AdminLoginDTO.class))).thenReturn(true);
        String requestJson = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/inventory/v1/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loginInternalServerErrorTest() throws Exception {
        // Test when an internal server error occurs during the admin login process
        AdminLoginDTO loginDTO = new AdminLoginDTO("admin@example.com", "123456");
        when(adminService.adminLogin(loginDTO)).thenThrow(new DaoException("Internal Server Error during login"));
        when(adminValidator.isAdminCredentialValid(any(AdminLoginDTO.class))).thenReturn(true);
        String requestJson = objectMapper.writeValueAsString(loginDTO);


        mockMvc.perform(post("/inventory/v1/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());

        verify(adminService, times(1)).adminLogin(loginDTO);
    }
}
