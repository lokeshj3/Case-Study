package com.ticketmaster.utils;

import com.ticketmaster.models.Ticket;
import com.ticketmaster.service.TicketService;

import java.io.IOException;
import java.util.Map;

/**
 * AppUtil class
 * contains common purpose methods that can be used in the system
 * Created by Evans on 11/2/16.
 */
public class AppUtil {

    public static void initializeApp()
            throws ClassNotFoundException, IOException {

        SerializerUtil util = new SerializerUtil();
        Ticket.masterId = Integer.parseInt(util.readProperty("id"));
        CustomLogger.init().info("master id set to "+Ticket.masterId+" during initialization");
        TicketService service = new TicketService();

        service.setTicketList((Map<Integer, Ticket>) util.readFromFile());
        service.initTags();
        service.initAgents();

    }

    public static int prepareCount(Map<String, Integer> map, String str){
        return map.containsKey(str) ? map.get(str) +1 : 1;
    }
}
