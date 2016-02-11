package com.inin.service.report;

import com.inin.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/2/16.
 */
public interface TicketReportService {

    List<Ticket> ticketsByTag(String tag);

    List<Ticket> ticketsByAgent(String agent);

    Map<String, Long> ticketsCountByAgent();

    Map<String, Long> ticketsCountByTag();

    int totalTicketCount();

    Ticket oldestTicket();

    List<Ticket> ticketOlderByDate(LocalDateTime date);
}
