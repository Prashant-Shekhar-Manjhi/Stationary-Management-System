package com.targetindia.stationarymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_email", unique = true)
    private String studentEmail;

    @Column(name = "student_password")
    private String studentPassword;

    @OneToMany
    @JoinColumn(name = "student_id")
    private List<Transaction> transactions;
}
