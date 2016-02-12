package com.inin.dao;

import com.inin.exception.TicketNotFoundException;
import com.inin.factory.TicketFactory;
import com.inin.logger.TLogger;
import com.inin.model.Ticket;
import com.inin.util.TicketUtil;
import sun.management.resources.agent;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by root on 11/2/16.
 */
public class MapTicketDAO implements TicketServiceDAO,TicketReportDAO {
    MapRepository ticketMap = new MapRepository();

    /**
     * creates Ticket
     * @param subject
     * @param agent
     * @param tags
     * @return int
     * @throws IllegalArgumentException
     */
    @Override
    public int create(String subject,String agent, Set<String> tags) throws IllegalArgumentException{
        if(!TicketUtil.isValidString(subject) || !TicketUtil.isValidString(agent))
            throw new IllegalArgumentException();
        tags = tags != null ? new HashSet<>(tags) : new HashSet<>();

        Ticket ticket   = TicketFactory.newTicketInstance(subject, agent, tags);
        ticketMap.getTicketMap().put(ticket.getId(),ticket);
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

        Ticket ticket = ticketMap.getTicketMap().get(id);
        ticket.setAgent(agent);
        if (TicketUtil.isValidCollection(tags)){
            ticket.setTags(tags);
        }

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
        return ticketMap.getTicketMap().remove(id) != null ? true : false;
    }


    /**
     * Return the total ticket count
     * @return
     */
    @Override
    public int count() {
        return ticketMap.getTicketMap().size();
    }

    /**
     * Check ticket existence
     * @param id
     * @return boolean
     */
    private boolean isExist(int id){
        return ticketMap.getTicketMap().containsKey(id);
    }


    /**
     * Returns Ticket By Id
     * @param id
     * @return int
     */
    @Override
    public Ticket findById(int id) {
        if(!isExist(id))
            throw new TicketNotFoundException("No Ticket found with id"+id);
        return ticketMap.getTicketMap().get(id);
    }

    /**
     * Returns List of Tickets
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findAll() {
        if(ticketMap.getTicketMap().size() > 0)
            return ticketMap.getTicketMap().values()
                    .stream()
                    .sorted((ticket1,ticket2)->ticket1.getCreated().compareTo(ticket2.getCreated()))
                    .collect(Collectors.toList());

        return new ArrayList<>();
    }

    /**
     * Return the list of Ticket specified by agent
     * @param agent
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findAllByAgent(String agent) {
        String tempAgent = agent.toLowerCase();
        if (ticketMap.getTicketMap().size() > 0)
            return ticketMap.getTicketMap().values()
                    .stream()
                    .filter(ticket -> ticket.getAgent().toLowerCase().equals(tempAgent))
                    .sorted((ticket1,ticket2)->ticket1.getCreated().compareTo(ticket2.getCreated()))
                    .collect(Collectors.toList());

        return new ArrayList<>();
    }

    /**
     * Return the list of Ticket specified by Tag
     * @param tag
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findAllByTag(String tag) {
        String tempTag = tag.toLowerCase();
        if (ticketMap.getTicketMap().size() > 0)
            return ticketMap.getTicketMap().values()
                    .stream()
                    .filter(ticket -> ticket.getTags().contains(tempTag))
                    .sorted((ticket1,ticket2)->ticket1.getCreated().compareTo(ticket2.getCreated()))
                    .collect(Collectors.toList());

        return new ArrayList<>();
    }


    /**
     * Return oldest Ticket in the system
     * @return Ticket
     */
    @Override
    public Ticket findOldestRecord() {
        if (ticketMap.getTicketMap().isEmpty())
            throw new TicketNotFoundException("No Ticket found.");

        return ticketMap.getTicketMap().values()
                .stream()
                .sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated()))
                .findFirst()
                .get();
    }



    /**
     * Returns Tickets Older than the Date entered
     * @param date
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> findTicketsFromDate(LocalDateTime date){
        if (ticketMap.getTicketMap().isEmpty())
            return new ArrayList<>();
        return ticketMap.getTicketMap().values()
                .stream()
                .filter(ticket -> date.compareTo(ticket.getCreated()) >=0 )
                .collect(Collectors.toList());
    }


    /**
     * Return the agent's ticket count
     * @return
     */
    @Override
    public Map<String ,Long> ticketsCountByAgent(){
        if(ticketMap.getTicketMap().size() > 0)
        return ticketMap.getTicketMap().values()
                .stream()
                .collect(Collectors.groupingBy(Ticket::getAgent,Collectors.counting()));

        return new HashMap<>();
    }


    /**
     * Return the tag's Ticket count
     * @return
     */
    @Override
    public Map<String, Long> ticketsCountByTag() {
        Map<String,Long> ticketByTag = new HashMap<>();
        if(ticketByTag.size() > 0)
            ticketMap.getTicketMap().values()
                    .stream()
                    .forEach(ticket -> {
                        ticket.getTags().forEach( tag -> {
                            if(ticketByTag.containsKey(tag))
                                ticketByTag.put(tag,ticketByTag.get(tag)+1L);
                            else {
                                List<Ticket> list = new ArrayList<>();
                                list.add(ticket);
                                ticketByTag.put(tag, 1L);
                            }
                        });
                    });

        return ticketByTag;
    }

}
