package com.helpdesk.services;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;
import com.sun.istack.internal.NotNull;

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
            writeLog(Level.INFO, "Ticket create failed");
            throw new TicketExceptions("Somthing went worng! Ticket not created");
        }
    }

    public Ticket update(int id, String agentName, Set<String> tagSet, String action) throws TicketExceptions {
        writeLog(Level.INFO, "Update ticket service start");
        Ticket ticket = objRepository.getTicket(id);

        ticket.updateAgent(agentName);

        if (action.equals("a")) {  // Adding new  tags
            tagSet.addAll(ticket.getTags());
            ticket.addTags(tagSet);
        }
        else if (action.equals("r")) {  // remove tags
            HashSet<String> oldTags = new HashSet<>();
            oldTags.addAll(ticket.getTags());
            ticket.getTags().forEach((tag) -> {
                if (tagSet.contains(tag)) {
                oldTags.remove(tag);
                }
            });
            ticket.addTags(oldTags);
        }

        if(objRepository.updateTicket(id, ticket)){
            writeLog(Level.INFO, "Ticket updated successful");
            return ticket;
        }
        else {
            writeLog(Level.INFO, "Ticket updation failed");
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
        objRepository.getTicket(id);

        if(objRepository.deleteTicket(id))
            return true;
        else
            return false;
    }

    public List<Ticket> getAlltickets(){
        writeLog(Level.INFO, "get all tickets service start");
        return objRepository.getAllTickets();
    }

}
