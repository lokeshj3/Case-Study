package com.inin.service.report;

import com.inin.dao.DAOFactory;
import com.inin.dao.TicketReportDAO;
import com.inin.model.Ticket;
import com.inin.util.TicketUtil;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/2/16.
 */
public class TicketReportServiceImpl implements TicketReportService{

    private TicketReportDAO ticketReportDAO = DAOFactory.newTicketReportDAOInstance();

    @Override
    public List<Ticket> ticketsByTag(String tag) throws InvalidParameterException{
        if (!TicketUtil.isValidString(tag))
            throw new InvalidParameterException();

        return ticketReportDAO.findAllByTag(tag);
    }

    @Override
    public List<Ticket> ticketsByAgent(String agent) throws InvalidParameterException{
        if (!TicketUtil.isValidString(agent))
            throw new InvalidParameterException();

        return ticketReportDAO.findAllByAgent(agent);
    }

    @Override
    public Map<String, Integer> ticketsCountByAgent() {
        return null;
    }

    @Override
    public Map<String, Integer> ticketsCountByTag() {
        return null;
    }

    @Override
    public int totalTicketCount() {
        return ticketReportDAO.count();
    }

    @Override
    public Ticket oldestTicket() {
        return ticketReportDAO.findOldestRecord();
    }

    @Override
    public List<Ticket> ticketOlderByDate(LocalDateTime date) {
        return ticketReportDAO.findTicketsFromDate(date);
    }
}
