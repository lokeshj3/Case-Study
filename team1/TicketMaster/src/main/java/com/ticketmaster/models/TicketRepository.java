package com.ticketmaster.models;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * TicketRepository class
 * This class is used to hold the ticket related data collected from file system.
 * Created by Virendra on 10/2/16.
 */
public class TicketRepository {
    /**
     * private constructor
     * accessible only via init method
     */
    private TicketRepository(){

    }
    /**
     * init method is used to get the TicketRepository object
     * used to initialize the TicketRepository class
     * @return
     */
    public static TicketRepository init(){
        if (!(_instance instanceof TicketRepository))
            _instance = new TicketRepository();
        return _instance;
    }
    /**
     * getTicket method is used to get the ticket object from the system based on the supplied ticket id
     * @param id Integer
     * @return Ticket object
     */
    public Ticket getTicket(Integer id){
        if (!ticketList.containsKey(id)){
            return null;
        }
        return ticketList.get(id);
    }

    /**
     * getList method is used to retrieve the list of ticket present in the system
     * @return Map object
     */
    public Map<Integer, Ticket> getList(){
        return Collections.unmodifiableMap(ticketList);
    }

    /**
     * deleteTicket method is used to remove the ticket object from system
     * @param id Integer object
     * @return Ticket object
     */
    public Ticket deleteTicket(Integer id){

        if (!ticketList.containsKey(id)){
            return null;
        }
        return ticketList.remove(id);

    }


    //properties
    private static TicketRepository _instance; //holds the instance of TicketRepository class
    private Map<Integer, Ticket> ticketList ; //holds tickets. acts as temporary storage
    private Set<String> agentList; // holds unique agents data
    private Set<String> tagList; //holds unique tags data

}
