package com.helpdesk.controller;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.services.TicketReportService;
import com.helpdesk.services.TicketService;
import com.helpdesk.logger.TicketLogger;

import java.util.HashSet;
import java.util.List;
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
    public Ticket create(String subject, String agentName, HashSet<String> tagSet) throws TicketExceptions {
        TicketLogger.writeLog(Level.INFO, "create controller start");

        if(subject != null && !subject.trim().isEmpty() && agentName != null && !agentName.trim().isEmpty() && tagSet != null) {
            Ticket tickets = ticketService.createTicket(subject, agentName, tagSet);
            return tickets;
        }
        else throw new InvalidParamsException("Please give proper input!");
    }


/*    public Ticket update(int id, String agentName, HashSet<String> tagSet, String action)  throws TicketExceptions{
        TicketLogger.writeLog(Level.INFO, "update controller start");
        if(agentName != null && !agentName.trim().isEmpty() && tagSet != null && action != null && action.trim().isEmpty()) {
            Ticket ticket = ticketService.update(id, agentName, tagSet, action);
            return ticket;
        }
        else   throw new TicketExceptions("Please give proper input!");
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
*/
    public List<Ticket> getAll(){
        TicketLogger.writeLog(Level.INFO, "getAll controller start");
        return ticketService.getAlltickets();
    }
/*
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