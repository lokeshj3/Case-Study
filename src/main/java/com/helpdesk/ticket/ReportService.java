package com.helpdesk.ticket;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.InvalidTicketDAOFactoryTypeException;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public class ReportService {

    TicketDAO ticketDAO;

    ReportService() throws InvalidTicketDAOFactoryTypeException{
        ticketDAO = TicketDAOFactory.get(TicketDAOFactoryType.INMEMORY);
    }

    public int getTicketsCountInSystem() {
        return ticketDAO.getTotalTicketInSystem();
    }

    public Map<String, Integer> getTagsWithTicketCount() {
        return ticketDAO.findAllTagsWithTicketCount();
    }

    public List<Ticket> getTicketsOlderThanNDays(int noOfDays) throws InvalidParamsException{
        if (noOfDays < 0) {
            throw new InvalidParamsException("Number of days cannot be less than zero");
        } else {
            return ticketDAO.findAllOlderThanNDays(noOfDays);
        }
    }

    public Ticket getOldestTicketInSystem() {
        return ticketDAO.findOldestTicketInSystem();
    }
}
