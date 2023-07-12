package com.targetindia.stationarymanagementsystem.repository;

import com.targetindia.stationarymanagementsystem.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
