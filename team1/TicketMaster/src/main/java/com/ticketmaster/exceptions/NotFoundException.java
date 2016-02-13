package com.ticketmaster.exceptions;

/**
 * Created by Virendra on 11/2/16.
 */
public class NotFoundException extends Exception {
    public NotFoundException(final String message){
        super(message);
    }
}
