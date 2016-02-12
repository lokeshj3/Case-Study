package com.helpdesk.exception;

/**
 * Created by root on 11/2/16.
 */
public class TicketExceptions extends Exception {
    private String message = null;
    public TicketExceptions(String message){
        super(message);
        this.message = message;
    }
}
