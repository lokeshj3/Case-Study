package com.inin.service.core;

import com.inin.dao.DAOFactory;
import com.inin.dao.TicketServiceDAO;
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


    private TicketServiceDAO ticketServiceDAO = DAOFactory.newTicketServiceDAOInstance();
    /**
     * Function to Create New Ticket
     * @param subject
     * @param agent
     * @param tags
     * @return TickcetId
     * @throws IllegalArgumentException
     */
    public int create(String subject, String agent, Set<String> tags) throws IllegalArgumentException{
        if(!TicketUtil.isValidString(subject) || !TicketUtil.isValidString(agent))
            throw new IllegalArgumentException();
        Ticket ticket   = TicketFactory.newInstance(subject, agent, tags);
        return ticketServiceDAO.create(ticket);
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
        if (!TicketUtil.isValidString(agent) || !TicketUtil.isValidCollection(tags))
            throw new IllegalArgumentException();

        return ticketServiceDAO.update(id, agent, tags);
    }

    /**
     * Function to remove a Ticket
     * @param id
     */
    public boolean delete(int id) {
        return ticketServiceDAO.delete(id);
    }

    /**
     * Function to return a Ticket with Id
     * @param id
     * @return Ticket
     * @throws TicketNotFoundException
     */
    public Ticket ticket(int id) throws TicketNotFoundException{ return ticketServiceDAO.findById(id);  }

    /**
     * Function to return Ticket list
     * @return
     */
    public List<Ticket> tickets() {
        return ticketServiceDAO.findAll();
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
