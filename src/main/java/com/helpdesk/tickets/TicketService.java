package com.helpdesk.tickets;

import java.util.HashSet;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {
    //
    public TicketModel createTicket(int a) {

        TicketModel ticketModel = new TicketModel.Builder(1,"sub","agent").build();

        //
        int id = 1;
        String subject = "subject";
        String agent = "agent";
        HashSet<String> tags = new HashSet<String>();
        tags.add("a");
        return null;
    }
}
