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

    public Ticket update(int id, @NotNull String agentName, Set<String> tags, String action) {


        // incomplete --- need to handle serialization part only,  waiting for deepak's service
        // 10 & logic will be changed

        //Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();

        boolean modifiedFlag = false;
        Ticket ticket = masterTicketsData.get(id);
        if (!agentName.isEmpty()){
            ticket.setAgentName(agentName);
            modifiedFlag = true;
        }

        if(action.equals("A")){  // Adding new  tags
            tags.addAll(ticket.getTags());
            ticket.setTags(tags);
            modifiedFlag = true;
        }
        else if(action.equals("R")){  // remove tags
            HashSet<String> oldTags = new HashSet<>();
            oldTags.addAll(ticket.getTags());
            ticket.getTags().forEach((tag)->{
                if(tags.contains(tag)){
                    oldTags.remove(tag);
                }
            });
            ticket.setTags(oldTags);
            modifiedFlag = true;
        }

       /* if(modifiedFlag)
            TicketSerialization.serialize(masterTicketsData, false);*/
        return ticket;
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
         return new ArrayList<>();
        // no data the returns empty map
    }
}
