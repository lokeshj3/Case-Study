package com.helpdesk.ticket;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.InvalidTicketDAOFactoryTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public class ReportService {

    TicketDAO ticketDAO;
    static Logger logger = LoggerFactory.getLogger(ReportService.class);

    ReportService() throws InvalidTicketDAOFactoryTypeException{
        ticketDAO = TicketDAOFactory.get(TicketDAOFactoryType.INMEMORY);
    }

    /**
     * get total number of tickets present in the system
     * @return count
     */
    public int getTicketsCountInSystem() {
        return ticketDAO.getTotalTicketInSystem();
    }

    /**
     * get all tags with ticket count
     * @return count with tags
     */
    public Map<String, Integer> getTagsWithTicketCount() {
        return ticketDAO.findAllTagsWithTicketCount();
    }

    /**
     * Get list of tickets older than N number of days
     * @param noOfDays
     * @return list of tickets
     * @throws InvalidParamsException
     */
    public List<Ticket> getTicketsOlderThanNDays(int noOfDays) throws InvalidParamsException{
        if (noOfDays < 0) {
            logger.error("Invalid number of days");
            throw new InvalidParamsException("Number of days cannot be less than zero");
        } else {
            return ticketDAO.findAllOlderThanNDays(noOfDays);
        }
    }

    /**
     * Get oldest ticket in the system
     * @return oldest ticket
     */
    public Ticket getOldestTicketInSystem() {
        return ticketDAO.findOldestTicketInSystem();
    }
}
