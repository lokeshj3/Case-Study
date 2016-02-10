package com.helpdesk.repository;

import com.helpdesk.model.Ticket;
import com.helpdesk.serialization.TicketSerialization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by root on 9/2/16.
 */
public class TicketRepository {

    private static Map<Integer, Ticket> ticketMap;

    static {
        ticketMap = TicketSerialization.getTicketsFromFile();
    }

    public void addTicket(Ticket ticket){
        ticketMap.put(ticket.getId(), ticket);
    }

    public void updateTicket(int id, Ticket ticket){
        ticketMap.put(id, ticket);
    }

    public void deleteTicket(int id){
        ticketMap.remove(id);
    }

    public Map<Integer, Ticket> getTicketsMap(){
        return new ConcurrentHashMap<>(ticketMap);
    }
}
