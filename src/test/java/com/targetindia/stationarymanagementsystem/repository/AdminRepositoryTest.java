package com.targetindia.stationarymanagementsystem.repository;

import com.targetindia.stationarymanagementsystem.entities.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository repository;
    Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin(1, "Prashant Shekhar",
                "shekhar@gmail.com", "12345",new Date(23-05-2001));
        repository.save(admin);
    }

    @AfterEach
    void tearDown() {
        admin = null;
        repository.deleteAll();
    }

    //Test Cases
    @Test
    void testFindByAdminEmail_Found(){
        Admin adminFromDb = repository.findByAdminEmail("shekhar@gmail.com");
        assertThat(adminFromDb.getAdminId()).isEqualTo(admin.getAdminId());
        assertThat(adminFromDb.getAdminName()).isEqualTo(admin.getAdminName());
    }

    @Test
    void testFindByAdminEmail_NotFound(){
        Admin adminFromDb = repository.findByAdminEmail("prashant@gmail.com");
        assertThat(adminFromDb).isNull();
    }
}
