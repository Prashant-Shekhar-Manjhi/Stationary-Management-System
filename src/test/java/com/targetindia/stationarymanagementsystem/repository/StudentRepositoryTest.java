package com.targetindia.stationarymanagementsystem.repository;

import com.targetindia.stationarymanagementsystem.entities.Admin;
import com.targetindia.stationarymanagementsystem.entities.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;
    Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1, "AkashDeep", "akash@gmail.com",
                new Date(23-07-1998), "123456", new ArrayList<>());
        repository.save(student);
    }

    @AfterEach
    void tearDown() {
        student = null;
        repository.deleteAll();
    }

    @Test
    void testFindByStudentEmail_Found(){
        Student studentFromDb = repository.findByStudentEmail("akash@gmail.com");
        assertThat(studentFromDb.getStudentId()).isEqualTo(student.getStudentId());
        assertThat(studentFromDb.getStudentName()).isEqualTo(student.getStudentName());
    }

    @Test
    void testFindByStudentEmail_NotFound(){
        Student studentFromDb = repository.findByStudentEmail("shekhar@gmail.com");
        assertThat(studentFromDb).isNull();
    }
}
