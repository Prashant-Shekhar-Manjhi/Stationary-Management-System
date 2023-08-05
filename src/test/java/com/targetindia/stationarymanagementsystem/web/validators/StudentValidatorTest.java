package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentValidatorTest {

    private StudentValidator studentValidator = new StudentValidator();

    @Test
    public void isStudentValidTestValidStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentEmail("saicharan@example.com");
        studentDTO.setStudentName("saicharan");
        studentDTO.setStudentPassword("secretpassword");

        boolean result = studentValidator.isStudentValid(studentDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void isStudentValidTestInvalidStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentEmail("");
        studentDTO.setStudentName(null);
        studentDTO.setStudentPassword("secretpassword");

        boolean result = studentValidator.isStudentValid(studentDTO);
        Assertions.assertFalse(result);
    }

    @Test
    public void isStudentCredentialValidTestValidStudentLoginDTO() {
        StudentLoginDTO studentLoginDTO = new StudentLoginDTO();
        studentLoginDTO.setStudentEmail("saicharan@example.com");
        studentLoginDTO.setStudentPassword("secretpassword");

        boolean result = studentValidator.isStudentCredentialValid(studentLoginDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void isStudentCredentialValidTestInvalidStudentLoginDTO() {
        StudentLoginDTO studentLoginDTO = new StudentLoginDTO();
        studentLoginDTO.setStudentEmail("");
        studentLoginDTO.setStudentPassword(null);

        boolean result = studentValidator.isStudentCredentialValid(studentLoginDTO);
        Assertions.assertFalse(result);
    }
}
