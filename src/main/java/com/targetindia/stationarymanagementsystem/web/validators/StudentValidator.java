package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentValidator {
    public Boolean isStudentValid(StudentDTO studentDTO){
        if(studentDTO.getStudentEmail() == null || studentDTO.getStudentEmail().isBlank()) return false;
        if(studentDTO.getStudentName() == null || studentDTO.getStudentName().isBlank()) return false;
        if(studentDTO.getStudentPassword() == null || studentDTO.getStudentPassword().isBlank()) return false;
        return true;
    }

    public Boolean isStudentCredentialValid(StudentLoginDTO studentLoginDTO){
        if(studentLoginDTO.getStudentEmail() == null || studentLoginDTO.getStudentEmail().isBlank()) return false;
        if(studentLoginDTO.getStudentPassword() == null || studentLoginDTO.getStudentPassword().isBlank()) return  false;
        return true;
    }
}
