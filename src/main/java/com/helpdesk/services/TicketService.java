package com.helpdesk.services;

import com.helpdesk.components.Util;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

import java.util.*;
import java.util.logging.Level;

import static com.helpdesk.logger.TicketLogger.*;

/**
 * Ticket service class
 * */
public class TicketService {
    private int max_id = 0;
    private TicketRepository objRepository;

    public TicketService(){
        objRepository = new TicketRepository();
        max_id = objRepository.getMaxId();
    }

   /**
    * Ticket create service
    * */
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet) throws TicketExceptions {
        writeLog(Level.INFO, "create service start");

        Ticket ticket = new Ticket.Builder().withId(++max_id).withAgent(agentName).withSubject(subject).withTags(tagSet).build();

        if(objRepository.addTicket(ticket)){
            writeLog(Level.INFO, "Ticket create successful");
            return ticket;
        }
        else{
            writeLog(Level.WARNING, "Ticket create failed");
            throw new TicketExceptions("Something went wrong! Ticket not created");
        }
    }

    /**
     * ticket update service */
    public Ticket update(int id, String agentName, Set<String> tagSet, String action) throws TicketExceptions {
        writeLog(Level.INFO, "Update ticket service start");
        Ticket ticket = objRepository.getTicket(id);

        if(Util.isValidString(agentName))
            ticket.updateAgent(agentName);

        if (action.trim().equals("a")) {
            ticket.addTags(tagSet);
        }
        else if (action.trim().equals("r")) {
            ticket.removeTags(tagSet);
        }

        if(objRepository.updateTicket(id, ticket)){
            writeLog(Level.INFO, "Ticket updated successful");
            return ticket;
        }
        else {
            writeLog(Level.INFO, "Ticket update failed");
            throw new TicketExceptions("Error!!! ticket is not updated");
        }

    }

    public Ticket ticketDetails(int id) throws TicketExceptions{
        writeLog(Level.INFO, "ticket details service start");
        if(id > max_id)
            throw new InvalidParamsException("Invalid ticket id!");

        return objRepository.getTicket(id);
    }

    public boolean delete(int id) throws TicketExceptions {
        writeLog(Level.INFO, "Update ticket service start");
        if(id > max_id)
            throw new InvalidParamsException("Invalid ticket id!");

        return objRepository.deleteTicket(id);
    }

    public List<Ticket> getAllTickets(){
        writeLog(Level.INFO, "get all tickets service start");
        return objRepository.getAllTickets();
    }

    public Boolean deleteAllTickets(){
        writeLog(Level.INFO, "delete all tickets service start");
        return objRepository.removeAllTickets();
    }
}
