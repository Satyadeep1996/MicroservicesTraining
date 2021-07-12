package com.zensar.olxadvertises.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAdvertisementException extends RuntimeException{

    private String message;

    public InvalidAdvertisementException(){
        this.message = "";
    }

    public InvalidAdvertisementException(String message){
        this.message = message;
    }

    public String toString(){
        return "Invalid Advertisement id: " + this.message;
    }

}
