package com.helpdesk.repository;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.serialization.TicketSerialization;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.helpdesk.logger.TicketLogger.writeLog;

/**
 * Created by root on 9/2/16.
 */
public class TicketRepository {

    private Map<Integer, Ticket> ticketMap;
    private TicketSerialization ticketSerialization;

    public TicketRepository() {
        ticketSerialization = new TicketSerialization("src/main/resources/files/", "tickets.ser");
        ticketMap = ticketSerialization.getTicketsFromFile();
    }

    /**
     * add ticket to repository
     * @param ticket */
    public boolean addTicket(Ticket ticket){
        if(ticketSerialization.saveSingelTicket(ticket)){
            ticketMap.put(ticket.getId(), ticket);
            writeLog(Level.INFO, "Ticket save in file");
            return true;
        }
        else{
            writeLog(Level.INFO, "Ticket failed to save in file");
            return false;
        }
    }


    /**
     * update ticket to repository
     * @param id, ticket */
    public void updateTicket(int id, Ticket ticket){
        ticketMap.put(id, ticket);
    }

    /**
     * delete ticket from repository
     * @param id */
    public void deleteTicket(int id){
        ticketMap.remove(id);
    }

    /**
     * return all tickets sorted by updated time.
     * */
    public List<Ticket> getAllTickets(){
        return (List<Ticket>) ticketMap.values().stream().sorted((Ticket t1, Ticket t2)-> t2.getUpdated().compareTo(t1.getUpdated()));
    }

    /**
     * add all the tickets in map
     * @param tickets
     * */
    public void addAll(Map<Integer, Ticket> tickets){
        ticketMap.putAll(tickets);
    }

    public int getMaxId(){
        int maxId = 0;
        if(ticketMap.size() > 0)
            maxId = Collections.max(ticketMap.keySet());

        return maxId;
    }

    public Ticket getTicket(int id) throws TicketExceptions {
        if(ticketMap.containsKey(id)){
            return ticketMap.get(id);
        }
        else {
            throw new TicketExceptions("Ticket not found");
        }
    }

}
