// LB comment : Kindly use Doc Type comments instead of single liner comment above each functions
package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.AppUtil;
import com.ticketmaster.utils.CustomLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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

	// create ticket
	public Ticket createTicket(String subject, String agent, Set tags)
			throws IOException, IncompleteDataException, ClassNotFoundException, NotFoundException {

		CustomLogger.init(classz).info("start ticket creation with subject: " + subject + " agent: " + agent);

		// LB comment : You could have clubbed the 2 checks below into 1 as you are throwing the same type of Exception using AND '&&' condition .
		// If you are throwing different exception, only then I would break my Checks into 2.
		if (subject == null || subject.isEmpty()) {
			throw new IncompleteDataException("subject is required");
		}
		if (agent == null || agent.isEmpty()) {
			throw new IncompleteDataException("agent is required");
		}

		ticket = new Ticket.TicketBuilder(subject, agent).withTags(tags).build();
		repository.saveTicket(ticket);
		CustomLogger.init(classz).info("ticket created. Id: " + ticket.getId());
		return ticket;
	}

	// update ticket
	public Ticket updateTicket(Integer id, String newAgent, Set<String> newTag)
			throws NoUpdateException, NotFoundException {

		CustomLogger.init(classz).debug("updating ticket. Id:"+id+", to update: agent->"+newAgent+", tags->"+newTag);

		ticket = this.getTicket(id);

		boolean flag = false;
		// LB comment : there is no need to check length of a string here, instead isEmpty() check is more appropriate than length.
		if (newAgent != null && newAgent.length() > 0) {
			// LB comment : use either Repo or Service layer to interact with Model directly.
			ticket.setAgent(newAgent);
			flag = true;
		}
		if (newTag != null) {
			// LB comment : use either Repo or Service layer to interact with Model directly.
			ticket.setTags(newTag);
			flag = true;
		}

		if (!flag) {
			CustomLogger.init(classz).error("throwing NoUpdateException from update");
			throw new NoUpdateException("Nothing to update");
		}
		return ticket;
	}

	// get ticket details
	public Ticket getTicket(Integer id) throws NotFoundException {
		return repository.getTicket(id);
	}

	// delete ticket
	public boolean deleteTicket(int id) throws NotFoundException, IOException, ClassNotFoundException {
		ticket = this.getTicket(id);
		repository.delete(id);
		String time = LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).now().toString();
		CustomLogger.init().debug("ticket with id:"+id+" deleted from system on "+time);
		return true;
	}

	// get ticket list
	public List<Ticket> getTicketList() throws IOException, ClassNotFoundException {
		repository.updatePool();
		List<Ticket> list = repository.getStreamValues()
				.sorted((obj1, obj2) -> (obj1.getModified() > obj2.getModified()) ? 1 : -1)
				.collect(Collectors.toList());
		return list;
	}

	// search ticket count by agent / tags
	public List<Ticket> searchTicket(String key, String... value) throws IOException, ClassNotFoundException {
		// LB comment : avoid declaring unnecessary variables.
		// this variable could be avoided as it takes unnecessary memory.
		List<Ticket> result = new LinkedList<>();
		repository.updatePool();
		if (value.length == 1) {
			String val = value[0];
			// LB comment : You can move this logic to Repo as well to avoid coupling.
			result = repository.getStreamValues()
					.filter(obj -> (key.equals("agent")) ? obj.getAgent().toLowerCase().equals(val.toLowerCase()) : obj.getTags().contains(val.toLowerCase()))
					.sorted((obj1, obj2) -> (obj1.getModified() > obj2.getModified()) ? 1 : -1)
					.collect(Collectors.toList());
		}
		CustomLogger.init().debug("result of "+key+" search:"+result);
		return result;
	}

	//tag wise ticket count
	public Map<String, Integer> agentTicketCount() {
		// LB comment : You can move this logic to Repo as well to avoid coupling.
		List<String> agentList = repository.getStreamValues().map(Ticket::getAgent).collect(Collectors.toList());
		Map<String, Integer> agentCountMap = new HashMap<>();
		int i;

		for (String agent : agentList) {
			// LB comment : please check AppUtil class for my comments.
			i = AppUtil.prepareCount(Collections.unmodifiableMap(agentCountMap), agent);
			agentCountMap.put(agent, i);
		}

		CustomLogger.init().debug("result of agent ticket count:"+agentCountMap);
		return agentCountMap;
	}

    public TicketService(){
        repository = TicketRepository.init();
    }

    public void setTicketList(Map<Integer, Ticket> values){
        TicketRepository.init().updateList(values);
    }

    public void initTags(){
        repository.initTagList();
    }

    public void initAgents(){
        repository.initAgentList();
    }

    public void cleanTestData(Integer id )
            throws IOException, NotFoundException{
        repository.delete(id);
    }

	// LB comment : Class instance variables should be declared at the start of the Class.
    //properties
    TicketRepository repository;
    Ticket ticket ;
    Class classz = TicketService.class;
}
