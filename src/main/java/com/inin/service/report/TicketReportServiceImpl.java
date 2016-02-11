package com.inin.service.report;

import com.inin.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/2/16.
 */
public class TicketReportServiceImpl implements TicketReportService{
    @Override
    public List<Ticket> ticketsByTag(String tag) {
        return null;
    }

    @Override
    public List<Ticket> ticketsByAgent(String agent) {
        return null;
    }

    @Override
    public Map<String, Integer> ticketsCountByAgent() {
        return null;
    }

    @Override
    public int totalTicketCount() {
        return 0;
    }

    @Override
    public List<Ticket> oldestTicket() {
        return null;
    }

    @Override
    public List<Ticket> ticketOlderByDate(LocalDateTime date) {
        return null;
    }
}
