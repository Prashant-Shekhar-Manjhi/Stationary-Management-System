package com.targetindia.stationarymanagementsystem.exception;

public class ItemNotFoundException extends Exception{
    public ItemNotFoundException() {
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
