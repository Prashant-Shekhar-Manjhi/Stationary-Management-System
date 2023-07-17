package com.targetindia.stationarymanagementsystem.repository;

import com.targetindia.stationarymanagementsystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findByStudentEmail(String email);
    public Optional<Student> findOneByStudentEmailAndStudentPassword(String studentEmail, String studentPassword);
}
