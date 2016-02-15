package com.ticketmaster.service;

import com.ticketmaster.constants.SearchKeys;
import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.CustomLogger;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evans/Virendra on 8/2/16.
 * Methods distributed internally
 * TicketService class
 * This class is used for CRUD and basic ticket operations
 */
public class TicketService {

	/**
	 * createTicket method used to create the new ticket in the system
	 * @param subject <p>String<p/>
	 * @param agent <p>String</p>
	 * @param tags <p>Collection</p>
	 * @return <p>Ticket Object</p>
	 * @throws IOException
	 * @throws IncompleteDataException
	 * @throws ClassNotFoundException
     * @throws NotFoundException
     */
	public Ticket createTicket(String subject, String agent, Set<String> tags)
			throws IOException, IncompleteDataException, ClassNotFoundException, NotFoundException {

		CustomLogger.init(thisClass).info("start ticket creation with subject: " + subject + " agent: " + agent);

		if (subject == null || subject.isEmpty()) {
			throw new IncompleteDataException("subject is required");
		}
		if (agent == null || agent.isEmpty()) {
			throw new IncompleteDataException("agent is required");
		}

		ticket = new Ticket.TicketBuilder(subject, agent).withTags(tags).build();
		repository.saveTicket(ticket);
		CustomLogger.init(thisClass).info("ticket created. Id: " + ticket.getId());
		return ticket;
	}

	/**
	 * updateTicket method is used to update the ticket present in the system
	 * @param id <p>Integer</p> Ticket id
	 * @param newAgent <p>String</p> new agent name
	 * @param newTag <p>Collection</p> tag set
	 * @return <p>Ticket object</p>
	 * @throws NoUpdateException
	 * @throws NotFoundException
     */
    public Ticket updateTicket(Integer id, String newAgent, Set<String> newTag)
			throws NoUpdateException, NotFoundException, IOException, ClassNotFoundException {

		CustomLogger.init(thisClass).debug("updating ticket. Id:"+id+", to update: agent->"+newAgent+", tags->"+newTag);
		boolean flag = false;

		if (newAgent != null && !newAgent.isEmpty()) {
			flag = true;
		}
		if (newTag != null) {
			flag = true;
		}

		if (!flag) {
			CustomLogger.init(thisClass).error("throwing NoUpdateException from update");
			throw new NoUpdateException("Nothing to update");
		}

		return repository.updateTicket(id,newAgent,newTag);
	}

	/**
	 * getTicket method is used to get the ticket object
	 * @param id <p>Integer id</p>
	 * @return <p>Ticket object</p>
	 * @throws NotFoundException
     */
	public Ticket getTicket(Integer id) throws NotFoundException {
		return repository.getTicket(id);
	}

	/**
	 * deleteTicket method is used to delete the existing ticket of the system
	 * @param id <p>Integer id</p>
	 * @return <p>boolean value true if ticket is deleted</p>
	 * @throws NotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
     */
	public boolean deleteTicket(int id) throws NotFoundException, IOException, ClassNotFoundException {
		ticket = this.getTicket(id);
		repository.delete(id);
		String time = Instant.now().toString();
		CustomLogger.init().debug("ticket with id:"+id+" deleted from system on "+time);
		return true;
	}

	/**
	 * getTicketList method is used to get the list of tickets present in the system
	 * @return <p>List collection containing ticket objects</p>
	 * @throws IOException
	 * @throws ClassNotFoundException
     */
	public List<Ticket> getTicketList() throws IOException, ClassNotFoundException {
		repository.updatePool();
		return repository.getStreamValues()
				.sorted((obj1, obj2) -> (obj1.getModified() > obj2.getModified()) ? 1 : -1)
				.collect(Collectors.toList());
	}

	/**
	 * searchTicket method is used to search the ticket present in the system be either agent or by tag
	 * @param key <p>SearchKeys</p> type of search on ticket list
	 * @param value <p>String</p> Array of strings to be searched in collection
	 * @return <p>List collection of ticket objects</p>
	 * @throws IOException
	 * @throws ClassNotFoundException
     */
	public List<Ticket> searchTicket(SearchKeys key, String... value) throws IOException, ClassNotFoundException {
		List<Ticket> result = new ArrayList<>();
		if (value.length == 1){
			repository.updatePool();
			switch(key) {
				case AGENT:
					result = repository.searchTicketByAgent(value[0]);
					break;
				case TAGS:
					result = repository.searchTicketByTags(value[0]);
				break;
			}
		}
		CustomLogger.init().debug("result of "+key+" search:"+result);
		return result;
	}

	/**
	 * agentTicketCount method is used to get agent wise count for tickets
	 * @return <p>Map Collection of agent and ticket count</p>
     */
	public Map<String, Integer> agentTicketCount() {
		List<String> agentList = repository.getStreamValues().map(Ticket::getAgent).collect(Collectors.toList());
		Map<String, Integer> agentCountMap = new HashMap<>();
		int i;

		for (String agent : agentList) {
			i = repository.prepareCount(Collections.unmodifiableMap(agentCountMap), agent);
			agentCountMap.put(agent, i);
		}

		CustomLogger.init().debug("result of agent ticket count:"+agentCountMap);
		return agentCountMap;
	}

    public TicketService(){
        repository = TicketRepository.init();
    }

	/**
	 * setTicketList method
	 * @param values <p>Map collection</p>
     */
    public void setTicketList(Map<Integer, Ticket> values){
        TicketRepository.init().updateList(values);
    }

	/**
	 * initTags method is used to initialize temporary storage of tags
	 */
    public void initTags(){
        repository.initTagList();
    }

	/**
	 * initAgents method is used to initialize temporary storage of agent
	 */
    public void initAgents(){
        repository.initAgentList();
    }

	/**
	 * cleanTestData method is used to clean the test tickets during testings
	 * @param id <p>Integer</p>
	 * @throws IOException
	 * @throws NotFoundException
     */
    public void cleanTestData(Integer id )
            throws IOException, NotFoundException{
        repository.delete(id);
    }

    //properties
    TicketRepository repository;
    Ticket ticket ;
    Class thisClass = TicketService.class;
}
