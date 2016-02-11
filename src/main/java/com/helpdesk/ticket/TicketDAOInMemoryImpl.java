package com.helpdesk.ticket;

import java.util.List;
import java.util.Map;

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
        //TODO:logic
    }

    public Ticket findOldestTicketInSystem() {
        //TODO:logic
    }

    public Map<String, Integer> findAllTagsWithTicketCount() {
        //TODO:logic
    }

    public List<Ticket> findAllOlderThanNDays(int noofdays) {
        //TODO:logic
    }
}
