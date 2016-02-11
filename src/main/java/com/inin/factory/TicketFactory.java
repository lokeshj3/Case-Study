package com.inin.factory;

import com.inin.model.Ticket;
import com.inin.service.core.TicketService;
import com.inin.service.core.TicketServiceImpl;

import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketFactory {
    public static int counter;

    /**
     * Create new Ticket with subject, name and tags
     * @param subject
     * @param agent
     * @param tags
     * @return Ticket
     */
    public static Ticket newInstance(String subject, String agent, Set<String> tags){
        int id = getTicketId();
        return new Ticket(id, subject, agent, tags);
    }

    /**
     * Create new copy of passed Ticket
     * @param ticket
     * @return Ticket
     */
    public static Ticket newInstance(Ticket ticket){
        return new Ticket(ticket);
    }

    public static TicketService newTicketServiceInstance(){  return new TicketServiceImpl(); }

    private static int getTicketId(){
        return ++counter;
    }
}
