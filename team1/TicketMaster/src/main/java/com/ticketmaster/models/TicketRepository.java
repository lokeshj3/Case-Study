package com.ticketmaster.models;

import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

        ticketList = new ConcurrentHashMap<>();

        if (agentList == null ){
            agentList = new HashSet<>();
        }
        util = new SerializerUtil();

        if (tagList == null){
            tagList = new HashSet<>();

        }
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
    public Ticket getTicket(Integer id) throws NotFoundException{
        if (!ticketList.containsKey(id)){
            throw new NotFoundException("ticket not found");
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
    public Ticket delete(Integer id) throws IOException, NotFoundException{

        if (!ticketList.containsKey(id)){
            throw new NotFoundException("ticket not found");
        }
        Ticket ticket = ticketList.remove(id);

        if (ticket != null){
            //clean file contents
            util.emptyObjectFile();
            //write complete data again to file
            util.writeToFile(getList());
        }
        return ticket;

    }


    public boolean saveTicket(Ticket ticket)
            throws IOException, ClassNotFoundException, NotFoundException {


        SerializerUtil util = new SerializerUtil();

        ticket.save(util);

        Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(ticket.getId(), ticket);

        //add ticket in file
        util.writeToFile(tempMap);
        //read new entries
        updatePool();
        addAgent(ticket.getAgent());
        addTags(ticket.getTags());
        //update id in file
        util.writeProperty("id",ticket.getId().toString());
        return ticket != null;

    }

    public void updatePool()
            throws ClassNotFoundException, IOException{
        SerializerUtil util = new SerializerUtil();
        Map<Integer,Ticket> temp = (Map<Integer,Ticket>) util.readFromFile();
        this.updateList(temp);
    }
    public void addAgent(String agent){
        agentList.add(agent);
    }

    public void updateList(Map<Integer, Ticket> object) {
        ticketList.putAll(object);

    }


    public void addTags(Set<String> tag){
        tagList.addAll(tag);
    }

    public void initTagList(){

        if (ticketList != null){
            ticketList.values().stream().forEach(e-> tagList.addAll( e.getTags() ) );
        }
    }

    public void initAgentList(){

        if (ticketList != null){
            ticketList.values().stream().forEach(e-> agentList.add(e.getAgent()) );
        }

    }




    //properties
    private static TicketRepository _instance; //holds the instance of TicketRepository class
    private Map<Integer, Ticket> ticketList ; //holds tickets. acts as temporary storage
    private Set<String> agentList; // holds unique agents data
    private Set<String> tagList; //holds unique tags data
    private SerializerUtil util;

}
