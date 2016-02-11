package com.helpdesk.ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 9/2/16.
 */
public class TicketDAOInMemoryImpl implements TicketDAO {
    TicketInMemoryStorage ticketInMemoryStorage = TicketInMemoryStorage.getInstance();

    public Ticket save(Ticket ticket) {
        //TODO:logic
    }

    public boolean delete(int ticketId) {
        //TODO:logic
    }

    public Ticket find(int ticketId) {
        //TODO:logic
    }

    public List<Ticket> findAll() {
        //TODO:logic
    }

    public List<Ticket> findAllByAgentName(String agentName) {
        //TODO:logic
    }

    public List<Ticket> findAllByTag(String tag) {

        //TODO:logic
    }

    public Map<String, Integer> findAllAgentWithTicketCount() {
        //TODO:logic
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
}
