package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.TransactionRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionValidatorTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    private TransactionValidator transactionValidator = new TransactionValidator();
    @Test
    public void isTransactionValidValidTransactionRequestDTOTest() throws ParseException {
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(1);
        transactionDTO.setReturned(false);
        transactionDTO.setReturnDate(sdf.parse("2023-08-02"));
        transactionDTO.setWithdrawnQuantity(5);

        boolean result = transactionValidator.isTransactionValid(transactionDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void isTransactionValidInvalidTransactionRequestDTOTest() throws ParseException {
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setStationaryItemId(null);
        transactionDTO.setReturned(null);
        transactionDTO.setReturnDate(sdf.parse("2001-03-29"));
        transactionDTO.setWithdrawnQuantity(null);

        boolean result = transactionValidator.isTransactionValid(transactionDTO);
        Assertions.assertFalse(result);
    }
}
