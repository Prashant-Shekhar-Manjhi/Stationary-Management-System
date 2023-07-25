package com.targetindia.stationarymanagementsystem.web.controllers;

import com.targetindia.stationarymanagementsystem.dto.TransactionDTO;
import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.services.StationaryItemService;
import com.targetindia.stationarymanagementsystem.services.StudentService;
import com.targetindia.stationarymanagementsystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targetindia.stationarymanagementsystem.web.validators.TransactionValidator.isTransactionValid;


@RestController
@CrossOrigin
@RequestMapping("/inventory/v1/transaction")
public class TransactionController{

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
            if(result.isEmpty()){
                return ResponseEntity.status(204).body(new Message("No transactions available."));
            }
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
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @GetMapping(path = "/{id}",produces = "application/json")
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
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));
        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @GetMapping(path = "/all/by_student_id", produces = "application/json")
    public ResponseEntity handleFindAllTransactionByStudentId(@RequestParam("id") Integer studentId){
        try {
            List <Transaction> transactionList = service.findAllTransactionByStudentId(studentId);
            if(transactionList.isEmpty()){
                return ResponseEntity.status(204).body(new Message("No transactions available with Student Id "+studentId));
            }
            List <TransactionDTO> transactionDTOList = transactionList.stream()
                    .map(transaction -> {
                        TransactionDTO temp = new TransactionDTO();
                        temp.setTransactionId(transaction.getTransactionId());
                        temp.setStudentId(transaction.getStudent().getStudentId());
                        temp.setWithdrawnQuantity(transaction.getWithdrawnQuantity());
                        temp.setReturned(transaction.getReturned());
                        temp.setReturnDate(transaction.getReturnDate());
                        temp.setStationaryItemId(transaction.getStationaryItem().getItemId());
                        return temp;
                    }).toList();
            return ResponseEntity.ok(transactionDTOList);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));
        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @GetMapping(path = "/all/by_item_id", produces = "application/json")
    public ResponseEntity handleFindAllTransactionByItemId(@RequestParam("id") Integer itemId){
        try {
            List <Transaction> transactionList = service.findAllTransactionByItemId(itemId);
            if(transactionList.isEmpty()){
                return ResponseEntity.status(204).body(new Message("No transactions available with Item Id "+itemId));
            }
            List <TransactionDTO> transactionDTOList = transactionList.stream()
                    .map(transaction -> {
                        TransactionDTO temp = new TransactionDTO();
                        temp.setTransactionId(transaction.getTransactionId());
                        temp.setStudentId(transaction.getStudent().getStudentId());
                        temp.setWithdrawnQuantity(transaction.getWithdrawnQuantity());
                        temp.setReturned(transaction.getReturned());
                        temp.setReturnDate(transaction.getReturnDate());
                        temp.setStationaryItemId(transaction.getStationaryItem().getItemId());
                        return temp;
                    }).toList();
            return ResponseEntity.ok(transactionDTOList);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    //Handle Post Mapping...
    @PostMapping(path = "/{studentId}",produces = "application/json", consumes = "application/json")
    public ResponseEntity handleCreateTransaction(@PathVariable Integer studentId, @RequestBody TransactionDTO transactionDT){
        //Validation...
        if(!isTransactionValid(transactionDT)){
            return ResponseEntity.status(400).body(new Message("Bad Request"));
        }
        try {
            Student student = studentService.findStudentById(studentId);
            StationaryItem stationaryItem = stationaryItemService.getOneItem(transactionDT.getStationaryItemId());

            if(student != null && stationaryItem != null){
                int totalQuantity = stationaryItem.getQuantity();
                int withDrawn = transactionDT.getWithdrawnQuantity();

                if(totalQuantity >= withDrawn){
                    Transaction transaction = new Transaction();
                    transaction.setStudent(student);
                    transaction.setStationaryItem(stationaryItem);
                    transaction.setWithdrawnQuantity(transactionDT.getWithdrawnQuantity());
                    transaction.setReturned(transactionDT.getReturned());
                    transaction.setReturnDate(transactionDT.getReturnDate());
                    service.createOneTransaction(transaction);

                    //update quantity
                    StationaryItem updatedItem = new StationaryItem();
                    updatedItem.setItemId(stationaryItem.getItemId());
                    updatedItem.setQuantity(totalQuantity-withDrawn);
                    stationaryItemService.updateItem(updatedItem);

                    return ResponseEntity.status(201).body(new Message("Created"));
                }else throw new Exception("Withdrawn quantity must be less than total quantity.");
            }else throw new ItemNotFoundException("Either Student or Item not found with their respective Ids");
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message("Transaction not created: "+e.getMessage()));
        }catch (DaoException e){
            return ResponseEntity.status(500).body(new Message("Transaction not created: "+e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(new Message("Transaction not created: "+e.getMessage()));
        }
    }
    //Handle Patch Mapping...
    @PatchMapping(path = "/{transactionId}",produces = "application/json", consumes = "application/json")
    public ResponseEntity handleUpdateTransaction(@PathVariable Integer transactionId, @RequestBody TransactionDTO transactionDTO){
        try {
            Transaction newTransaction = new Transaction();
            newTransaction.setTransactionId(transactionId);
            newTransaction.setReturned(transactionDTO.getReturned());
            newTransaction.setReturnDate(transactionDTO.getReturnDate());

            Transaction result = service.updateTransaction(newTransaction);

            TransactionDTO res = new TransactionDTO();
            res.setTransactionId(result.getTransactionId());
            res.setStudentId(result.getStudent().getStudentId());
            res.setStationaryItemId(result.getStationaryItem().getItemId());
            res.setWithdrawnQuantity(result.getWithdrawnQuantity());
            res.setReturned(result.getReturned());
            res.setReturnDate(result.getReturnDate());

            return ResponseEntity.status(200).body(res);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));
        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }
    //Handle Delete Mapping...
}
