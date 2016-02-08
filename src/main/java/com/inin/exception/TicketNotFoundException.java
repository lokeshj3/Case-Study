package com.inin.exception;

/**
 * Created by root on 8/2/16.
 */
public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String message){
        super(message);
    }
}
