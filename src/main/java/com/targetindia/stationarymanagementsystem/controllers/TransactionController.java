package com.targetindia.stationarymanagementsystem.controllers;

import com.targetindia.stationarymanagementsystem.dto.TransactionDTO;
import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.services.StationaryItemService;
import com.targetindia.stationarymanagementsystem.services.StudentService;
import com.targetindia.stationarymanagementsystem.services.TransactionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController  implements Serializable {

    @Autowired
    private TransactionService service;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StationaryItemService stationaryItemService;

    //Handle Get Mapping...
    @GetMapping(produces = "application/json")
    public ResponseEntity handleFindAllTransaction(){
        try {
            List<Transaction> result = service.findAllTransactions();
            List<TransactionDTO> res = result.stream().map(transaction -> {
                TransactionDTO temp = new TransactionDTO();
                temp.setTransactionId(transaction.getTransactionId());
                temp.setStudentId(transaction.getStudent().getStudentId());
                temp.setStationaryItemId(transaction.getStationaryItem().getItemId());
                temp.setWithdrawnQuantity(transaction.getWithdrawnQuantity());
                temp.setReturned(transaction.getReturned());
                temp.setReturnDate(transaction.getReturnDate());
                return temp;
            }).toList();
            return ResponseEntity.status(200).body(res);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Transactions Not Found"));
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity handleFindOneTransaction(@PathVariable Integer id){
        try {
            Transaction result = service.findOneTransaction(id);
            TransactionDTO res = new TransactionDTO();
            res.setTransactionId(result.getTransactionId());
            res.setStudentId(result.getStudent().getStudentId());
            res.setStationaryItemId(result.getStationaryItem().getItemId());
            res.setWithdrawnQuantity(result.getWithdrawnQuantity());
            res.setReturned(result.getReturned());
            res.setReturnDate(result.getReturnDate());
            return ResponseEntity.status(200).body(res);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Not Found!"));
        }
    }


    //Handle Post Mapping...
    @PostMapping(path = "/{studentId}",produces = "application/json", consumes = "application/json")
    public ResponseEntity handleCreateTransaction(@PathVariable Integer studentId, @RequestBody TransactionDTO transactionDT){
        try {
            Student student = studentService.findStudentById(studentId);
            StationaryItem stationaryItem = stationaryItemService.getOneItem(transactionDT.getStationaryItemId());

            if(student != null && stationaryItem != null){
                int totalQuantity = stationaryItem.getQuantity();
                int withDrawn = transactionDT.getWithdrawnQuantity();

                if(totalQuantity >= withDrawn){
                    //update quantity
                    StationaryItem updatedItem = new StationaryItem();
                    updatedItem.setItemId(stationaryItem.getItemId());
                    updatedItem.setQuantity(totalQuantity-withDrawn);
                    stationaryItemService.updateItem(updatedItem);

                    Transaction transaction = new Transaction();
                    transaction.setStudent(student);
                    transaction.setStationaryItem(stationaryItem);
                    transaction.setWithdrawnQuantity(transactionDT.getWithdrawnQuantity());
                    transaction.setReturned(transactionDT.getReturned());
                    transaction.setReturnDate(transactionDT.getReturnDate());
                    service.createOneTransaction(transaction);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Created"));
                }else throw new Exception();
            }else throw new Exception();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Transaction not created"));
        }
    }
    //Handle Patch Mapping...
    @PatchMapping(path = "/{studentId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity handleUpdateTransaction(@PathVariable Integer studentId, @RequestBody TransactionDTO transactionDTO){
        try {
//            transactionDTO.setTransactionId(id);
            Transaction result = service.updateTransaction(studentId, transactionDTO);

            TransactionDTO res = new TransactionDTO();
            res.setTransactionId(result.getTransactionId());
            res.setStudentId(result.getStudent().getStudentId());
            res.setStationaryItemId(result.getStationaryItem().getItemId());
            res.setWithdrawnQuantity(result.getWithdrawnQuantity());
            res.setReturned(result.getReturned());
            res.setReturnDate(result.getReturnDate());

            return ResponseEntity.status(200).body(res);
        }catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }
    //Handle Delete Mapping...
}
