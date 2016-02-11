package com.inin.service.serialize;

import com.inin.model.Ticket;

import java.util.List;

/**
 * Created by root on 11/2/16.
 */
public class TicketSerializeServiceImpl implements TicketSerializeService{
    @Override
    public void serializeTicket(String fileName, Ticket ticket) {

    }

    @Override
    public void serializeTickets(String fileName, List<Ticket> ticketList) {

    }

    @Override
    public Ticket deSerializeTicket(String fileName) {
        return null;
    }

    @Override
    public List<Ticket> deSerializeTickets(String fileName) {
        return null;
    }
}
