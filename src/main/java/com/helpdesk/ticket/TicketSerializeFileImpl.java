package com.helpdesk.ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public class TicketSerializeFileImpl implements TicketSerialize{
    public boolean serializeSingleTicket(Ticket ticket) throws IOException{
        //tODO: logic
        return false;
    }

    public Ticket deSerializeSingleTicket() throws IOException, ClassNotFoundException {
        // TODO: logic
        return new Ticket();
    }

    public boolean serializeMultiTicket(List<Ticket> ticketList) throws IOException {
        // TODO: logic
        return false;
    }

    public List<Ticket> deSerializeMultiTicket() throws IOException, ClassNotFoundException {
        // TODO: logic
        return new ArrayList<Ticket>();
    }
}



