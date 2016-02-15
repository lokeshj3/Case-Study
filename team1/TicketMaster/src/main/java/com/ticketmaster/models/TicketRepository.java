package com.ticketmaster.models;

import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        util = new SerializerUtil();
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
    public boolean delete(Integer id) throws IOException, NotFoundException{

        ticketList.remove(id);

        //clean file contents
        util.emptyObjectFile();
        //write complete data again to file
        util.writeToFile(getList());
        return true;
    }

    /**
     * createTicket method used to create the new ticket in the systems
     * @param ticket <p>Ticket</p>
     * @return <p>boolean</p>
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NotFoundException
     */
    public boolean saveTicket(Ticket ticket)
            throws IOException, ClassNotFoundException, NotFoundException {

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
        util.writeProperty("id",Ticket.masterId.toString());
        return ticket != null;

    }

    /**
     * updatePool method is used to update the ticket List map
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void updatePool()
            throws ClassNotFoundException, IOException{
        SerializerUtil util = new SerializerUtil();
        Map<Integer,Ticket> temp = (Map<Integer,Ticket>) util.readFromFile();
        this.updateList(temp);
    }

    /**
     * updatePool method
     * @param agent <p>String</p>
     */
    public void addAgent(String agent){
        agentList.add(agent);
    }

    /**
     * updateList method
     * @param object <p>Map Collection</p>
     */
    public void updateList(Map<Integer, Ticket> object) {
        ticketList.putAll(object);
    }

    /**
     * addTags method adds the tag in collection
     * @param tag <p>String</p>
     */
    public void addTags(Set<String> tag){
        tagList.addAll(tag);
    }

    /**
     * initTagList method is used to initialize the temporary set of tag
     */
    public void initTagList(){
        if (ticketList != null){
            ticketList.values().stream().forEach(e-> tagList.addAll( e.getTags() ) );
        }
    }

    /**
     * initAgentList method is used to initialize the temporary set of agent
     */
    public void initAgentList(){
        if (ticketList != null)
            ticketList.values().stream().forEach(e-> agentList.add(e.getAgent()) );
    }

    /**
     * getStreamValues method is used to get the stream of ticket list
     * @return <p>Stream</p> Stream of ticket objects
     */
    public Stream<Ticket> getStreamValues(){
        return getList().values().stream();
    }

    /**
     * updateTicket method is used to update the ticket
     * @param id <p>Integer</p> id of ticket
     * @param agent <p>String</p> agent name
     * @param tag <p>Set</p> Set of tags
     * @return <p>Ticket object</p>
     * @throws IOException
     * @throws NotFoundException
     * @throws ClassNotFoundException
     */
    public Ticket updateTicket(Integer id, String agent, Set<String> tag)
            throws IOException, NotFoundException, ClassNotFoundException {

        Ticket ticket = getTicket(id);

        if (agent!= null) ticket.setAgent(agent);
        ticket.setTags(tag);

        ticket.update();

        Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(ticket.getId(), ticket);

        util.writeToFile(tempMap);
        addAgent(ticket.getAgent());
        addTags(ticket.getTags());

        return ticket;
    }

    /**
     * prepareCount method decided the count for the specified map object
     * @param map <p>Map</p> Collection of String and Integer
     * @param str <p>String</p>
     * @return <p>int</p> count
     */
    public int prepareCount(Map<String, Integer> map, String str){
        return map.containsKey(str) ? map.get(str) +1 : 1;
    }

    /**
     * searchTicketByTags method is used to search the tickets by provided tag name
     * @param value <p>String</p> tag name to be searched
     * @return <p>List collection of ticket objects</p>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<Ticket> searchTicketByTags(String value)
            throws IOException, ClassNotFoundException {
        return getStreamValues()
                .filter(obj -> obj.getTags().contains(value.toLowerCase()) )
                .sorted((obj1, obj2) -> (obj1.getModified() > obj2.getModified()) ? 1 : -1)
                .collect(Collectors.toList());

    }

    /**
     * searchTicketByAgent method is used to seach the tickets with provided agent name
     * @param value <p>String</p> tag name to be searched
     * @return <p>List collection of ticket objects</p>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<Ticket> searchTicketByAgent(String value)
            throws IOException, ClassNotFoundException {

        return getStreamValues()
                .filter(obj ->  obj.getAgent().toLowerCase().equals(value.toLowerCase()) )
                .sorted((obj1, obj2) -> (obj1.getModified() > obj2.getModified()) ? 1 : -1)
                .collect(Collectors.toList());

    }

    //properties
    private static TicketRepository _instance; //holds the instance of TicketRepository class
    private Map<Integer, Ticket> ticketList ; //holds tickets. acts as temporary storage
    private Set<String> agentList; // holds unique agents data
    private Set<String> tagList; //holds unique tags data
    private SerializerUtil util;

}
