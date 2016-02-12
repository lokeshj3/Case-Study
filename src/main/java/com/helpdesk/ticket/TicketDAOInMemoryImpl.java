package com.helpdesk.ticket;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.helpdesk.exception.DuplicateTicketIdException;
import com.helpdesk.exception.TicketNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.stream.Collectors;

/**
 * Created by root on 9/2/16.
 */
public class TicketDAOInMemoryImpl implements TicketDAO {
    static Logger logger = LoggerFactory.getLogger(TicketDAOInMemoryImpl.class);

    TicketInMemoryStorage ticketInMemoryStorage = TicketInMemoryStorage.getInstance();

    public Ticket create(Ticket ticket) throws DuplicateTicketIdException {
        int ticketId = ticket.getId();
        if (!isExist(ticketId)) {
            ticketInMemoryStorage.writeData(ticketId, ticket);
            return new Ticket(ticket);
        }
        logger.error("Duplicate Ticket Id");
        throw new DuplicateTicketIdException("Duplicate Ticket Id");
    }


    public Ticket update(Ticket ticket) throws TicketNotFoundException {
        int ticketId = ticket.getId();
        if (isExist(ticketId)) {
            ticketInMemoryStorage.writeData(ticketId, ticket);
            return new Ticket(ticket);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    public boolean isExist(int ticketId) {
        return ticketInMemoryStorage.getTicketData().containsKey(ticketId);
    }

    public boolean delete(int ticketId) throws TicketNotFoundException {
        if (isExist(ticketId)) {
            ticketInMemoryStorage.deleteData(ticketId);
            return true;
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket not found");
    }

    public Ticket find(int ticketId) throws TicketNotFoundException {
        if (isExist(ticketId)) {
            return new Ticket(ticketInMemoryStorage.getTicketData().get(ticketId));
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    public List<Ticket> findAll() throws TicketNotFoundException {

        List<Ticket> arrList = ticketInMemoryStorage.getTicketData().values()
                .stream()
                .sorted((Ticket o1, Ticket o2) -> o2.getModified().compareTo(o1.getModified()))
                .collect(Collectors.toList());
        if (!arrList.isEmpty()) {
            return Collections.unmodifiableList(arrList);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    public int getTotalTicketInSystem() {
        return ticketInMemoryStorage.getTicketData().size();
    }

    public Ticket findOldestTicketInSystem() {
        return ticketInMemoryStorage.getTicketData().values().stream().max( (ticket1, ticket2) -> ticket2.getCreated().compareTo(ticket1.getCreated())).get();
    }

    public Map<String, Integer> findAllTagsWithTicketCount() {
        Map<String, Integer> hashMap = new HashMap<>();
        ticketInMemoryStorage.getTicketData().values().forEach( ticket -> {
                for (String tags : ticket.getTags()){
                    if(hashMap.containsKey(tags)){
                        hashMap.put(tags, hashMap.get(tags) +1);
                    }else {
                        hashMap.put(tags, 1);
                    }
                }

             }
        );
        return hashMap;
    }

    public List<Ticket> findAllOlderThanNDays(int noofdays) {
        LocalDateTime olderDays = LocalDateTime.now().minus(noofdays, ChronoUnit.DAYS);
        return ticketInMemoryStorage.getTicketData().values().stream().filter(ticket -> ticket.getCreated().isBefore(olderDays)).collect(Collectors.toList());

    }
    public List<Ticket> findAllByAgentName(String agentName) throws TicketNotFoundException {

        List<Ticket> arrList = ticketInMemoryStorage.getTicketData().values().stream()
                .filter(t -> t.getAgentName().equalsIgnoreCase(agentName))
                .collect(Collectors.toList());

        if (!arrList.isEmpty()) {
            return Collections.unmodifiableList(arrList);
        }
        logger.error("");
        throw new TicketNotFoundException("Ticket Not Found");

    }

    public List<Ticket> findAllByTag(String tag) throws TicketNotFoundException {

        List<Ticket> arrList = ticketInMemoryStorage.getTicketData().values().stream()
                .filter(t -> t.getTags().contains(tag))
                .collect(Collectors.toList());

        if (!arrList.isEmpty()) {
            return Collections.unmodifiableList(arrList);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    public Map<String, Integer> findAllAgentWithTicketCount() throws TicketNotFoundException {

        TreeMap<String, Integer> tmCount = new TreeMap<>();

        ticketInMemoryStorage.getTicketData().values().stream().forEach(ticket -> {
            if (tmCount.containsKey(ticket.getAgentName())) {
                tmCount.put(ticket.getAgentName(), tmCount.get(ticket.getAgentName()) + 1);
            } else {
                tmCount.put(ticket.getAgentName(), 1);
            }
        });

        if (!tmCount.isEmpty()) {
            return Collections.unmodifiableMap(tmCount);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }
}
