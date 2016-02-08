package com.inin.factory;

import com.inin.model.Ticket;

import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketFactory {
    public static int counter;
    public static Ticket newInstance(String subject, String name, Set tags){
        int ticketId = getTicketId();
        return new Ticket(ticketId, subject, name, tags);
    }

    public static Ticket newInstance(Ticket ticket){
        return new Ticket(ticket);
    }
    public static int getTicketId(){
        return ++counter;
    }
}
