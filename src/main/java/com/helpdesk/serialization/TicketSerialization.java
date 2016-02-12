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
       file = Util.createFile(path, fileName);
    }


    /**
     * serialize tickets
     * @param ticketMap
     * */
    public boolean saveTicketsInFile(Map<Integer, Ticket> ticketMap, boolean append){
        //try(FileOutputStream fos = new FileOutputStream(file, append); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(file, true);

            for (Ticket ticket : ticketMap.values()){
                oos = new ObjectOutputStream(fos);
                oos.writeObject(ticket);
            }
            oos.close();
            fos.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Save single ticket in file
     * */
    public boolean saveSingelTicket(Ticket ticket){
        /*FileOutputStream fos = null;
        ObjectOutputStream oos = null;*/
        try(FileOutputStream fos = new FileOutputStream(file, true);ObjectOutputStream oos =new ObjectOutputStream(fos);){
            /*fos = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fos);*/
            oos.writeObject(ticket);
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(file);
            while(fis.available() > 0){
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
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return new ConcurrentHashMap<>();
    }

}
