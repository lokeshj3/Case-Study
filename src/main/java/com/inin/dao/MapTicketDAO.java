package com.inin.dao;

import com.inin.constant.TicketAttribute;
import com.inin.exception.TicketNotFoundException;
import com.inin.model.Ticket;
import com.inin.util.TicketUtil;

import java.time.LocalDateTime;
import java.util.*;
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
     * @param id , agent, tags
     * @return Ticket
     */
    @Override
    public Ticket update(int id, String agent, Set<String> tags) throws TicketNotFoundException{
        if(!isExist(id))
            throw new TicketNotFoundException("No Ticket found with id"+id);

        Ticket ticket = ticketMap.get(id);
        ticket.setAgent(agent);
        ticket.setTags(tags);
        return ticket;
    }

    /**
     * Remove Ticket from map
     * @param id
     * @return boolean
     */
    @Override
    public boolean delete(int id) {
        if(!isExist(id))
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


    /**
     * Function to get Ticket By Id
     * @param id
     * @return int
     */
    @Override
    public Ticket findById(int id) {
        if(!isExist(id))
            throw new TicketNotFoundException("No Ticket found with id"+id);
        return ticketMap.get(id);
    }

    /**
     * Function to return List of Tickets
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findAll() {
        if(ticketMap.size() > 0)
            return ticketMap.values()
                    .stream()
                    .sorted((ticket1,ticket2)->ticket1.getCreated().compareTo(ticket2.getCreated()))
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    /**
     * f
     * @param agent
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findAllByAgent(String agent) {
        if (ticketMap.size() > 0)
            return ticketMap.values()
                .stream()
                .filter(ticket -> ticket.getAgent().toLowerCase().equals(agent.toLowerCase()))
                .collect(Collectors.toList());

        return new ArrayList<>();

    }

    /**
     * Function to fetch all Tickets by Tag
     * @param tag
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findAllByTag(String tag) {
        String tempTag = tag.toLowerCase();
        if (ticketMap.size() > 0)
            return ticketMap.values()
                .stream()
                .filter(ticket -> ticket.getTags().contains(tempTag))
                .collect(Collectors.toList());

        return new ArrayList<>();
    }

    /**
     * Function to fetch the oldest Ticket
     * @return Ticket
     */
    @Override
    public Ticket findOldestRecord() {
        if (ticketMap.isEmpty())
            throw new TicketNotFoundException("No Ticket found.");

        return ticketMap.values()
                .stream()
                .sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated()))
                .findFirst()
                .get();
    }

    /**
     * Function to fetch Tickets Older than the Date entered
     * @param date
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findTicketsFromDate(LocalDateTime date){
        if (ticketMap.isEmpty())
            throw new TicketNotFoundException("No Ticket found.");

        return ticketMap.values()
                .stream()
                .filter(ticket -> date.compareTo(ticket.getCreated()) >=0 )
                .collect(Collectors.toList());
    }

}
