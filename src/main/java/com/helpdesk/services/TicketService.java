package com.helpdesk.services;

import com.helpdesk.model.Ticket;
import com.sun.istack.internal.NotNull;

import java.security.InvalidParameterException;
import java.util.*;

public class TicketService {
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet){
        Ticket ticket = new Ticket();

        if(ticket == null) throw new InvalidParameterException();

        return ticket;
    }

    public boolean isTicketExist(int id){
        return true;
    }

    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action) {
        Ticket ticket = new Ticket();

        return ticket;
    }

    public boolean delete(int id) {
       return true;
    }

    public Ticket ticketDetails(int id) {
        Ticket ticket = new Ticket();

        return ticket;
    }
    public Map<Integer, Ticket> tickets(){
        return new HashMap<>();
    }

    public List<Ticket> ticketsByAgentName(String agentName){
        return new ArrayList<>();
    }

    public List<Ticket> ticketsByTag(String tag){
        return new ArrayList<>();
    }

    public Map<String , List<Ticket>> ticketCountsGroupByAgent(){
        return new HashMap<>();
    }

    public int getTotalTicketCounts() {
        return 0;
    }

    public Ticket oldestTicket() {
        Ticket ticket = new Ticket();

        return ticket;
    }
    public List<Ticket> ticketsOlderByDays(int day){
        return new ArrayList<>();
    }
}
