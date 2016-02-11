package com.helpdesk.exception;

/**
 * Created by root on 11/2/16.
 */
public class TicketFailure extends Exception {
    private String message = null;
    public TicketFailure(String message){
        super(message);
        this.message = message;
    }
}
