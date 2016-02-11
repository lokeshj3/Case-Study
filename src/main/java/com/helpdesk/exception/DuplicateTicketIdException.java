package com.helpdesk.exception;

/**
 * Created by root on 10/2/16.
 */
public class DuplicateTicketIdException extends Exception {
    public DuplicateTicketIdException(String message) {
        super(message);
    }
}
