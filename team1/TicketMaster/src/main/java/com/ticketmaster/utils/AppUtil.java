package com.ticketmaster.utils;

import com.ticketmaster.models.Ticket;
import com.ticketmaster.service.TicketService;

import java.io.IOException;
import java.util.Map;

/**
 * Created by root on 11/2/16.
 */
public class AppUtil {

    public static void initializeApp()
            throws ClassNotFoundException, IOException {

        SerializerUtil util = new SerializerUtil();
        Ticket.masterId = Integer.parseInt(util.readProperty("id"));
        TicketService service = new TicketService();

        service.setTicketList((Map<Integer, Ticket>) util.readFromFile());
        service.initTags();
        service.initAgents();

    }
}
