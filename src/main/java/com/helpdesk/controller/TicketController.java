package com.helpdesk.controller;

import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import com.helpdesk.services.TicketReportService;
import com.helpdesk.services.TicketService;
import static com.helpdesk.logger.TicketLogger.*;

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
        writeLog(Level.INFO, "create controller start");

        if(subject != null && !subject.trim().isEmpty() && agentName != null && !agentName.trim().isEmpty() && tagSet != null) {
            return ticketService.createTicket(subject, agentName, tagSet);
        }
        else throw new InvalidParamsException("Please give proper input!");
    }


    public Ticket update(int id, String agentName, Set<String> tagSet, String action)  throws TicketExceptions{
        writeLog(Level.INFO, "update controller start");

        if(id>0 && ((agentName != null && !agentName.trim().isEmpty()) || (tagSet != null && !action.trim().isEmpty()))) {
            return ticketService.update(id, agentName, tagSet, action);
        }
        else   throw new TicketExceptions("Invalid Params!!! Base controller");
    }

    public Boolean delete(int id) throws TicketExceptions {
        writeLog(Level.INFO, "delete controller start");

        this.getDetails(id);

        if(ticketService.delete(id))
            return  true;
        else
             throw  new TicketExceptions("Ticket Deletion Failed!");
    }

    public Ticket getDetails(int id) throws TicketExceptions{
        writeLog(Level.INFO, "getDetails start");
        if(id > 0){
            return ticketService.ticketDetails(id);
        }
        else {
            throw new InvalidParamsException("Invalid ticket id!");
        }
    }

    public  List<Ticket> getTicketsByAgent(String agentName){
        writeLog(Level.INFO, "getTicketsByAgent controller start");
        return ticketReportService.ticketsByAgentName(agentName);
    }

    public List<Ticket> getTicketsByTag(Set<String> tagSet) throws TicketExceptions{
        writeLog(Level.INFO, "get ticket by tags controller starts");
        if(tagSet != null || !tagSet.isEmpty()) {
            return ticketReportService.ticketsByTag(tagSet);
        }
        else
            throw new InvalidParamsException("Please enter proper tag names!");
      }


    public List<Ticket> getAll(){
        writeLog(Level.INFO, "getAll controller start");
        return ticketService.getAllTickets();
    }

    public Map<String , List<Ticket>> getAllAgentsTicketCount(){
        writeLog(Level.INFO, "getAllAgentsTicketCount controller start");
        return ticketReportService.ticketCountsGroupByAgent();
    }

    public int getTotalTicketCount(){
        writeLog(Level.INFO, "getTotalTicketCount controller start");
       return ticketReportService.getTotalTicketCounts();
    }

    public Ticket getOldestTicket() throws TicketExceptions{
        writeLog(Level.INFO, "getOldestTicket count controller starts");
        return ticketReportService.oldestTicket();
    }

    public List<Ticket> olderTicketsThanDays(int days) throws InvalidParamsException {
        writeLog(Level.INFO, "older tickets than days controller starts");
        if(days >= 0){
            return ticketReportService.ticketsOlderByDays(days);
        }
        else {
            throw new InvalidParamsException("Please enter proper no of days!");
        }
    }


    public void displayTickets(List<Ticket> ticketList){
        writeLog(Level.INFO, "Display ticket list controller start");
        if(ticketList.size() > 0){
            ticketList
                    .stream()
                    .sorted((Ticket t1, Ticket t2) -> t2.getUpdated()
                    .compareTo(t1.getUpdated())).forEach(System.out::println);
        }
        else {
            writeLog(Level.INFO, "No tickets in system");
            System.out.println("No tickets found!");
        }
    }


    public void getTicketCountByTag(){
        writeLog(Level.INFO, "get ticket count by tag controller starts");
        ticketReportService.getTicketCountByTag()
                .forEach((String tagName,List<Ticket> ticketList)-> System.out.println(tagName+"   :   "+ticketList.size()));
    }

    public boolean removeAllTickets(){
        writeLog(Level.INFO, "delete all tickets controller starts");
        return ticketService.deleteAllTickets();
    }
}