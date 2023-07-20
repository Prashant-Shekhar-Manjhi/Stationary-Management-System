package com.targetindia.stationarymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_email", unique = true)
    private String adminEmail;

    @Column(name = "admin_password")
    private String adminPassword;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

}
