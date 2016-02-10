package com.helpdesk.tickets;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 10/2/16.
 */
public class TicketInMemoryStorage {
    private Map<Integer, TicketModel> ticketData = new HashMap<Integer, TicketModel>();
    private static TicketInMemoryStorage ticketInMemoryStorage = new TicketInMemoryStorage();

    public static TicketInMemoryStorage getInstance() {
        return ticketInMemoryStorage;
    }

    public void writeData(int id, TicketModel ticketModel) {
        ticketData.put(id, ticketModel);
    }

    public Map<Integer, TicketModel> getTicketData() {
        return Collections.unmodifiableMap(ticketData);
    }
}
