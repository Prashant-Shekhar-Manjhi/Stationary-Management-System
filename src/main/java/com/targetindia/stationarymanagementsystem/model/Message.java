package com.targetindia.stationarymanagementsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class Message {
    private String message;
    private Date date = new Date();

    public Message(String message){
        this.message = message;
    }
}
