package com.targetindia.stationarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "admin_id", nullable = false)
    private Integer adminId;

    @Column(name = "admin_name", nullable = false)
    private String adminName;

    @Column(name = "admin_email", unique = true, nullable = false)
    private String adminEmail;

    @JsonIgnore
    @Column(name = "admin_password", nullable = false)
    private String adminPassword;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

}
