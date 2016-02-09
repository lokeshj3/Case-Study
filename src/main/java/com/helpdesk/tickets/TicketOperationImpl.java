package com.helpdesk.tickets;

import java.util.List;

/**
 * Created by root on 9/2/16.
 */
public class TicketOperationImpl implements TicketOperation {
    public TicketModel save(TicketModel ticketModel) {
        return null;
    }

    public boolean delete(int ticketId) {
        return false;
    }

    public TicketModel find(int ticketId) {
        return null;
    }

    public List<TicketModel> findAll() {
        return null;
    }

    public List<TicketModel> findAllByAgentName(String agentName) {
        return null;
    }

    public List<TicketModel> findAllByTag(String tag) {
        return null;
    }

    public List<TicketModel> findAllAgentWithTicketCount() {
        return null;
    }
}
