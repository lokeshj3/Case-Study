package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String , Integer> ticketCountsGroupByAgent(){
       /* return objRepository.getAllTickets()
                .stream().collect(Collectors.groupingBy(Ticket::getAgent));
*/
        //code to return ticket count group byb agent

            /*    Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty())    return new HashMap<>();
        return Collections.unmodifiableMap(masterTicketsData.values().stream().collect(Collectors.groupingBy(Ticket::getAgentName)));

        */
        return new HashMap<>();
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
