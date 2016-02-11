package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.CustomLogger;

import java.io.IOException;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {

    public TicketService(){

    }

    public Ticket createTicket(String subject, String agent, Set tags)
            throws IOException, IncompleteDataException, ClassNotFoundException, NotFoundException {

        CustomLogger.init(classz).info("start ticket creation with subject: "+subject+" agent: "+agent);

        if (subject == null){
            throw new IncompleteDataException("subject is required");
        }if (agent == null){
            throw new IncompleteDataException("agent is required");
        }


        ticket = new Ticket.TicketBuilder(subject, agent).withTags(tags).build();

        repository.saveTicket(ticket);
        CustomLogger.init(classz).info("ticket created. Id: "+ticket.getId());

        return ticket;

    }

    public void clearTickets() {

    }

    public Ticket updateTicket(Integer id, String newAgent, Set<String> newTag)
            throws NoUpdateException, NotFoundException {

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

    public Ticket getTicket(Integer id) throws NotFoundException{
        return repository.getTicket(id);
    }

    //properties
    TicketRepository repository;
    Ticket ticket ;
    Class classz = TicketService.class;

}
