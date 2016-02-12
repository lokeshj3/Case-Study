package com.helpdesk.repository;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.serialization.TicketSerialization;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.helpdesk.logger.TicketLogger.writeLog;

/**
 * Created by root on 9/2/16.
 */
public class TicketRepository {

    private static Map<Integer, Ticket> ticketMap;
    private TicketSerialization ticketSerialization;

    public TicketRepository() {
        ticketSerialization = new TicketSerialization("src/main/resources/files/", "tickets.ser");
        ticketMap = ticketSerialization.getTicketsFromFile();
    }

    /**
     * add ticket to repository
     * @param ticket */
    public boolean addTicket(Ticket ticket){
        writeLog(Level.INFO, "add ticket repository start");
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
    public boolean updateTicket(int id, Ticket ticket){
        Ticket tempTicket = ticketMap.get(id);
        ticketMap.put(id, ticket);
        if(ticketSerialization.saveSingelTicket(ticket)){
           return true;
        }
        else {
            ticketMap.put(id, tempTicket);
            return false;
        }
    }

    /**
     * delete ticket from repository
     * @param id */
    public boolean deleteTicket(int id) {
        if (ticketMap.containsKey(id)){
            ticketMap.remove(id);
            ticketSerialization.saveTicketsInFile(ticketMap, false);
            return true;
        }
        else
            return false;
    }

    /**
     * return all tickets sorted by updated time.
     * */
    public List<Ticket> getAllTickets(){
        writeLog(Level.INFO, "get all tickets repository start");
       return ticketMap.values().stream().collect(Collectors.toList());
    }

    /**
     * add all the tickets in map
     * @param tickets
     * */
    public void addAll(Map<Integer, Ticket> tickets){
        writeLog(Level.INFO, "add all ticket repository start");
        ticketMap.putAll(tickets);
    }

    /**
     * get the max id in repository from file
     * @return maxId*/
    public int getMaxId(){
        writeLog(Level.INFO, "get max id repository start");
        int maxId = 0;
        if(ticketMap.size() > 0)
            maxId = Collections.max(ticketMap.keySet());

        writeLog(Level.INFO, "enc get max id repository max id :"+maxId);
        return maxId;
    }

    /**
     * get ticket from repository by id
     * @return  ticket
     * */
    public Ticket getTicket(int id) throws TicketExceptions {
        writeLog(Level.INFO, "get Ticket repository start");
        if(ticketMap.containsKey(id)){
            return ticketMap.get(id);
        }
        else {
            writeLog(Level.INFO, "ticket not found in repository id: "+id);
            throw new TicketExceptions("Ticket not found");
        }
    }
}
