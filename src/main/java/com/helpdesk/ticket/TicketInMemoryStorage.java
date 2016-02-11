package com.helpdesk.ticket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 10/2/16.
 */
public class TicketInMemoryStorage {

    private Map<Integer, Ticket> ticketData = new HashMap<Integer, Ticket>();
    private static TicketInMemoryStorage ticketInMemoryStorage = new TicketInMemoryStorage();

    public static TicketInMemoryStorage getInstance() {
        return ticketInMemoryStorage;
    }

    public void writeData(int id, Ticket ticket) {
        ticketData.put(id, new Ticket(ticket));
    }

    public Map<Integer, Ticket> getTicketData() {
        return Collections.unmodifiableMap(ticketData);
    }
}
