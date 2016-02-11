package com.helpdesk.services;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.logger.TicketLogger;
import com.helpdesk.model.Ticket;
import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.logging.Level;

//only CRUD
public class TicketService {
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet) throws InvalidParamsException {
        TicketLogger.writeLog(Level.INFO, "create service start");

        if(subject == null || subject.trim().isEmpty() || agentName == null || agentName.trim().isEmpty() || tagSet == null) {
            throw new InvalidParamsException("Please give proper input!");
        }

           
        TicketLogger.writeLog(Level.INFO, " inside TicketService -- ");
        return null;
    }

    public boolean isTicketExist(int id){
        //code to check given ticket id is present on not in the system.
        return true;
    }

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
    }
}
