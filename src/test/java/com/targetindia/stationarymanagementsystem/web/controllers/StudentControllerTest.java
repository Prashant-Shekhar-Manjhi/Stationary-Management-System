package com.targetindia.stationarymanagementsystem.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.services.StudentService;
import com.targetindia.stationarymanagementsystem.web.validators.StudentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private StudentValidator studentValidator;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper objectMapper;

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void registrationValidInputTest() throws Exception {
        StudentDTO studentDTO = new StudentDTO("student", "student@example.com", "password");

        doNothing().when(studentService).studentRegistration(studentDTO);
        when(studentValidator.isStudentValid(any(StudentDTO.class))).thenReturn(true);
        mockMvc.perform(post("/inventory/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated());
    }
    @Test
    public void registrationInvalidInputTest() throws Exception {
        StudentDTO studentDTO = new StudentDTO("", "student@example.com", "password");
        when(studentValidator.isStudentValid(any(StudentDTO.class))).thenReturn(false);
        mockMvc.perform(post("/inventory/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void registrationConflictTest() throws Exception {
        StudentDTO studentDTO = new StudentDTO("student", "student@example.com", "password");

        doThrow(new DaoException("Conflict")).when(studentService).studentRegistration(studentDTO);
        when(studentValidator.isStudentValid(any(StudentDTO.class))).thenReturn(true);
        mockMvc.perform(post("/inventory/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void ValidStudentLoginTest() throws Exception {
        StudentLoginDTO loginDTO = new StudentLoginDTO("student@example.com", "password");
        Student student = new Student();
        when(studentService.studentLogin(loginDTO)).thenReturn(student);
        when(studentValidator.isStudentCredentialValid(any(StudentLoginDTO.class))).thenReturn(true);
        String requestJson = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/inventory/v1/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
    @Test
    public void invalidStudentLoginTest() throws Exception {
        StudentLoginDTO loginDTO = new StudentLoginDTO("", "password");
        String requestJson = objectMapper.writeValueAsString(loginDTO);
        when(studentValidator.isStudentValid(any(StudentDTO.class))).thenReturn(false);
        mockMvc.perform(post("/inventory/v1/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void wrongCredentialsStudentLoginTest() throws Exception {
        StudentLoginDTO loginDTO = new StudentLoginDTO("invalid-email", "password");
        String requestJson = objectMapper.writeValueAsString(loginDTO);
        when(studentService.studentLogin(loginDTO)).thenThrow(new RuntimeException("incorrect details"));
        when(studentValidator.isStudentCredentialValid(any(StudentLoginDTO.class))).thenReturn(true);
        mockMvc.perform(post("/inventory/v1/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void getAllStudentsTest() throws Exception {
        when(studentService.getAllStudent()).thenReturn(List.of(new Student()));

        mockMvc.perform(get("/inventory/v1/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getAllStudentsNoStudentTest() throws Exception {
        when(studentService.getAllStudent()).thenThrow(new RuntimeException("No students Found"));

        mockMvc.perform(get("/inventory/v1/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
