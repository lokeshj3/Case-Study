package com.helpdesk.controller;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.services.TicketReportService;
import com.helpdesk.services.TicketService;
import com.helpdesk.logger.TicketLogger;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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


    public Ticket update(int id, String agentName, Set<String> tagSet, String action)  throws TicketExceptions{
        TicketLogger.writeLog(Level.INFO, "update controller start");

        if(id>0 && ((agentName != null && !agentName.trim().isEmpty()) || (tagSet != null && action != null && !action.trim().isEmpty()))) {
            Ticket ticket = ticketService.update(id, agentName, tagSet, action);
            return ticket;
        }
        else   throw new TicketExceptions("Invalid Params!!! Base controller");
    }

    public Boolean delete(int id) throws TicketExceptions {
        TicketLogger.writeLog(Level.INFO, "delete controller start");
        if(ticketService.delete(id))
            return  true;
        else
             throw  new TicketExceptions("Ticket Deletion Failed!");
    }

    public Ticket getDetails(int id) throws TicketExceptions{
        TicketLogger.writeLog(Level.INFO, "getDetails start");
        if(id == (int) id || id > 0){
            return ticketService.ticketDetails(id);
        }
        else {
            throw new InvalidParamsException("Enter proper Ticket id!");
        }
    }

    public  List<Ticket> getTicketsByAgent(String agentName){
        return ticketReportService.ticketsByAgentName(agentName);
    }

    /*  public List<Ticket> getTicketsByTag(String tag){
          return ticketReportService.ticketsByTag(tag);
      }
  */

    public List<Ticket> getAll(){
        TicketLogger.writeLog(Level.INFO, "getAll controller start");
        return ticketService.getAlltickets();
    }

    public Map<String , List<Ticket>> getAllAgentsTicketCount(){
        return ticketReportService.ticketCountsGroupByAgent();
    }

    public int getTotalTicketCount(){
       return ticketReportService.getTotalTicketCounts();
    }
/*
    public Ticket getOldestTicket() throws InvalidParameterException{
        Ticket ticket = ticketReportService.oldestTicket();
        return ticket;
    }

    public List<Ticket> getOlderTicketsThanDays(int days) {
       return ticketReportService.ticketsOlderByDays(days);
    }

    */

}