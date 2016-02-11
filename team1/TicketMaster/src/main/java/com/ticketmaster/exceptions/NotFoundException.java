package com.ticketmaster.exceptions;

/**
 * Created by root on 11/2/16.
 */
public class NotFoundException extends Exception {
    public NotFoundException(final String message){
        super(message);
    }
}
