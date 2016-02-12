package com.helpdesk.services;

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
            writeLog(Level.INFO, "Ticket create failed");
            throw new TicketExceptions("Somthing went worng! Ticket not created");
        }
    }

/*    public boolean isTicketExist(int id){
        //code to check given ticket id is present on not in the system.
        return true;
    }


    public Ticket update(int id, @NotNull String agentName, Set<String> tags, String action) {


        // incomplete --- need to handle serialization part only,  waiting for deepak's service
        // 10 & logic will be changed

        //Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();

        boolean modifiedFlag = false;
        Ticket ticket = masterTicketsData.get(id);
        if (!agentName.isEmpty()) {
            ticket.setAgentName(agentName);
            modifiedFlag = true;
        }

        if (action.equals("A")) {  // Adding new  tags
            tags.addAll(ticket.getTags());
            ticket.setTags(tags);
            modifiedFlag = true;
        } else if (action.equals("R")) {  // remove tags
            HashSet<String> oldTags = new HashSet<>();
            oldTags.addAll(ticket.getTags());
            ticket.getTags().forEach((tag) -> {
                if (tags.contains(tag)) {
                    oldTags.remove(tag);
                }
            });
            ticket.setTags(oldTags);
            modifiedFlag = true;
        }

       *//* if(modifiedFlag)
            TicketSerialization.serialize(masterTicketsData, false);*//*
    }

    public boolean delete(int id) {
       // code to delete a ticket
        return true;
    }
*/

    public Ticket ticketDetails(int id) throws TicketExceptions{
        writeLog(Level.INFO, "ticket details service start");
        if(id > max_id)
            throw new InvalidParamsException("Invalid ticket id!");

        return objRepository.getTicket(id);
    }

    public List<Ticket> getAlltickets(){
        writeLog(Level.INFO, "get all tickets service start");
        return objRepository.getAllTickets();
    }

}
