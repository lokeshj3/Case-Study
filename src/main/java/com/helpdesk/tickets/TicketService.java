package com.helpdesk.tickets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {
    public final TicketModel createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags) {
        //logic for creation of ticket
        return new TicketModel();
    }

    public final TicketModel updateTicket(int ticketId) {
        //logic for updation of ticket
        return new TicketModel();
    }

    public final boolean deleteTicket(int ticketId) {
        //logic for deletion of ticket
        return false;
    }

    public final TicketModel getTicketDetail(int ticketId) {
        //logic for get details of a ticket
        return new TicketModel();
    }

    public final List<TicketModel> getTicketList() {
        //logic for get list of tickets
        return new ArrayList<TicketModel>();
    }
}
