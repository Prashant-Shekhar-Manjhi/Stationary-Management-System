package com.targetindia.stationarymanagementsystem.dto;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Student;
import lombok.Data;
import java.util.Date;
@Data
public class TransactionResponseDTO {
    private Integer transactionId;
    private Student student;
    private StationaryItem stationaryItem;
    private Integer withdrawnQuantity;
    private Date transactionDate;
    private Date returnDate;
    private Boolean returned;
}
