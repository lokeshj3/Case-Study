package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.*;
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

    public Map<String , List<Ticket>> ticketCountsGroupByAgent(){
        writeLog(Level.INFO, "ticketCountsGroupByAgent start");
        return Collections.unmodifiableMap(objRepository.getAllTickets().stream().collect(Collectors.groupingBy(Ticket::getAgent)));
    }

    public int getTotalTicketCounts() {
        writeLog(Level.INFO, "getTotalTicketCounts service start");
        return objRepository.getAllTickets().size();
    }

    public Ticket oldestTicket() {
        writeLog(Level.INFO, "oldestTicket service start");
        //Ganesh D: instead of sorting all tickets by created & finding first element, it would be much better
        // if you can compare & find the oldest element, as sorting is heaving operation, swapping/shifting is heavy
        return objRepository.getAllTickets()
                .stream()
                .sorted((Ticket t1, Ticket t2) -> t1.getCreated()
                        .compareTo(t2.getCreated()))
                .findFirst().get();
    }

    public List<Ticket> ticketsOlderByDays(int days){
        writeLog(Level.INFO, "ticketsOlderByDays service start");
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
        return objRepository.getAllTickets().stream().filter(ticket -> ticket.getCreated().compareTo(localDateTime) < 0).sorted(Comparator.comparing(Ticket::getCreated)).collect(Collectors.toList());
    }


    public Map<String, List<Ticket>> getTicketCountByTag(){
        writeLog(Level.INFO, "getTicketCountByTag service start");
        //Ganesh D: if you just required ticket count, then it make sense to have an Integer value
        // rather than List<Ticket>, as if you using list it makes process heavy
        Map<String, List<Ticket>> tagCountMap = new HashMap<>();
        objRepository.getAllTickets().stream()
                .forEach(ticket -> ticket.getTags().forEach(tag ->{
                    if(tagCountMap.containsKey(tag))
                        tagCountMap.get(tag).add(ticket);
                    else{
                        List<Ticket> list = new ArrayList<>();
                        list.add(ticket);
                        tagCountMap.put(tag, list);
                    }
                }));

        return tagCountMap;
    }
}
