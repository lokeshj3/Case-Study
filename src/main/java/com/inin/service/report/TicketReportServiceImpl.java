package com.inin.service.report;

import com.inin.dao.DAOFactory;
import com.inin.dao.TicketReportDAO;
import com.inin.model.Ticket;
import com.inin.util.TicketUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/2/16.
 */
public class TicketReportServiceImpl implements TicketReportService{

    private TicketReportDAO ticketReportDAO = DAOFactory.newTicketReportDAOInstance();

    /**
     * Return Ticket of specified Id
     * @param id
     * @return Ticket
     */
    public Ticket getTicket(int id){
        return ticketReportDAO.findById(id);
    }


    /**
     * Return all Ticket list
     * @return
     */
    public List<Ticket> getAllTickets()
    {
        return ticketReportDAO.findAll();
    }


    /**
     * Return the Ticket list of specified tag
     * @param tag
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> ticketsByTag(String tag) {
        if (!TicketUtil.isValidString(tag))
            return new ArrayList<>();
        return ticketReportDAO.findAllByTag(tag);
    }


    /**
     * Return the Ticket list of specified agent
     * @param agent
     * @return List<Ticket>
     */
    @Override
    public List<Ticket> ticketsByAgent(String agent) {
        if (!TicketUtil.isValidString(agent))
            return new ArrayList<>();
        return ticketReportDAO.findAllByAgent(agent);
    }


    /**
     * Return the agent's Ticket count
     * @return Map<String, Long>
     */
    @Override
    public Map<String, Long> ticketsCountByAgent() {
        return ticketReportDAO.ticketsCountByAgent();
    }


    /**
     * Return the tag's Ticket count
     * @return
     */
    @Override
    public Map<String, Long> ticketsCountByTag() {
        return ticketReportDAO.ticketsCountByTag();
    }


    /**
     * Return total ticket count
     * @return int
     */
    @Override
    public int totalTicketCount() {
        return ticketReportDAO.count();
    }


    /**
     * Return Oldest Ticket in system
     * @return Ticket
     */
    @Override
    public Ticket oldestTicket() {
        return ticketReportDAO.findOldestRecord();
    }


    /**
     * Return List of Ticket older than specified Date
     * @param date
     * @return
     */
    @Override
    public List<Ticket> ticketOlderByDate(LocalDateTime date) {
        return ticketReportDAO.findTicketsFromDate(date);
    }
}
