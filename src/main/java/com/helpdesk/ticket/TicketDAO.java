package com.helpdesk.ticket;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public interface TicketDAO {
    //for create & update
    TicketModel save(TicketModel ticketModel);

    boolean delete(int ticketId);

    TicketModel find(int ticketId);

    List<TicketModel> findAll();

    List<TicketModel> findAllByAgentName(String agentName);

    List<TicketModel> findAllByTag(String tag);

    Map<String, Integer> findAllAgentWithTicketCount();

    int getTotalTicketInSystem();

    TicketModel findOldestTicketInSystem();

    Map<String, Integer> findAllTagsWithTicketCount();

    List<TicketModel> findAllOlderThanNDays(int noofdays);
}
