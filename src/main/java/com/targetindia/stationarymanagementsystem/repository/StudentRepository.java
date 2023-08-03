package com.targetindia.stationarymanagementsystem.repository;

import com.targetindia.stationarymanagementsystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findByStudentEmail(String email);
}
