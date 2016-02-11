package com.helpdesk.data;

import com.helpdesk.ticket.TicketModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public class Repository {
    public Map<Integer, TicketModel> ticketData = new HashMap<Integer, TicketModel>();

    private static Repository repository = new Repository();

    private Repository() {
    }

    public static Repository getInstance() {
        return repository;
    }
}
