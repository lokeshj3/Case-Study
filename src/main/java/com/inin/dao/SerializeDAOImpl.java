package com.inin.dao;

import com.inin.model.Ticket;
import com.inin.util.FileHandler;
import com.inin.util.TicketUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 11/2/16.
 */
public class SerializeDAOImpl implements SerializeDAO{

    /**
     * Serialize Single ticket into specified file
     * @param ticket
     */
    @Override
    public void serializeTicket(Ticket ticket) {
        ObjectOutputStream oos = null;
        try{
            File file = FileHandler.createFile(TicketUtil.getProperty("serializeTicketFile"));
            if(file.length() != 0)
            {
                oos = new ObjectOutputStream(new FileOutputStream(file, true)) {
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }else {
                oos = new ObjectOutputStream(new FileOutputStream(file));
            }
            oos.writeObject(ticket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Serialize List of Object into specified file
     * @param ticketList
     */
    @Override
    public void serializeTickets(List<Ticket> ticketList) {
        ObjectOutputStream oos = null;
        try {
            File file = FileHandler.createFile(TicketUtil.getProperty("serializeTicketFile"));
            if(file.length() != 0)
            {
                oos = new ObjectOutputStream(new FileOutputStream(file, true)) {
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }else
                oos = new ObjectOutputStream(new FileOutputStream(file));
            Iterator<Ticket> iterator = ticketList.iterator();
            while (iterator.hasNext())
            {
                Ticket ticket = iterator.next();
                oos.writeObject(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Deserialize single object from file
     * @return
     */
    @Override
    public Ticket deserializeTicket() {
        Ticket ticket = null;
        File file = FileHandler.createFile(TicketUtil.getProperty("serializeTicketFile"));
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ticket = (Ticket)ois.readObject();
        }catch (EOFException e){
//            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return ticket;
    }

    /**
     * Deserialize single object from file
     * @return
     */
    @Override
    public List<Ticket> deserializeTickets() {
        List<Ticket> tickets = new ArrayList<>();
        File file = FileHandler.createFile(TicketUtil.getProperty("serializeTicketFile"));
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                Ticket ticket = (Ticket)ois.readObject();
                tickets.add(ticket);
            }
        }catch (EOFException e){
//            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return tickets;
    }
}
