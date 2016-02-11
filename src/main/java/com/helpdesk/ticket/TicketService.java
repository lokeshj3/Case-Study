package com.helpdesk.ticket;

import com.helpdesk.exception.DuplicateTicketKeyException;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {
    public final TicketModel createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags) throws InvalidParamsException, DuplicateTicketKeyException {
        //logic for creation of ticket
        return new TicketModel();
    }

    public final TicketModel updateTicket(int ticketId) throws InvalidParamsException, TicketNotFoundException {
        //logic for updation of ticket
        return new TicketModel();
    }

    public final boolean deleteTicket(int ticketId) throws InvalidParamsException, TicketNotFoundException {
        //logic for deletion of ticket
        return false;
    }

    public final TicketModel getTicketDetail(int ticketId) throws InvalidParamsException, TicketNotFoundException {
        //logic for get details of a ticket
        return new TicketModel();
    }

    public final List<TicketModel> getTicketList() {
        //logic for get list of ticket
        return new ArrayList<TicketModel>();
    }
}
