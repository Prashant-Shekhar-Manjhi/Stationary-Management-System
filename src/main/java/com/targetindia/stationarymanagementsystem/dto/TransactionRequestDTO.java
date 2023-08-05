package com.targetindia.stationarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionRequestDTO {
    private Integer transactionId;
    private Integer studentId;
    private Integer stationaryItemId;
    private Integer withdrawnQuantity;
    private Date returnDate;
    private Boolean returned;
}
