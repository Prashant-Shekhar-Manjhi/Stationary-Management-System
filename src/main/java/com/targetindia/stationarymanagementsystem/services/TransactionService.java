package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.repository.StationaryItemRepository;
import com.targetindia.stationarymanagementsystem.repository.StudentRepository;
import com.targetindia.stationarymanagementsystem.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {


    @Autowired
    private TransactionRepository repository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StationaryItemRepository stationaryItemRepository;


    //create transaction....
    public Transaction createOneTransaction(Transaction transaction) throws DaoException {
        try {
            return repository.save(transaction);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    //read transaction....
    public Transaction findOneTransaction(Integer transactionId) throws ItemNotFoundException, DaoException {
        try {
            Optional<Transaction> result = repository.findById(transactionId);
            if (result.isPresent()) return result.get();
            else throw new ItemNotFoundException("Transaction not found with id " + transactionId);
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public List<Transaction> findAllTransactionByStudentId(Integer studentId) throws DaoException, ItemNotFoundException {
        try {
            Optional<Student> result = studentRepository.findById(studentId);
            if (result.isPresent()) {
                Student student = result.get();
                List<Transaction> list = repository.findAllByStudent(student);
                return list;
            } else throw new ItemNotFoundException("Student not found with id " + studentId);
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public List<Transaction> findAllTransactionByItemId(Integer itemId) throws DaoException, ItemNotFoundException {
        try {
            Optional<StationaryItem> result = stationaryItemRepository.findById(itemId);
            if (result.isPresent()) {
                StationaryItem stationaryItem = result.get();
                List<Transaction> list = repository.findAllByStationaryItem(stationaryItem);
                return list;
            } else throw new ItemNotFoundException("Item not found with id " + itemId);
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public List<Transaction> findAllTransactions() throws DaoException {
        try {
            List<Transaction> result = repository.findAll();
            return result;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }
    //update transaction...

    public Transaction updateTransaction(Transaction transaction) throws DaoException, ItemNotFoundException {
        try {
            Optional<Transaction> result = repository.findById(transaction.getTransactionId());
            if (result.isPresent()) {
                Transaction res = result.get();
                if (transaction.getReturnDate() != null) res.setReturnDate(transaction.getReturnDate());
                if (transaction.getReturned() != null) {
                    StationaryItem item = stationaryItemRepository.findById(res.getStationaryItem().getItemId()).get();
                    if (transaction.getReturned() == true) {
                        res.setReturned(true);
                        item.setQuantity(res.getStationaryItem().getQuantity() + res.getWithdrawnQuantity());
                    } else {
                        res.setReturned(false);
                        item.setQuantity(res.getStationaryItem().getQuantity() - res.getWithdrawnQuantity());
                    }
                    stationaryItemRepository.save(item);
                } else res.setReturned(res.getReturned());
                res = repository.save(res);
                return res;
            } else
                throw new ItemNotFoundException("Transaction not available with id " + transaction.getTransactionId());
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }
    //delete transaction....
}
