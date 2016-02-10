package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.sun.istack.internal.NotNull;

import java.util.*;

//only CRUD
public class TicketService {
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet){//add more required parameters
        // code to create ticket & adding into a file
        //return ticket;
        //handle throw exception here IncompleteDataException
    }

    public boolean isTicketExist(int id){
        //code to check given ticket id is present on not in the system.
        //return true;
    }

    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action) {//add more required parameters
        //code to update the ticket into file as well as map
        //return ticket;
        //handle throw exception here InvalidParameterException
    }
gi
    public boolean delete(int id) {
       // code to delete a ticket
        //return true;
    }

    public Ticket ticketDetails(int id) {
       // code to return ticket details by id
        // return ticket;
        //handle throw exception here
    }

    public Map<Integer, Ticket> tickets(){
       //code to return all tickets
        // return new HashMap<>();
        // no data the returns empty map
    }
}
