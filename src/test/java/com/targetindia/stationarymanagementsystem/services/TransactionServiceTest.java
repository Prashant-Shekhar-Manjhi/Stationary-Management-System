package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.repository.StationaryItemRepository;
import com.targetindia.stationarymanagementsystem.repository.StudentRepository;
import com.targetindia.stationarymanagementsystem.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private StationaryItemRepository stationaryItemRepository;

    @Mock
    private StudentRepository studentRepository;

    private TransactionService transactionService;
    private Transaction transaction;
    private Student student;
    private StationaryItem item;
    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository, studentRepository, stationaryItemRepository);

        student = new Student();
        student.setStudentId(1);
        student.setStudentName("Prashant Shekhar");
        student.setStudentEmail("shekhar@gmail.com");
        student.setStudentPassword("123456");
        student.setDateOfBirth(new Date(23-05-2001));

        item = new StationaryItem();
        item.setItemId(1);
        item.setItemName("Pen");
        item.setQuantity(40);
        item.setReturnable(true);
        item.setMaxDays(5);

        transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setStudent(student);
        transaction.setStationaryItem(item);
        transaction.setWithdrawnQuantity(4);
        transaction.setReturnDate(new Date(23-07-2023));
        transaction.setReturned(false);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testCreateOneTransaction() throws DaoException {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        when(transactionRepository.save(transaction)).thenReturn(transaction);
        assertThat(transactionService.createOneTransaction(transaction).getTransactionId()).isEqualTo(transaction.getTransactionId());
    }

    @Test
    void testCreateOneTransactionException() throws DaoException {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        doThrow(new RuntimeException("Something went wrong!"))
                .when(transactionRepository)
                .save(any());

        assertThatThrownBy(()->transactionService.createOneTransaction(transaction))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }


    @Test
    void testFindOneTransaction() throws DaoException, ItemNotFoundException {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        when(transactionRepository.findById(1)).thenReturn(Optional.ofNullable(transaction));
        assertThat(transactionService.findOneTransaction(1).getStationaryItem().getItemName())
                .isEqualTo(transaction.getStationaryItem().getItemName());
    }

    @Test
    void testFindOneTransactionNotFoundException() throws DaoException, ItemNotFoundException {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        when(transactionRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->transactionService.findOneTransaction(1))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Transaction not found with id 1");
    }

    @Test
    void testFindOneTransactionDaoException() {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        doThrow(new RuntimeException("Something went wrong!"))
                .when(transactionRepository)
                .findById(any());

        assertThatThrownBy(()->transactionService.findOneTransaction(1))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }

    @Test
    void testFindAllTransactionByStudentId() throws DaoException, ItemNotFoundException {
        mock(Transaction.class);
        mock(Student.class);
        mock(TransactionRepository.class);
        mock(StudentRepository.class);

        when(studentRepository.findById(1)).thenReturn(Optional.ofNullable(student));
        when(transactionRepository.findAllByStudent(student))
                .thenReturn(new ArrayList<Transaction>(Collections.singleton(transaction)));

        assertThat(transactionService.findAllTransactionByStudentId(1).get(0).getStationaryItem().getItemName())
                .isEqualTo(transaction.getStationaryItem().getItemName());
    }

    @Test
    void testFindAllTransactionByStudentIdNotFoundException() {
        mock(Transaction.class);
        mock(Student.class);
        mock(TransactionRepository.class);
        mock(StudentRepository.class);

        when(studentRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->transactionService.findAllTransactionByStudentId(1))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Student not found with id 1");
    }

    @Test
    void testFindAllTransactionByStudentIdDaoException() {
        mock(Transaction.class);
        mock(Student.class);
        mock(TransactionRepository.class);
        mock(StudentRepository.class);

        when(studentRepository.findById(1)).thenReturn(Optional.ofNullable(student));
        doThrow(new RuntimeException("Something went wrong!"))
                .when(transactionRepository)
                .findAllByStudent(any());

        assertThatThrownBy(()->transactionService.findAllTransactionByStudentId(1))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }


    @Test
    void testFindAllTransactionByItemId() throws DaoException, ItemNotFoundException {
        mock(Transaction.class);
        mock(StationaryItem.class);
        mock(TransactionRepository.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(item));
        when(transactionRepository.findAllByStationaryItem(item))
                .thenReturn(new ArrayList<Transaction>(Collections.singleton(transaction)));

        assertThat(transactionService.findAllTransactionByItemId(1).get(0).getStationaryItem().getItemName())
                .isEqualTo(transaction.getStationaryItem().getItemName());
    }

    @Test
    void testFindAllTransactionByItemIdNotFoundException(){
        mock(Transaction.class);
        mock(StationaryItem.class);
        mock(TransactionRepository.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->transactionService.findAllTransactionByItemId(1))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Item not found with id 1");
    }

    @Test
    void testFindAllTransactionByItemIdDaoException(){
        mock(Transaction.class);
        mock(StationaryItem.class);
        mock(TransactionRepository.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(item));
        doThrow(new RuntimeException("Something went wrong!"))
                .when(transactionRepository)
                .findAllByStationaryItem(any());

        assertThatThrownBy(()->transactionService.findAllTransactionByItemId(1))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }

    @Test
    void testFindAllTransactions() throws DaoException {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        when(transactionRepository.findAll())
                .thenReturn(new ArrayList<Transaction>(Collections.singleton(transaction)));

        assertThat(transactionService.findAllTransactions().get(0).getStationaryItem().getItemName())
                .isEqualTo(transaction.getStationaryItem().getItemName());
    }

    @Test
    void testFindAllTransactionsDaoException() {
        mock(Transaction.class);
        mock(TransactionRepository.class);

        doThrow(new RuntimeException("Something went wrong!"))
                .when(transactionRepository)
                .findAll();

        assertThatThrownBy(()->transactionService.findAllTransactions())
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }

    @Test
    public void updateTransactionSuccessReturnedTrueTest() throws Exception {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setTransactionId(transaction.getTransactionId());
        updatedTransaction.setStudent(transaction.getStudent());
        updatedTransaction.setStationaryItem(transaction.getStationaryItem());
        updatedTransaction.setWithdrawnQuantity(transaction.getWithdrawnQuantity());
        updatedTransaction.setReturnDate(transaction.getReturnDate());
        updatedTransaction.setReturned(true);

        when(transactionRepository.findById(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);
        when(stationaryItemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));

        Transaction result = transactionService.updateTransaction(updatedTransaction);

        assertEquals(updatedTransaction, result);
    }
    @Test
    public void updateTransactionSuccessReturnedFalseTest() throws Exception {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setTransactionId(transaction.getTransactionId());
        updatedTransaction.setStudent(transaction.getStudent());
        updatedTransaction.setStationaryItem(transaction.getStationaryItem());
        updatedTransaction.setWithdrawnQuantity(transaction.getWithdrawnQuantity());
        updatedTransaction.setReturnDate(transaction.getReturnDate());
        updatedTransaction.setReturned(false);

        when(transactionRepository.findById(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);
        when(stationaryItemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));

        Transaction result = transactionService.updateTransaction(updatedTransaction);

        assertEquals(updatedTransaction, result);
    }
    @Test
    public void updateTransactionInternalErrorTest() throws Exception {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setTransactionId(transaction.getTransactionId());
        updatedTransaction.setStudent(transaction.getStudent());
        updatedTransaction.setStationaryItem(transaction.getStationaryItem());
        updatedTransaction.setWithdrawnQuantity(transaction.getWithdrawnQuantity());
        updatedTransaction.setReturnDate(transaction.getReturnDate());
        updatedTransaction.setReturned(true);

        when(transactionRepository.findById(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);
        when(stationaryItemRepository.findById(item.getItemId())).thenThrow(new RuntimeException("Internal server"));

        assertThrows(DaoException.class, () -> transactionService.updateTransaction(transaction));
    }
    @Test
    public void updateTransactionTransactionNotFoundTest() throws Exception {
        when(transactionRepository.findById(transaction.getTransactionId())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> transactionService.updateTransaction(transaction));
    }
}