package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

import java.time.LocalDateTime;
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


    public List<Ticket> ticketsByTag(Set<String> tagSet){
        return objRepository.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getTags()
                        .stream()
                        .anyMatch(tag -> tagSet.contains(tag)))
                .collect(Collectors.toList());
    }

    public Map<String , List<Ticket>> ticketCountsGroupByAgent(){
        return Collections.unmodifiableMap(objRepository.getAllTickets().stream().collect(Collectors.groupingBy(Ticket::getAgent)));
    }

    public int getTotalTicketCounts() {
        return objRepository.getAllTickets().size();
    }

    public Ticket oldestTicket() {
        return objRepository.getAllTickets()
                .stream()
                .sorted((Ticket t1, Ticket t2) -> t1.getCreated()
                        .compareTo(t2.getCreated()))
                .findFirst().get();
    }

    public List<Ticket> ticketsOlderByDays(int days){
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
        return objRepository.getAllTickets().stream().filter(ticket -> ticket.getCreated().compareTo(localDateTime) < 0).sorted(Comparator.comparing(Ticket::getCreated)).collect(Collectors.toList());
    }

}
