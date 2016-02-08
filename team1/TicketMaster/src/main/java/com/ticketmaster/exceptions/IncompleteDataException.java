package com.ticketmaster.exceptions;

/**
 * Created by root on 8/2/16.
 */
public class IncompleteDataException extends Exception{

    public IncompleteDataException(final String message){
        super(message);
    }
}
