package com.helpdesk.tickets;

import java.util.Map;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketServiceImpl implements TicketService {
    public final TicketModel createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags) {
        //logic for creation of ticket
        return null;
    }

    public final TicketModel updateTicket(int ticketId) {
        //logic for updation of ticket
        return null;
    }

    public final boolean deleteTicket(int ticketId) {
        //logic for deletion of ticket
        return false;
    }

    public final TicketModel getTicketDetail(int ticketId) {
        //logic for get details of a ticket
        return null;
    }

    public final Map<Integer, TicketModel> getTicketList(int ticketId) {
        //logic for get list of tickets
        return null;
    }
}
