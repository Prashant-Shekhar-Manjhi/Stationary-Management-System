package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.TransactionRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {
    public Boolean isTransactionValid(TransactionRequestDTO transactionDTO){
        if(transactionDTO.getStationaryItemId() == null) return false;
        if(transactionDTO.getReturned() == null) return false;
        if(transactionDTO.getReturnDate() == null) return false;
        if(transactionDTO.getWithdrawnQuantity() == null) return false;
        return true;
    }
}
