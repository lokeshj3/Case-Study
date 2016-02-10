package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.sun.istack.internal.NotNull;

import java.util.*;

//only CRUD
public class TicketService {
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet){//add more required parameters
        // code to create ticket & adding into a file
        //handle throw exception here IncompleteDataException
        //return ticket;
    }

    public boolean isTicketExist(int id){
        //code to check given ticket id is present on not in the system.
        //return true;
    }

    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action) {//add more required parameters
        //code to update the ticket into file as well as map
        //handle throw exception here InvalidParameterException
        //return ticket;
    }

    public boolean delete(int id) {
       // code to delete a ticket
        //return true;
    }

    public Ticket ticketDetails(int id) {
       // code to return ticket details by id
        //handle throw exception here
       // return ticket;
    }

    public Map<Integer, Ticket> tickets(){
       //code to return all tickets
        // return new HashMap<>();
    }
}
