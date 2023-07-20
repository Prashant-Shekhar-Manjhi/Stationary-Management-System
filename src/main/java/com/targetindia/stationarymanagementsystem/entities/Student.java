package com.targetindia.stationarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
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

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @JsonIgnore
    @Column(name = "student_password")
    private String studentPassword;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "student_id")
    private List<Transaction> transactions;
}
