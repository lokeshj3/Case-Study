package com.helpdesk.exception;

/**
 * Created by root on 10/2/16.
 */
public class TicketNotFoundException extends Exception {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
