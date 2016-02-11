package com.helpdesk.tickets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public class TicketSerializeFileImpl implements TicketSerialize{
    public boolean serializeSingleTicket(TicketModel ticketModel) throws IOException{
        //tODO: logic
        return false;
    }

    public TicketModel deSerializeSingleTicket() throws IOException, ClassNotFoundException {
        // TODO: logic
        return new TicketModel();
    }

    public boolean serializeMultiTicket(List<TicketModel> ticketModelList) throws IOException {
        // TODO: logic
        return false;
    }

    public List<TicketModel> deSerializeMultiTicket() throws IOException, ClassNotFoundException {
        // TODO: logic
        return new ArrayList<TicketModel>();
    }
}



