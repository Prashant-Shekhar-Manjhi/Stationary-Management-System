package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.TransactionDTO;

public class TransactionValidator {
    public static Boolean isTransactionValid(TransactionDTO transactionDTO){
        if(transactionDTO.getStationaryItemId() == null) return false;
        if(transactionDTO.getReturned() == null) return false;
        if(transactionDTO.getReturnDate() == null) return false;
        if(transactionDTO.getWithdrawnQuantity() == null) return false;
        return true;
    }
}
