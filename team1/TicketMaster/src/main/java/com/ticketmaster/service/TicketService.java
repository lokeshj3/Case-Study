package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.CustomLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {

//	public TicketService() {
//
//	}

	// create ticket
	public Ticket createTicket(String subject, String agent, Set tags)
			throws IOException, IncompleteDataException, ClassNotFoundException, NotFoundException {

		CustomLogger.init(classz).info("start ticket creation with subject: " + subject + " agent: " + agent);

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

		ticket = this.getTicket(id);
		if (ticket != null) {
			boolean flag = false;
			if (newAgent != null && newAgent.length() > 0) {
				ticket.setAgent(newAgent);
				flag = true;
			}
			if (newTag != null) {
				ticket.setTags(newTag);
				flag = true;
			}

			if (!flag) {
				throw new NoUpdateException("Nothing to update");
			}
			return ticket;
		}
		else return new Ticket();
	}

	// get ticket details
	public Ticket getTicket(Integer id) throws NotFoundException {
		return repository.getTicket(id);
	}

	// delete ticket
	public boolean deleteTicket(int id) throws NotFoundException, IOException, ClassNotFoundException {
		ticket = this.getTicket(id);
		if (ticket != null) {
			if(repository.delete(id));
			return true;
		}
		else {
			throw new NotFoundException("Record with id:"+id +" does not exists");
		}
	}

	// get ticket list
	public List<Ticket> getTicketList() throws IOException, ClassNotFoundException {
		repository.updatePool();
		List<Ticket> list = repository.getStreamValues()
				.sorted((obj1, obj2) -> (obj1.getModified() < obj2.getModified()) ? 1 : -1)
				.collect(Collectors.toList());
		if (!list.isEmpty()) {
			return list;
		}
		return new LinkedList<>();
	}

	// search ticket count by agent / tags
	public List<Ticket> searchTicket(String key, String... value) throws IOException, ClassNotFoundException {
		List<Ticket> result = new LinkedList<>();
		repository.updatePool();
		if (value.length == 1) {
			String val = value[0];
			result = repository.getStreamValues()
					.filter(obj -> (key.equals("agent")) ? obj.getAgent().toLowerCase().equals(val.toLowerCase()) : obj.getTags().contains(val.toLowerCase()))
					.sorted((obj1, obj2) -> (obj1.getModified() < obj2.getModified()) ? 1 : -1)
					.collect(Collectors.toList());
		}
		return result;
	}

	//tag wise ticket count
	public Map<String, Integer> agentTicketCount() {

		List<String> agentList = repository.getList().values().stream().map(Ticket::getAgent).collect(Collectors.toList());
		Map<String, Integer> agentCountMap = new HashMap<>();
		int i;

		for (String s : agentList) {
			if (agentCountMap.containsKey(s)) {
				i = agentCountMap.get(s)+1;
				agentCountMap.put(s, i);
			}
			else
				agentCountMap.put(s, 1);
		}

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

    //properties
    TicketRepository repository;
    Ticket ticket ;
    Class classz = TicketService.class;

}
