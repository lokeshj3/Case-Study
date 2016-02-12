package com.helpdesk.ticket;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public class TicketSerializeFileImpl implements TicketSerialize{

    /**
     * get file name
     * @return string
     */
    static String getFileNameWithLocation() {
        return "/tmp/ticketData.ser";
    }

    /**
     * Serialize a single ticket
     * @param ticketModel
     * @return boolean
     * @throws IOException
     */
    public boolean serializeSingleTicket(Ticket ticketModel) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFileNameWithLocation()));

        out.writeObject(ticketModel);

        out.flush();

        return true;
    }

    /**
     * Deserialize a single ticket
     * @return ticket
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public Ticket deSerializeSingleTicket() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFileNameWithLocation()));

        Ticket ticket = (Ticket) in.readObject();

        in.close();

        return  ticket;
    }

    /**
     * Serialize multiple tickets
     * @param ticketModelList
     * @return boolean
     * @throws IOException
     */
    public boolean serializeMultiTicket(List<Ticket> ticketModelList) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFileNameWithLocation()));

        for (Ticket ticket : ticketModelList){
            out.writeObject(ticket);
        }

        out.flush();

        return true;
    }

    /**
     * Deserialize multiple tickets
     * @return list of tickets
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<Ticket> deSerializeMultiTicket() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFileNameWithLocation()));

        List<Ticket> arrListTickets = new ArrayList<>();
        try
        {
            for (;;){
                arrListTickets.add((Ticket) in.readObject());
            }

        }catch (EOFException eof){

        }

        in.close();

        return arrListTickets;
    }

}
