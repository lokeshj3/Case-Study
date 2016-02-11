package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;

import java.io.IOException;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {

    public TicketService(){

    }

    public Ticket createTicket(String subject, String agent, Set tags)
            throws IOException, IncompleteDataException {

        if (subject == null){
            throw new IncompleteDataException("subject is required");
        }if (agent == null){
            throw new IncompleteDataException("agent is required");
        }



        ticket = new Ticket.TicketBuilder(subject, agent).withTags(tags).build();

        // writing in the file.
        repository.save(ticket);

        return ticket;

    }

    public void clearTickets() {

    }

    public Ticket updateTicket(Integer id, String newAgent, Set<String> newTag) throws NoUpdateException {

        boolean flag = false;
        if (newAgent != null && newAgent.length() >0){
            ticket.setAgent(newAgent);
            flag = true;
        }
        if (newTag != null){
            ticket.setTags(newTag);
            flag = true;
        }

        ticket = this.getTicket(id);

        if (!flag){
            throw new NoUpdateException("Nothing to update");
        }
        return ticket;

    }

    public Ticket getTicket(Integer id) {
        return ticket;

    }

    //properties
    TicketRepository repository;
    Ticket ticket ;

}
