package com.targetindia.stationarymanagementsystem.web.controllers;

import com.targetindia.stationarymanagementsystem.dto.TransactionRequestDTO;
import com.targetindia.stationarymanagementsystem.dto.TransactionResponseDTO;
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

            List<TransactionResponseDTO> res = result.stream().map(transaction -> {
                return getTransactionResponseDTO(transaction);
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
            TransactionResponseDTO res = getTransactionResponseDTO(result);
            return ResponseEntity.status(200).body(res);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));
        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    private static TransactionResponseDTO getTransactionResponseDTO(Transaction result) {
        TransactionResponseDTO res = new TransactionResponseDTO();
        res.setTransactionId(result.getTransactionId());
        res.setStudent(result.getStudent());
        res.setStationaryItem(result.getStationaryItem());
        res.setWithdrawnQuantity(result.getWithdrawnQuantity());
        res.setTransactionDate(result.getTransactionDate());
        res.setReturnDate(result.getReturnDate());
        res.setReturned(result.getReturned());
        return res;
    }

    @GetMapping(path = "/all/by_student_id", produces = "application/json")
    public ResponseEntity handleFindAllTransactionByStudentId(@RequestParam("id") Integer studentId){
        try {
            List <Transaction> transactionList = service.findAllTransactionByStudentId(studentId);
            if(transactionList.isEmpty()){
                return ResponseEntity.status(204).body(new Message("No transactions available with Student Id "+studentId));
            }
            List <TransactionResponseDTO> list = transactionList.stream()
                    .map(transaction -> {
                        return getTransactionResponseDTO(transaction);
                    }).toList();
            return ResponseEntity.ok(list);
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
            List <TransactionResponseDTO> list = transactionList.stream()
                    .map(transaction -> {
                        return getTransactionResponseDTO(transaction);
                    }).toList();
            return ResponseEntity.ok(list);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    //Handle Post Mapping...
    @PostMapping(path = "/{studentId}",produces = "application/json", consumes = "application/json")
    public ResponseEntity handleCreateTransaction(@PathVariable Integer studentId, @RequestBody TransactionRequestDTO transactionDT){
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
    public ResponseEntity handleUpdateTransaction(@PathVariable Integer transactionId, @RequestBody TransactionRequestDTO transactionDTO){
        try {
            Transaction newTransaction = new Transaction();
            newTransaction.setTransactionId(transactionId);
            newTransaction.setReturned(transactionDTO.getReturned());
            newTransaction.setReturnDate(transactionDTO.getReturnDate());

            Transaction result = service.updateTransaction(newTransaction);

            TransactionResponseDTO res = getTransactionResponseDTO(result);
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
