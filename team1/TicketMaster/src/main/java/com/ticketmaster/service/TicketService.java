package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.models.Ticket;

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

        ticket = new Ticket.TicketBuilder().withSubject(subject).withAgent(agent).withTags(tags).build();


        ticket.save();

        return ticket;

    }

    public void clearTickets() {

    }

    Ticket ticket ;
}
