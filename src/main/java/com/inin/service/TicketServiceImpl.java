package com.inin.service;

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
    private final static Map<Integer, Ticket> ticketMap = new HashMap<>();

    public int create(String subject, String agent, Set<String> tags) throws IllegalArgumentException{
        if(!TicketUtil.isValidString(subject) || !TicketUtil.isValidString(agent))
            throw new IllegalArgumentException();
        Ticket ticket = TicketFactory.newInstance(subject, agent, tags);
        ticketMap.put(ticket.getId(),ticket );
        return ticket.getId();
    }

    public Ticket update(int id, String agent, Set<String> tags) {
        return null;
    }

    /**
     * Remove Ticket from ticketMap
     * @param id
     */
    public boolean delete(int id) {
        return ticketMap.remove(id) == null ? false : true;
    }

    /**
     * Return Ticket associated with this Id
     * @param id
     * @return Ticket
     * @throws TicketNotFoundException
     */
    public Ticket ticket(int id) throws TicketNotFoundException{
        Ticket ticket = ticketMap.get(id);
        if(ticket == null)
            throw new TicketNotFoundException("No Ticket found with this id: "+id);

        return TicketFactory.newInstance(ticket);
    }

    /**
     * Return the Ticket list
     * @return
     */
    public List<Ticket> tickets() {
        return Collections.unmodifiableList(ticketMap.values()
                .stream()
                .sorted((ticket1,ticket2) -> ticket1.getModified().compareTo(ticket2.getModified()))
                .collect(Collectors.toList()));
    }
}
