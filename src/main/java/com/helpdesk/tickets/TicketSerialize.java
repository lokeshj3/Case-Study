package com.helpdesk.tickets;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public interface TicketSerialize {
    boolean serializeSingleTicket(TicketModel ticketModel) throws IOException;

    TicketModel deSerializeSingleTicket() throws IOException, ClassNotFoundException;

    boolean serializeMultiTicket(List<TicketModel> ticketModelList) throws IOException;

    List<TicketModel> deSerializeMultiTicket() throws IOException, ClassNotFoundException;
}
