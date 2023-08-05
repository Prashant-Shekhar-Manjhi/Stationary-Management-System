package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void studentRegistrationTest() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentEmail("student@gmail.com");
        studentDTO.setStudentName("John Doe");
        studentDTO.setDateOfBirth(sdf.parse("1995-01-01"));
        studentDTO.setStudentPassword("password123");

        Student studentToAdd = new Student();
        studentToAdd.setStudentEmail(studentDTO.getStudentEmail());
        studentToAdd.setStudentName(studentDTO.getStudentName());
        studentToAdd.setDateOfBirth(studentDTO.getDateOfBirth());
        studentToAdd.setStudentPassword("hashedPassword");

        when(passwordEncoder.encode(studentDTO.getStudentPassword())).thenReturn("hashedPassword");
        when(studentRepository.save(studentToAdd)).thenReturn(studentToAdd);

        studentService.studentRegistration(studentDTO);


    }
    @Test
    public void studentRegistrationFailTest() throws Exception {
        StudentDTO studentDTO = new StudentDTO("Student", "student@example.com", "password");

        when(passwordEncoder.encode(studentDTO.getStudentPassword())).thenReturn("encoded_password");
        when(studentRepository.save(any(Student.class))).thenThrow(new RuntimeException("Failed to save student."));

        assertThrows(DaoException.class, () -> studentService.studentRegistration(studentDTO));

    }


    @Test
    public void studentLoginTest() throws DaoException {
        StudentLoginDTO loginDTO = new StudentLoginDTO();
        loginDTO.setStudentEmail("student@gmail.com");
        loginDTO.setStudentPassword("password123");

        Student fetchedStudent = new Student();
        fetchedStudent.setStudentEmail(loginDTO.getStudentEmail());
        fetchedStudent.setStudentPassword("hashedPassword");

        when(studentRepository.findByStudentEmail(loginDTO.getStudentEmail())).thenReturn(fetchedStudent);
        when(passwordEncoder.matches(loginDTO.getStudentPassword(), fetchedStudent.getStudentPassword())).thenReturn(true);

        Student loggedInStudent = studentService.studentLogin(loginDTO);

        assertNotNull(loggedInStudent);
        assertEquals(fetchedStudent, loggedInStudent);
    }
    @Test
    public void studentLoginInvalidEmailTest() {
        String email = "john@example.com";
        String password = "password";
        StudentLoginDTO loginDTO = new StudentLoginDTO(email, password);

        when(studentRepository.findByStudentEmail(email)).thenReturn(null);

        assertThrows(DaoException.class, () -> studentService.studentLogin(loginDTO));

    }
    @Test
    public void studentLoginInvalidPasswordTest() {
        String email = "john@example.com";
        String password = "password";
        StudentLoginDTO loginDTO = new StudentLoginDTO(email, password);
        Student student = new Student();
        student.setStudentEmail(email);
        student.setStudentPassword("encoded_password");

        when(studentRepository.findByStudentEmail(email)).thenReturn(student);
        when(passwordEncoder.matches(password, student.getStudentPassword())).thenReturn(false);

        assertThrows(DaoException.class, () -> studentService.studentLogin(loginDTO));
    }

    @Test
    public void getAllStudentTest() throws DaoException {
        List<Student> studentListFromRepo = new ArrayList<>();

        when(studentRepository.findAll()).thenReturn(studentListFromRepo);

        List<Student> fetchedStudentList = studentService.getAllStudent();

        assertNotNull(fetchedStudentList);
        assertEquals(studentListFromRepo.size(), fetchedStudentList.size());
    }
    @Test
    public void getAllStudentErrorTest() {
        List<Student> studentListFromRepo = new ArrayList<>();

        when(studentRepository.findAll()).thenThrow(new RuntimeException("Internal error"));
        assertThrows(DaoException.class,() ->studentService.getAllStudent());

    }



    @Test
    public void findStudentByIdTest() throws DaoException {
        int studentId = 1;
        Student studentFromRepo = new Student();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentFromRepo));

        Student fetchedStudent = studentService.findStudentById(studentId);

        assertNotNull(fetchedStudent);
        assertEquals(studentFromRepo, fetchedStudent);
    }
    @Test
    public void findStudentByIdNotFoundTest() {
        Integer studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(DaoException.class, () -> studentService.findStudentById(studentId));

    }
}
