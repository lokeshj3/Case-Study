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
        //MD: Here we need only agent with it's ticket count. No need to pass List of Ticket of each agent. We can achieve
        // this by passing Collectors.counting() second parameter in groupingBy function.
        return Collections.unmodifiableMap(objRepository.getAllTickets().stream().collect(Collectors.groupingBy(Ticket::getAgent)));
    }

    public int getTotalTicketCounts() {
        writeLog(Level.INFO, "getTotalTicketCounts service start");

        //MD: For only ticket count you fetch all ticket and then cont its size. I think it better to directly count the
        // record in data base.
        return objRepository.getAllTickets().size();
    }

    public Ticket oldestTicket() {
        writeLog(Level.INFO, "oldestTicket service start");
        return objRepository.getAllTickets()
                .stream()
                .sorted((Ticket t1, Ticket t2) -> t1.getCreated()
                        .compareTo(t2.getCreated()))
                .findFirst().get();
    }

    public List<Ticket> ticketsOlderByDays(int days){
        writeLog(Level.INFO, "ticketsOlderByDays service start");
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
        //MD:Why you need sorting according to created here?
        return objRepository.getAllTickets().stream().filter(ticket -> ticket.getCreated().compareTo(localDateTime) < 0).sorted(Comparator.comparing(Ticket::getCreated)).collect(Collectors.toList());
    }


    public Map<String, List<Ticket>> getTicketCountByTag(){
        writeLog(Level.INFO, "getTicketCountByTag service start");
        //MD:Here you need ticket count, no need to return Ticket list
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
