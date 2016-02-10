package com.helpdesk.tickets;

import java.io.*;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public class TicketSerializeFileImpl implements TicketSerialize{
    public boolean serializeSingleTicket(TicketModel ticketModel) throws IOException{
        //tODO: logic
    }

    public TicketModel deSerializeSingleTicket() throws IOException, ClassNotFoundException {
        // TODO: logic
    }

    public boolean serializeMultiTicket(List<TicketModel> ticketModelList) throws IOException {
        // TODO: logic
    }

    public List<TicketModel> deSerializeMultiTicket() throws IOException, ClassNotFoundException {
        // TODO: logic
    }
}
