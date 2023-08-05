package com.targetindia.stationarymanagementsystem.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.stationarymanagementsystem.dto.TransactionRequestDTO;
import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.services.StationaryItemService;
import com.targetindia.stationarymanagementsystem.services.StudentService;
import com.targetindia.stationarymanagementsystem.services.TransactionService;
import com.targetindia.stationarymanagementsystem.web.validators.TransactionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @Mock
    private StudentService studentService;

    @Mock
    private StationaryItemService stationaryItemService;

    @Mock
    private TransactionValidator transactionValidator;

    @InjectMocks
    private TransactionController transactionController;

    private ObjectMapper objectMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void findAllTransactionsTest() throws Exception {
        when(transactionService.findAllTransactions()).thenReturn(List.of(new Transaction()));

        mockMvc.perform(get("/inventory/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void findAllTransactionsNoTransactionsTest() throws Exception {
        when(transactionService.findAllTransactions()).thenReturn(List.of());

        mockMvc.perform(get("/inventory/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void findAllTransactionsInternalErrorTest() throws Exception {
        when(transactionService.findAllTransactions()).thenThrow(new DaoException("Internal Server Error"));

        mockMvc.perform(get("/inventory/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findOneTransactionTest() throws Exception {
        Integer transactionId = 1;
        when(transactionService.findOneTransaction(transactionId)).thenReturn(new Transaction());

        mockMvc.perform(get("/inventory/v1/transaction/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void findOneTransactionNotFoundTest() throws Exception {
        Integer transactionId = 1;
        when(transactionService.findOneTransaction(transactionId)).thenThrow(new ItemNotFoundException("Transaction Not Foun"));

        mockMvc.perform(get("/inventory/v1/transaction/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void findOneTransactionInternalServerTest() throws Exception {
        Integer transactionId = 1;
        when(transactionService.findOneTransaction(transactionId)).thenThrow(new DaoException("Internal Server"));

        mockMvc.perform(get("/inventory/v1/transaction/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findAllTransactionsByStudentIdTest() throws Exception {
        Integer studentId = 1;
        when(transactionService.findAllTransactionByStudentId(studentId)).thenReturn(List.of(new Transaction()));

        mockMvc.perform(get("/inventory/v1/transaction/all/by_student_id")
                        .param("id", studentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void findAllTransactionsByStudentIdNoTransactionsTest() throws Exception {
        Integer studentId = 1;
        when(transactionService.findAllTransactionByStudentId(studentId)).thenReturn(List.of());

        mockMvc.perform(get("/inventory/v1/transaction/all/by_student_id")
                        .param("id", studentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void findAllTransactionsByStudentIdNoStudentIdTest() throws Exception {
        Integer studentId = 1;
        when(transactionService.findAllTransactionByStudentId(studentId)).thenThrow( new ItemNotFoundException("Student is not found with id:"+studentId));

        mockMvc.perform(get("/inventory/v1/transaction/all/by_student_id")
                        .param("id", studentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void findAllTransactionsByStudentIdInternalServerTest() throws Exception {
        Integer studentId = 1;
        when(transactionService.findAllTransactionByStudentId(studentId)).thenThrow(new DaoException("Internal Server Error"));

        mockMvc.perform(get("/inventory/v1/transaction/all/by_student_id")
                        .param("id", studentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findAllTransactionsByItemIdTest() throws Exception {
        Integer itemId = 1;
        when(transactionService.findAllTransactionByItemId(itemId)).thenReturn(List.of(new Transaction()));

        mockMvc.perform(get("/inventory/v1/transaction/all/by_item_id")
                        .param("id", itemId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void findAllTransactionsByItemIdNoTransactionsTest() throws Exception {
        Integer itemId = 1;
        when(transactionService.findAllTransactionByItemId(itemId)).thenReturn(List.of());

        mockMvc.perform(get("/inventory/v1/transaction/all/by_item_id")
                        .param("id", itemId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void findAllTransactionsByItemNoItemIdTest() throws Exception {
        Integer itemId = 1;
        when(transactionService.findAllTransactionByItemId(itemId)).thenThrow(new ItemNotFoundException("There is no item with item id : "+itemId));

        mockMvc.perform(get("/inventory/v1/transaction/all/by_item_id")
                        .param("id", itemId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void findAllTransactionsByItemIdInternalServerTest() throws Exception {
        Integer itemId = 1;
        when(transactionService.findAllTransactionByItemId(itemId)).thenThrow(new DaoException("Internal server error"));

        mockMvc.perform(get("/inventory/v1/transaction/all/by_item_id")
                        .param("id", itemId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void createTransactionTest() throws Exception {
        Integer studentId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(1);
        transactionDTO.setWithdrawnQuantity(1);
        transactionDTO.setReturned(false);
        transactionDTO.setReturnDate(sdf.parse("10-29-2000"));
        //because of getting null while testing
        Student student = new Student(1,"name","name@gamil.com",sdf.parse("03-12-2001"), "root", List.of(new Transaction()));
        when(studentService.findStudentById(studentId)).thenReturn(student);
        //same above reason
        StationaryItem stationaryItem = new StationaryItem(1,"pen",10,false,2,List.of(new Transaction()));
        when(stationaryItemService.getOneItem(transactionDTO.getStationaryItemId())).thenReturn(stationaryItem);

        Transaction transaction = new Transaction(1,student,stationaryItem,2,new Date(),new Date(),false);
        when(transactionService.createOneTransaction(any())).thenReturn(transaction);
        when(transactionValidator.isTransactionValid(any(TransactionRequestDTO.class))).thenReturn(true);

        //updated item
        StationaryItem updatedItem = new StationaryItem(1,"pen",stationaryItem.getQuantity()-transactionDTO.getWithdrawnQuantity(),false,2,List.of(new Transaction()));

        when(stationaryItemService.updateItem(stationaryItem)).thenReturn(updatedItem);
        mockMvc.perform(post("/inventory/v1/transaction/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated());
    }
    @Test
    public void createTransactionInValidTest() throws Exception {
        Integer studentId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(1);
        transactionDTO.setWithdrawnQuantity(1);
        when(transactionValidator.isTransactionValid(any(TransactionRequestDTO.class))).thenReturn(false);
        mockMvc.perform(post("/inventory/v1/transaction/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void createTransactionInternalServerTest() throws Exception {
        Integer studentId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(1);
        transactionDTO.setWithdrawnQuantity(1);
        transactionDTO.setReturned(false);
        transactionDTO.setReturnDate(sdf.parse("10-29-2000"));

        Student student = new Student(1,"name","name@gamil.com",sdf.parse("03-12-2001"), "root", List.of(new Transaction()));
        when(studentService.findStudentById(studentId)).thenReturn(student);

        StationaryItem stationaryItem = new StationaryItem(1,"pen",10,false,2,List.of(new Transaction()));
        when(stationaryItemService.getOneItem(transactionDTO.getStationaryItemId())).thenReturn(stationaryItem);


        Transaction transaction = new Transaction(1,student,stationaryItem,2,new Date(),new Date(),false);
        when(transactionService.createOneTransaction(any())).thenThrow(new DaoException("Internal Server"));

        when(transactionValidator.isTransactionValid(any(TransactionRequestDTO.class))).thenReturn(true);
        //updated item
        StationaryItem updatedItem = new StationaryItem(1,"pen",stationaryItem.getQuantity()-transactionDTO.getWithdrawnQuantity(),false,2,List.of(new Transaction()));

        when(stationaryItemService.updateItem(stationaryItem)).thenReturn(updatedItem);
        mockMvc.perform(post("/inventory/v1/transaction/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void createTransactionInvalidItemIdOrStudentIdTest() throws Exception {
        Integer studentId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(1);
        transactionDTO.setWithdrawnQuantity(1);
        transactionDTO.setReturned(false);
        transactionDTO.setReturnDate(sdf.parse("10-29-2000"));
        //because of getting null while testing
        when(studentService.findStudentById(studentId)).thenReturn(null);
        //same above reason
        when(transactionValidator.isTransactionValid(any(TransactionRequestDTO.class))).thenReturn(true);
        when(stationaryItemService.getOneItem(transactionDTO.getStationaryItemId())).thenReturn(null);

        mockMvc.perform(post("/inventory/v1/transaction/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void createTransactionWithdrawnQuantityLessTest() throws Exception {
        Integer studentId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(1);
        transactionDTO.setWithdrawnQuantity(10);
        transactionDTO.setReturned(false);
        transactionDTO.setReturnDate(sdf.parse("10-29-2000"));
        //because of getting null while testing
        Student student = new Student(1,"name","name@gamil.com",sdf.parse("03-12-2001"), "root", List.of(new Transaction()));
        when(studentService.findStudentById(studentId)).thenReturn(student);
        //same above reason
        StationaryItem stationaryItem = new StationaryItem(1,"pen",2,false,2,List.of(new Transaction()));
        when(stationaryItemService.getOneItem(transactionDTO.getStationaryItemId())).thenReturn(stationaryItem);
        when(transactionValidator.isTransactionValid(any(TransactionRequestDTO.class))).thenReturn(true);
        when(transactionService.createOneTransaction(any())).thenThrow(new RuntimeException("Withdrawn quantity must be less than quantity"));
        mockMvc.perform(post("/inventory/v1/transaction/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateTransactionTest() throws Exception {
        Integer transactionId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setReturned(true);
        transactionDTO.setReturnDate(sdf.parse("02-21-2001"));

        when(transactionService.updateTransaction(any())).thenReturn(new Transaction());

        mockMvc.perform(patch("/inventory/v1/transaction/{transactionId}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTransactionNotFoundTest() throws Exception {
        Integer transactionId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setReturned(true);
        transactionDTO.setReturnDate(sdf.parse("02-21-2001"));

        when(transactionService.updateTransaction(any())).thenThrow(new ItemNotFoundException("Not found for id :"+transactionId));

        mockMvc.perform(patch("/inventory/v1/transaction/{transactionId}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTransactionInternalServerTest() throws Exception {
        Integer transactionId = 1;
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setReturned(true);
        transactionDTO.setReturnDate(sdf.parse("02-21-2001"));

        when(transactionService.updateTransaction(any())).thenThrow(new DaoException("IntenalServer"));

        mockMvc.perform(patch("/inventory/v1/transaction/{transactionId}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isInternalServerError());
    }

    // More test cases for other controller endpoints and functionalities
}

