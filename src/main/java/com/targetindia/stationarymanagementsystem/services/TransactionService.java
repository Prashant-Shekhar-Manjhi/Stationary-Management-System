package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.TransactionDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.repository.StudentRepository;
import com.targetindia.stationarymanagementsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService{

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private StudentRepository studentRepository;

    //create transaction....
    public Transaction createOneTransaction(Transaction transaction){
        try{
            return repository.save(transaction);
        }catch (Exception e){
            throw e;
        }
    }

    public List<Transaction> createManyTransactions(List<Transaction> transactions){
        try{
            return repository.saveAll(transactions);
        }catch (Exception e){
            throw e;
        }
    }
    //read transaction....
    public Transaction findOneTransaction(Integer transactionId){
        try {
            Optional<Transaction> result = repository.findById(transactionId);
            if(result.isPresent()) return result.get();
            else return null;
        }catch (Exception e){
            throw e;
        }
    }

    public List<Transaction> findAllById(List<Integer> ids){
        try {
            List<Transaction>result = repository.findAllById(ids);
            return result;
        }catch (Exception e){
            throw e;
        }
    }
    public List<Transaction> findAllTransactions(){
        try {
            List<Transaction> result = repository.findAll();
            return result;
        }catch (Exception e){
            throw e;
        }
    }
    //update transaction...

    public Transaction updateTransaction(Integer studentId, TransactionDTO transactionDTO) throws DaoException {
        try{
            Optional<Student> studentResult = studentRepository.findById(studentId);
            if(studentResult.isPresent()){
                Optional<Transaction> result = repository.findById(transactionDTO.getTransactionId());
                if(result.isPresent()){
                    Transaction res = result.get();
                    if(res.getStudent().getStudentId() == studentResult.get().getStudentId()){
                        if(transactionDTO.getReturnDate() != null) res.setReturnDate(transactionDTO.getReturnDate());
                        if(transactionDTO.getReturned() != null){
                            if(transactionDTO.getReturned() == true) res.setReturned(true);
                            else res.setReturned(false);
                        }else res.setReturned(res.getReturned());
                        repository.save(res);
                        return res;
                    }
                    else throw new Exception("you can only update your own transaction!");
                }
                else throw new Exception("Transaction Id not valid!");
            }else throw new Exception("Student Id not valid!");
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }
    //delete transaction....

    public Transaction deleteOneTransaction(Integer transactionId){
        // TODO: 7/17/2023
        return null;
    }

    public List<Transaction> deleteManyTransaction(List<Integer> transactionIds){
        // TODO: 7/17/2023
        return null;
    }
}
