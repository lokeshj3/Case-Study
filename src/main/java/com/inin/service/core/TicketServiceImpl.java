package com.inin.service.core;

import com.inin.exception.TicketNotFoundException;
import com.inin.factory.TicketFactory;
import com.inin.model.Ticket;
import com.inin.util.TicketUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 8/2/16.
 */
public class TicketServiceImpl implements TicketService {
    private final Map<Integer, Ticket> ticketMap = new HashMap<>();

    /**
     * Function to Create New Ticket
     * @param subject
     * @param agent
     * @param tags
     * @return
     * @throws IllegalArgumentException
     */
    public int create(String subject, String agent, Set<String> tags) throws IllegalArgumentException{
        if(!TicketUtil.isValidString(subject) || !TicketUtil.isValidString(agent))
            throw new IllegalArgumentException();
        Ticket ticket = TicketFactory.newTicketInstance(subject, agent, tags);
        ticketMap.put(ticket.getId(),ticket );
        return ticket.getId();
    }

    /**
     * Function to Update a single ticket
     * @param id
     * @param agent
     * @param tags
     * @return
     * @throws IllegalArgumentException
     * @throws TicketNotFoundException
     */
    public Ticket update(int id, String agent, Set<String> tags) throws IllegalArgumentException,TicketNotFoundException{
        if(!TicketUtil.isValidString(agent))
            throw new IllegalArgumentException();


        Ticket ticket = ticketMap.get(id);
        if (ticket == null)
            throw new TicketNotFoundException("Not Found");

        if (TicketUtil.isValidString(agent))
            ticket.setAgent(agent);
        if (TicketUtil.isValidCollection(tags)) {
            ticket.setTags(tags);
        }
        return TicketFactory.newTicketInstance(ticket);
    }

    /**
     * Function to remove a Ticket
     * @param id
     */
    public boolean delete(int id) {
        return ticketMap.remove(id) == null ? false : true;
    }

    /**
     * Function to return a Ticket with Id
     * @param id
     * @return Ticket
     * @throws TicketNotFoundException
     */
    public Ticket ticket(int id) throws TicketNotFoundException{
        Ticket ticket = ticketMap.get(id);
        if(ticket == null)
            throw new TicketNotFoundException("No Ticket found with this id: "+id);

        return TicketFactory.newTicketInstance(ticket);
    }

    /**
     * Function to return Ticket list
     * @return
     */
    public List<Ticket> tickets() {
        return Collections.unmodifiableList(ticketMap.values()
                .stream()
                .sorted((ticket1,ticket2) -> ticket1.getModified().compareTo(ticket2.getModified()))
                .collect(Collectors.toList()));
    }

    /**
     * Get Tickets w.r.t Tag passed
     * @param tag
     * @return
     *//*
    public List<Ticket> ticketsByTags(String tag){
        return Collections.unmodifiableList(ticketMap.values()
        .stream()
        .filter(ticket -> ticket.getTags().contains(tag))
        .sorted((Ticket t1, Ticket t2) -> t2.getModified().compareTo(t1.getModified()))
        .collect(Collectors.toList()));
    }

    *//**
     * Get Tickets w.r.t Agent passed
     * @param agent
     * @return
     *//*
    public List<Ticket> ticketsByAgent(String agent){
        return Collections.unmodifiableList(ticketMap.values()
        .stream()
        .filter(ticket -> ticket.getAgent().contains(agent))
        .sorted((Ticket t1, Ticket t2) -> t2.getModified().compareTo(t1.getModified()))
        .collect(Collectors.toList()));
    }*/

}
