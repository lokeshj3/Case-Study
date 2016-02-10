package com.helpdesk.controller;

import com.helpdesk.model.Ticket;
import com.helpdesk.services.TicketService;
import com.sun.istack.internal.NotNull;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TicketController {
    private TicketService ticketService = null;

    public TicketController(){
        ticketService = new TicketService();
    }

    public void create(String subject, String agentName, HashSet<String> tagSet){
        System.out.println("inside ticket controller");
        if(subject != null && !subject.isEmpty() && agentName != null && !agentName.isEmpty()) {
            try{
                Ticket tickets = ticketService.createTicket(subject,agentName,tagSet);
                System.out.println("Ticket Has been created successfully." + tickets.toString());
            }
            catch (InvalidParameterException ie){
                System.out.println("There is error while creating a ticket.");
            }
        }
        else System.out.println("Ticket Subject & Agent Name is compulsory.");
    }

    public void update(int id, @NotNull String agentName, HashSet<String> tags, String action){
        if(ticketService.isTicketExist(id)){
            Ticket ticket = ticketService.update(id, agentName, tags, action);
            System.out.println("Ticket id " + id + " is updated successfully");
            System.out.println(ticket.toString());
        }
        else    System.out.println("Entered Ticket id " + id + " is not present in the system.");
    }

    public void delete(int id) {
        if(ticketService.delete(id)) {
            System.out.println("Ticket id " + id + " is deleted successfully.");
        }
        else  System.out.println("Entered ticket id " + id + " is not present in the system." );
    }

    public void getTicket(int id){
        try {
            Ticket ticket = ticketService.ticketDetails(id);
            System.out.println("Details of ticket id : " + id + "\n\n" + ticket.toString());
        }catch (InvalidParameterException e){
            System.out.println("Invalid ticket Id . Entered ticket id " + id + " is not present in the system.");
        }
    }

    public void getTicketsByAgent(String agentName){
        if(!agentName.isEmpty()){
            List<Ticket> agentTicketList = ticketService.ticketsByAgentName(agentName);
            if(agentTicketList.size()>0){
                agentTicketList.forEach(ticket -> System.out.println(ticket.toString()));
            }
            else  System.out.println("No records found for entered agent " + agentName);
        }
        else    System.out.println("Invalid agent Name!!!");
    }

    public void getTicketsByTag(String tag){
        if(!tag.isEmpty()){
            List<Ticket> ticketList = ticketService.ticketsByTag(tag);
            if(ticketList.size()>0)
            {
                ticketList.forEach(ticket -> System.out.println(ticket.toString()));
            }
            else    System.out.println("No tickets are found for entered tag " + tag);
        }
        else    System.out.println("Invalid Tag!!!. Tag Name should not be empty!!!");
    }

    public void getTickets(){
        Map<Integer, Ticket> tickets = ticketService.tickets();
        if(tickets.size() >0 ){
            //tickets.values().stream().sorted((Ticket t1, Ticket t2) -> -t1.getModified().compareTo(t2.getModified())).forEach(ticket -> System.out.println(ticket.toString()));
        }
        else System.out.println("No tickets found!!!");
    }

    public void getAllAgentsTicketCount(){
        Map<String, List<Ticket>> ticketCountList = ticketService.ticketCountsGroupByAgent();
        if(ticketCountList.size()>0){
            ticketCountList.forEach((String agentName, List<Ticket> ticketList) -> System.out.println(agentName + " :  " +ticketList.size()));
        }
        else     System.out.println("No Records Found!!!");
    }

    public void getTotalTicketCount(){
        System.out.println("Total number of tickets present in the system : " + ticketService.getTotalTicketCounts());
    }

    public void getOldestTicket(){
        try {
            Ticket ticket = ticketService.oldestTicket();
            System.out.println("Oldest ticket in the system is : \n" + ticket.toString());
        }catch (InvalidParameterException e){
            System.out.println("No Ticket Found in the system");
        }
    }

    public void getOlderTicketsThanDays(int days) {
        List<Ticket> ticketList = ticketService.ticketsOlderByDays(days);
        if(ticketList.size()>0)
            ticketList.forEach(ticket -> System.out.println(ticket.toString()));
        else System.out.println("No tickets found");
    }

    public boolean isTicketExist(int id){
        return true;
    }
}
