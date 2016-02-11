package com.inin.dao;

import com.inin.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/2/16.
 */
public interface TicketReportDAO {
    Ticket findById(int id);

    List<Ticket> findAll();

    List<Ticket> findAllByAgent(String agent);

    List<Ticket> findAllByTag(String tag);

    Ticket findOldestRecord();

    List<Ticket> findTicketsFromDate(LocalDateTime date);

    int count();

    Map<String,Long> ticketsCountByAgent();

    Map<String,Long> ticketsCountByTag();
}
