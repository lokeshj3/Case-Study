package com.helpdesk.ticket;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public interface TicketDAO {
    //for create & update
    Ticket save(Ticket ticket);

    boolean delete(int ticketId);

    Ticket find(int ticketId);

    List<Ticket> findAll();

    List<Ticket> findAllByAgentName(String agentName);

    List<Ticket> findAllByTag(String tag);

    Map<String, Integer> findAllAgentWithTicketCount();

    int getTotalTicketInSystem();

    Ticket findOldestTicketInSystem();

    Map<String, Integer> findAllTagsWithTicketCount();

    List<Ticket> findAllOlderThanNDays(int noofdays);
}
