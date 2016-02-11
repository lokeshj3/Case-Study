package com.ticketmaster.models;

import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.util.*;

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
	private TicketRepository() {

	}

	/**
	 * init method is used to get the TicketRepository object
	 * used to initialize the TicketRepository class
	 *
	 * @return
	 */
	public static TicketRepository init() {
		if (!(_instance instanceof TicketRepository))
			_instance = new TicketRepository();
		return _instance;
	}

	/**
	 * getTicket method is used to get the ticket object from the system based on the supplied ticket id
	 *
	 * @param id Integer
	 * @return Ticket object
	 */
	public Ticket getTicket(Integer id) throws NotFoundException {
		if (!ticketList.containsKey(id)) {
			throw new NotFoundException("details for id: " + id + " not found");
		}
		return ticketList.get(id);
	}

	/**
	 * getList method is used to retrieve the list of ticket present in the system
	 *
	 * @return Map object
	 */
	public Map<Integer, Ticket> getList() {
		return Collections.unmodifiableMap(ticketList);
	}

	/**
	 * deleteTicket method is used to remove the ticket object from system
	 *
	 * @param id Integer object
	 * @return Ticket object
	 */
	public boolean delete(Integer id) throws IOException, ClassNotFoundException {
		this.updateList((Map<Integer, Ticket>) SerializerUtil.readFromFile());
		SerializerUtil.emptyObjectFile();
		SerializerUtil.writeToFile(this.getList());
		ticketList.remove(id);
		return true;
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
		util.writeProperty("id", new Integer(ticket.getMasterId()).toString());
		return getTicket(ticket.getId()) != null;
	}

	public void updatePool()
			throws ClassNotFoundException, IOException {
		SerializerUtil util = new SerializerUtil();
		Map<Integer, Ticket> temp = (Map<Integer, Ticket>) util.readFromFile();
		this.updateList(temp);
	}

	public void addAgent(String agent) {
		agentList.add(agent);
	}

	public void updateList(Map<Integer, Ticket> object) {
		ticketList.putAll(object);

	}

	public void addTags(Set<String> tag) {
		tagList.addAll(tag);
	}

	//properties
	private static TicketRepository _instance; //holds the instance of TicketRepository class
	private Map<Integer, Ticket> ticketList; //holds tickets. acts as temporary storage
	private Set<String> agentList; // holds unique agents data
	private Set<String> tagList; //holds unique tags data

}
