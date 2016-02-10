package com.helpdesk.tickets;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 9/2/16.
 */

 interface TicketService {
    TicketModel createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags);

    TicketModel updateTicket(int ticketId);

    boolean deleteTicket(int ticketId);

    TicketModel getTicketDetail(int ticketId);

    List<TicketModel> getTicketList();
}
