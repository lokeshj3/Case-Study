package com.helpdesk.services;

import com.helpdesk.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 10/2/16.
 */
public class TicketReportService {
    public List<Ticket> ticketsByAgentName(String agentName){
        //code to return all tickets assigned to agent
        return new ArrayList<>();
    }

    public List<Ticket> ticketsByTag(String tag){
        //code to return all tickets by tag
        return new ArrayList<>();
    }
    public Map<String , Integer> ticketCountsGroupByAgent(){
        //code to return ticket count group byb agent
        return new HashMap<>();
    }

    public int getTotalTicketCounts() {
        // code to return total number in the sysytem
        return 0;
        //return new ArrayList<>(); if no tickets found in the system
    }
    public Map<String , Integer> ticketCountsGroupByAgent(){
        //code to return ticket count group byb agent
        //return new HashMap<>() if no tickets found in the system;
    }

    public int getTotalTicketCounts() {
        // code to return total number in the system
        //return 0; if no tickets found in the system
    }

    public Ticket oldestTicket() {
        // code to return oldest ticket in the system by created
        return null;
    }
    public List<Ticket> ticketsOlderByDays(int day){
        // code to return all tickets older than given day(s)
        return new ArrayList<>();

    }
}
