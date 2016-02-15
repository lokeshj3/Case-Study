package com.helpdesk.serialization;

import com.helpdesk.components.Util;
import com.helpdesk.model.Ticket;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used for serialize and deserialize data*/
public class TicketSerialization {

    File file = null;

    public TicketSerialization(String path, String fileName){
        // Directory and File check should be there. Should not be manual process to create directories.
       file = Util.createFile(path, fileName);
    }


    /**
     * serialize tickets
     * @param ticketMap Map of tickets created
     * */
    public boolean saveTicketsInFile(Map<Integer, Ticket> ticketMap, boolean append){
        FileOutputStream fos;
        ObjectOutputStream oos = null;
        //MD:We can use try with resources.Refer below method code
        try{
            fos = new FileOutputStream(file, append);

            for (Ticket ticket : ticketMap.values()){
                oos = new ObjectOutputStream(fos);
                oos.writeObject(ticket);
            }
            oos.close();
            fos.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Save single ticket in file
     * */
    public boolean saveSingelTicket(Ticket ticket){
        try(FileOutputStream fos = new FileOutputStream(file, true);ObjectOutputStream oos =new ObjectOutputStream(fos)){
            oos.writeObject(ticket);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * return Ticket list from file
     * @return concurrentTicketMap */
    public Map<Integer, Ticket> getTicketsFromFile(){
        ConcurrentHashMap<Integer, Ticket> concurrentTicketMap = new ConcurrentHashMap<>();

        if(file.length() == 0){
            return concurrentTicketMap;
        }
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            while (fis.available() > 0) {
                ois = new ObjectInputStream(fis);
                Ticket ticket = (Ticket) ois.readObject();
                concurrentTicketMap.put(ticket.getId(), ticket);
            }
            ois.close();
            fis.close();
            return concurrentTicketMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ConcurrentHashMap<>();
    }

    /**
     * To remove all tickest from the file
     * @return boolean
     */
    public boolean removeAllTickets(){
        try(FileOutputStream fos = new FileOutputStream(file)){
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
