package com.helpdesk.exception;

/**
 * Created by root on 10/2/16.
 */
public class DuplicateTicketIdException extends Exception {
    /**DA:
     * For exception does we really need to create class?
     * you can broadly create Tickets Exception and make the subclasses further */
    public DuplicateTicketIdException(String message) {
        super(message);
    }
}
