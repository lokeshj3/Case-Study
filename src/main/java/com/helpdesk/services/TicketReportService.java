package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 10/2/16.
 */
public class TicketReportService {

    private TicketRepository objRepository;

    public TicketReportService(){
        objRepository = new TicketRepository();
    }
    public List<Ticket> ticketsByAgentName(String agentName){
        return  objRepository.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getAgent().toLowerCase().equals(agentName.toLowerCase()))
                .collect(Collectors.toList());
    }

  /*  public List<Ticket> ticketsByTag(String tag){
        //code to return all tickets by tag

        //return new ArrayList<>();
        return new ArrayList<>();
    }*/

    public Map<String , List<Ticket>> ticketCountsGroupByAgent(){
        return Collections.unmodifiableMap(objRepository.getAllTickets().stream().collect(Collectors.groupingBy(Ticket::getAgent)));
    }

    public int getTotalTicketCounts() {
        return objRepository.getAllTickets().size();
    }
/*
    public Ticket oldestTicket() {
        // code to return oldest ticket in the system by created
        return null;
    }
    public List<Ticket> ticketsOlderByDays(int day){
        // code to return all tickets older than given day(s)
        //return new ArrayList<>();
        return new ArrayList<>();

    }*/

}
