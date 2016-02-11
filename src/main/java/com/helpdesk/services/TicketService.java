package com.helpdesk.services;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketFailure;
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
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet) throws InvalidParamsException, TicketFailure {
        writeLog(Level.INFO, "create service start");

        Ticket ticket = new Ticket.Builder().withId(++max_id).withAgent(agentName).withSubject(subject).withTags(tagSet).build();

        if(objRepository.addTicket(ticket)){
            writeLog(Level.INFO, "Ticket create successful");
            return ticket;
        }
        else{
            writeLog(Level.INFO, "Ticket create failed");
            throw new TicketFailure("Somthing went worng! Ticket not created");
        }
    }

    public boolean isTicketExist(int id){
        //code to check given ticket id is present on not in the system.
        return true;
    }
/*
    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action) {//add more required parameters
        //code to update the ticket into file as well as map
        //return ticket;
        return null;
        //handle throw exception here InvalidParameterException
    }

    public boolean delete(int id) {
       // code to delete a ticket
        return true;
    }

    public Ticket ticketDetails(int id) {
       // code to return ticket details by id
        // return ticket;
        return null;
        //handle throw exception here
    }

    public List<Ticket> tickets(){
       //code to return all tickets
         return new HashMap<>();
        // no data the returns empty map
    }*/
}
