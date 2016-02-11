package com.helpdesk.ticket;

import com.helpdesk.exception.DuplicateTicketIdException;
import com.helpdesk.exception.TicketNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public interface TicketDAO {
    //for create & update

    Ticket create(Ticket ticket) throws DuplicateTicketIdException;

    Ticket update(Ticket ticket) throws TicketNotFoundException;

    boolean delete(int ticketId) throws TicketNotFoundException;

    Ticket find(int ticketId) throws TicketNotFoundException;

    List<Ticket> findAll() throws TicketNotFoundException;

    List<Ticket> findAllByAgentName(String agentName) throws TicketNotFoundException;

    List<Ticket> findAllByTag(String tag) throws TicketNotFoundException;


    Map<String, Integer> findAllAgentWithTicketCount() throws TicketNotFoundException;

    int getTotalTicketInSystem();


    Ticket findOldestTicketInSystem();

    Map<String, Integer> findAllTagsWithTicketCount();

    List<Ticket> findAllOlderThanNDays(int noofdays);

    boolean isExist(int ticketId);

}
