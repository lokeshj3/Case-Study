package com.helpdesk.exception;

import com.helpdesk.ticket.Ticket;

/**
 * Created by root on 8/2/16.
 */
public class InvalidParamsException extends Exception {
    public InvalidParamsException(String message) {
        super(message);
        new Ticket.Builder(1, "", "").build();
    }
}
