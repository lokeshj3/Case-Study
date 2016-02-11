package com.helpdesk.ticket;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 9/2/16.
 */
public class TicketDAOInMemoryImpl implements TicketDAO {
    TicketInMemoryStorage ticketInMemoryStorage = TicketInMemoryStorage.getInstance();

    public TicketModel save(TicketModel ticketModel) {
        //TODO:logic
    }

    public boolean delete(int ticketId) {
        //TODO:logic
    }

    public TicketModel find(int ticketId) {
        //TODO:logic
    }

    public List<TicketModel> findAll() {
        //TODO:logic
    }

    public List<TicketModel> findAllByAgentName(String agentName) {
        //TODO:logic
    }

    public List<TicketModel> findAllByTag(String tag) {
        //TODO:logic
    }

    public Map<String, Integer> findAllAgentWithTicketCount() {
        //TODO:logic
    }

    public int getTotalTicketInSystem() {
        //TODO:logic
    }

    public TicketModel findOldestTicketInSystem() {
        //TODO:logic
    }

    public Map<String, Integer> findAllTagsWithTicketCount() {
        //TODO:logic
    }

    public List<TicketModel> findAllOlderThanNDays(int noofdays) {
        //TODO:logic
    }
}
