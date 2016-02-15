package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.helpdesk.logger.TicketLogger.writeLog;

public class TicketReportService {

    private TicketRepository objRepository;

    public TicketReportService(){
        objRepository = new TicketRepository();
    }
    public List<Ticket> ticketsByAgentName(String agentName){
        writeLog(Level.INFO, "ticketsByAgentName service start");
        return  objRepository.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getAgent().toLowerCase().equals(agentName.toLowerCase()))
                .collect(Collectors.toList());
    }


    public List<Ticket> ticketsByTag(Set<String> tagSet){
        writeLog(Level.INFO, "ticketsByTag service start");
        return objRepository.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getTags()
                        .stream()
                        .anyMatch(tagSet::contains))
                .collect(Collectors.toList());
    }

    public Map<String, Long> ticketCountsGroupByAgent(){
        writeLog(Level.INFO, "ticketCountsGroupByAgent start");
        return objRepository.getAllTickets().stream()
                .collect(Collectors.groupingBy(Ticket::getAgent, Collectors.counting()));
    }

    public int getTotalTicketCounts() {
        writeLog(Level.INFO, "getTotalTicketCounts service start");
        return objRepository.countTickets();
    }

    public Ticket oldestTicket() {
        writeLog(Level.INFO, "oldestTicket service start");
        return objRepository.getAllTickets()
                .stream()
                .min((Ticket t1, Ticket t2) -> t1.getCreated()
                        .compareTo(t2.getCreated()))
                    .get();
    }

    public List<Ticket> ticketsOlderByDays(int days){
        writeLog(Level.INFO, "ticketsOlderByDays service start");
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
        return objRepository.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getCreated().compareTo(localDateTime) < 0).collect(Collectors.toList());
    }


    public Map<String, Integer> getTicketCountByTag(){
        writeLog(Level.INFO, "getTicketCountByTag service start");
        Map<String, Integer> tagCountMap = new HashMap<>();
        objRepository.getAllTickets().stream()
                .forEach(ticket -> ticket.getTags().forEach(tag ->{
                    if(tagCountMap.containsKey(tag)) {
                        tagCountMap.put(tag, tagCountMap.get(tag) + 1);
                    }
                    else{
                       tagCountMap.put(tag, 1);
                    }
                }));

        return tagCountMap;
    }
}
