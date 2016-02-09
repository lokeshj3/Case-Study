package com.helpdesk.tickets;

import java.util.List;

/**
 * Created by root on 8/2/16.
 */
public interface TicketOperation {
    TicketModel save(TicketModel ticketModel);

    boolean delete(int ticketId);

    TicketModel find(int ticketId);

    List<TicketModel> findAll();

    List<TicketModel> findAllByAgentName(String agentName);

    List<TicketModel> findAllByTag(String tag);

    List<TicketModel> findAllAgentWithTicketCount();
}
