package com.targetindia.stationarymanagementsystem.repository;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import org.assertj.core.api.PeriodAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StationaryItemRepository stationaryItemRepository;

    Transaction transaction;
    Student student;
    StationaryItem item;


    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentName("Prashant Shekhar");
        student.setStudentEmail("shekhar@gmail.com");
        student.setStudentPassword("123456");
        student.setDateOfBirth(new Date(23-05-2001));
        studentRepository.save(student);

        item = new StationaryItem();
        item.setItemName("Pen");
        item.setQuantity(40);
        item.setReturnable(true);
        item.setMaxDays(5);
        stationaryItemRepository.save(item);

        transaction = new Transaction();
        transaction.setStudent(student);
        transaction.setStationaryItem(item);
        transaction.setWithdrawnQuantity(4);
        transaction.setReturnDate(new Date(23-07-2023));
        transaction.setReturned(false);

        transaction = new Transaction();
        transaction.setStudent(student);
        transaction.setStationaryItem(item);
        transaction.setWithdrawnQuantity(4);
        transaction.setReturnDate(new Date(23-07-2023));
        transaction.setReturned(false);
        repository.save(transaction);
    }

    @AfterEach
    void tearDown() {
        transaction = null;
        student = null;
        item = null;
        repository.deleteAll();
        studentRepository.deleteAll();
        stationaryItemRepository.deleteAll();
    }

    @Test
    void testFindAllByStudent_Found(){
        List<Transaction> transactions = repository.findAllByStudent(student);
        assertThat(transactions.get(0).getTransactionId()).isEqualTo(transaction.getTransactionId());
    }

    @Test
    void testFindAllByStudent_NotFound(){
        student = new Student(2, "Prakash", "prakash@gmail.com",
                new Date(23-07-1999), "123456", new ArrayList<>());
        List<Transaction> transactionList = repository.findAllByStudent(student);
        assertThat(transactionList.isEmpty()).isTrue();
    }

    @Test
    void testFindAllByStationaryItem_Found(){
        List<Transaction> transactions = repository.findAllByStationaryItem(item);
        assertThat(transactions.get(0).getTransactionId()).isEqualTo(transaction.getTransactionId());
    }

    @Test
    void testFindAllByStationaryItem_NotFound(){
        item = new StationaryItem(2, "Pencil", 50, true,5, new ArrayList<>());
        List<Transaction> transactionList = repository.findAllByStationaryItem(item);
        assertThat(transactionList.isEmpty()).isTrue();
    }

}
