package com.inin.dao;

import com.inin.model.Ticket;

import java.util.List;

/**
 * Created by root on 11/2/16.
 */
public interface SerializeDAO {
    void serializeTicket(Ticket ticket);
    void serializeTickets(List<Ticket> ticketList);
    Ticket deserializeTicket();
    List<Ticket> deserializeTickets();
}
