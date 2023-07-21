package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //HANDLE Registration of Student
    public void studentRegistration(StudentDTO studentDTO) throws DaoException {
        Student student = new Student();
        student.setStudentEmail(studentDTO.getStudentEmail());
        student.setStudentName(studentDTO.getStudentName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setStudentPassword(passwordEncoder.encode(studentDTO.getStudentPassword()));
        try{
            repository.save(student);
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }


    //Handle Login of Student
    public Student studentLogin(StudentLoginDTO loginDTO) throws DaoException {
        try{
            Student fetchedStudentByEmail = repository.findByStudentEmail(loginDTO.getStudentEmail());
            if(fetchedStudentByEmail != null){
                String password = loginDTO.getStudentPassword();
                String encodedPassword = fetchedStudentByEmail.getStudentPassword();
                if(passwordEncoder.matches(password, encodedPassword)) return fetchedStudentByEmail;
                else throw new Exception("Incorrect Password");
            }
            else throw new Exception("Incorrect Email");
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }

    public List<Student> getAllStudent(){
        try {
            return repository.findAll();
        }catch (Exception e){
            throw e;
        }
    }

    public Student findStudentById(Integer studentId) throws DaoException {
        try {
            Optional<Student> result = repository.findById(studentId);
            if(result.isPresent()) return result.get();
            return null;
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }
}
