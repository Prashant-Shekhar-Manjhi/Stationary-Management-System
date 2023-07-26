package com.targetindia.stationarymanagementsystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequestDTO {
    private Integer transactionId;
    private Integer studentId;
    private Integer stationaryItemId;
    private Integer withdrawnQuantity;
    private Date returnDate;
    private Boolean returned;
}
