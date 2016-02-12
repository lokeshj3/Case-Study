package com.helpdesk.exception;

/**
 * Created by root on 11/2/16.
 */
public class InvalidParamsException extends TicketExceptions {
    private String message = null;
    public InvalidParamsException(String message){
        super(message);
        this.message = message;
    }
}
