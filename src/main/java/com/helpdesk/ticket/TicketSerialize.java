package com.helpdesk.ticket;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public interface TicketSerialize {
    boolean serializeSingleTicket(Ticket ticket) throws IOException;

    Ticket deSerializeSingleTicket() throws IOException, ClassNotFoundException;

    boolean serializeMultiTicket(List<Ticket> ticketList) throws IOException;

    List<Ticket> deSerializeMultiTicket() throws IOException, ClassNotFoundException;
}
