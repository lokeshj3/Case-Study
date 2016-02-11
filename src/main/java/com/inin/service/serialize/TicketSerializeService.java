package com.inin.service.serialize;

import com.inin.model.Ticket;

import java.util.List;

/**
 * Created by root on 11/2/16.
 */
public interface TicketSerializeService {

    void serializeTicket(String fileName, Ticket ticket);

    void serializeTickets(String fileName, List<Ticket> ticketList);

    Ticket deSerializeTicket(String fileName);

    List<Ticket> deSerializeTickets(String fileName);
}
