package com.helpdesk.ticket;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by root on 10/2/16.
 */
public class TicketInMemoryStorage {

    private Map<Integer, Ticket> ticketData = new ConcurrentHashMap<>();

    public static TicketInMemoryStorage newInstance() {
        return new TicketInMemoryStorage();
    }

    public void writeData(int id, Ticket ticket) {
        ticketData.put(id, new Ticket(ticket));
    }

    public void deleteData(int id) {
        ticketData.remove(id);
    }

    public Map<Integer, Ticket> getTicketData() {
        return Collections.unmodifiableMap(ticketData);
    }


}
