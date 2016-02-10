package com.helpdesk.exceptions;

import com.helpdesk.tickets.TicketModel;

/**
 * Created by root on 8/2/16.
 */
public class InvalidParamsException extends Exception {
    public InvalidParamsException(String message) {
        super(message);
        new TicketModel.Builder(1, "", "").build();
    }
}
