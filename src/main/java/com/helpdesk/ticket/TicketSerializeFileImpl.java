package com.helpdesk.ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 10/2/16.
 */
public class TicketSerializeFileImpl implements TicketSerialize{
    public boolean serializeSingleTicket(Ticket ticketModel) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ticket.txt"));

        out.writeObject(ticketModel);

        out.flush();

        return true;
    }

    public Ticket deSerializeSingleTicket() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("ticket.txt"));

        Ticket ticket = (Ticket) in.readObject();

        in.close();

        return  ticket;
    }

    public boolean serializeMultiTicket(List<Ticket> ticketModelList) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("multiTicket.txt"));

        for (Ticket ticket : ticketModelList){
            out.writeObject(ticket);
        }

        out.flush();

        return true;
    }

    public List<Ticket> deSerializeMultiTicket() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("multiTicket.txt"));

        List<Ticket> tickets = new ArrayList<>();

        tickets.add((Ticket) in.readObject());

        in.close();

        return tickets;
    }
}
