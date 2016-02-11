package com.helpdesk.controller;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketFailure;
import com.helpdesk.model.Ticket;
import com.helpdesk.serialization.TicketSerialization;
import com.helpdesk.services.TicketReportService;
import com.helpdesk.services.TicketService;
import com.helpdesk.logger.TicketLogger;
import com.sun.istack.internal.NotNull;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class TicketController {
    private TicketService ticketService = null;
    private TicketReportService ticketReportService = null;

    /**
     * Initialize objects
     * */
    public TicketController(){

        ticketService = new TicketService();
        ticketReportService = new TicketReportService();
    }


    /**
     * controller to get data for create ticket
     * */
    public Ticket create(String subject, String agentName, HashSet<String> tagSet) throws InvalidParamsException, TicketFailure {
        TicketLogger.writeLog(Level.INFO, "create controller start");

        if(subject != null && !subject.trim().isEmpty() && agentName != null && !agentName.trim().isEmpty() && tagSet != null) {
            Ticket tickets = ticketService.createTicket(subject, agentName, tagSet);
            return tickets;
        }
        else throw new InvalidParamsException("Please give proper input!");
    }

/*
    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action)  throws InvalidParameterException {
        if(ticketService.isTicketExist(id)){
            Ticket ticket = ticketService.update(id, agentName, tags, action);
            return ticket;
        }
        else   throw new InvalidParameterException();
    }

    public Boolean delete(int id) {
        if(ticketService.delete(id))
            return  true;
        else
             return false;
    }

    public Ticket getTicket(int id) throws InvalidParameterException{
        return ticketService.ticketDetails(id);
    }

    public  List<Ticket> getTicketsByAgent(String agentName){
        return ticketReportService.ticketsByAgentName(agentName);
    }

    public List<Ticket> getTicketsByTag(String tag){
        return ticketReportService.ticketsByTag(tag);
    }

    public List<Ticket> getTickets(){
        return ticketService.tickets();
    }

    public Map<String, Integer> getAllAgentsTicketCount(){
        return ticketReportService.ticketCountsGroupByAgent();
    }

    public int getTotalTicketCount(){
       return ticketReportService.getTotalTicketCounts();
    }

    public Ticket getOldestTicket() throws InvalidParameterException{
        Ticket ticket = ticketReportService.oldestTicket();
        return ticket;
    }

    public List<Ticket> getOlderTicketsThanDays(int days) {
       return ticketReportService.ticketsOlderByDays(days);
    }

    public boolean isTicketExist(int id){
        return true;
    }*/
}