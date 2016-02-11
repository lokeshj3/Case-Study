package com.inin.dao;

import com.inin.exception.TicketNotFoundException;
import com.inin.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by root on 11/2/16.
 */
public class MapTicketDAO implements TicketServiceDAO,TicketReportDAO {
    private final Map<Integer, Ticket> ticketMap  = new HashMap<>();

    /**
     * Create Ticket in map.
     * @param ticket
     * @return int
     */
    @Override
    public int create(Ticket ticket) throws IllegalArgumentException{
        if(ticket == null)
            throw new IllegalArgumentException("Ticket should not be null");
        ticketMap.put(ticket.getId(),ticket);
        return ticket.getId();
    }

    /**
     * Update Ticket
     * @param id
     * @param ticket
     * @return Ticket
     */
    @Override
    public Ticket update(int id, Ticket ticket) throws IllegalArgumentException,TicketNotFoundException{
        if(ticket == null)
            throw new IllegalArgumentException("Ticket should not be null");
        if(isExist(id))
            throw new TicketNotFoundException("No Ticket found with id"+id);
        Ticket ticket1 = ticketMap.get(id);
        ticket1.setAgent(ticket.getAgent());
        ticket1.setTags(ticket.getTags());
        return ticket1;
    }

    /**
     * Remove Ticket from map
     * @param id
     * @return boolean
     */
    @Override
    public boolean delete(int id) {
        if(isExist(id))
            throw new TicketNotFoundException("No Ticket found with id"+id);
        return ticketMap.remove(id) != null ? true : false;
    }


    /**
     * Return the total ticket count
     * @return
     */
    @Override
    public int count() {
        return ticketMap.size();
    }

    /**
     * Check ticket exist
     * @param id
     * @return boolean
     */
    private boolean isExist(int id){
        return ticketMap.containsKey(id);
    }


    @Override
    public Ticket find(int id) {
        if(isExist(id))
            throw new TicketNotFoundException("No Ticket found with id"+id);
        return ticketMap.get(id);
    }

    @Override
    public List<Ticket> findAll() {
        if(ticketMap.size() > 0)
            return ticketMap.values()
                    .stream()
                    .sorted((ticket1,ticket2)->ticket1.getCreated().compareTo(ticket2.getCreated()))
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public List<Ticket> findByAttribute() {
        return null;
    }

}
