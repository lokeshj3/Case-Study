package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.CustomLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
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

		if (subject == null) {
			throw new IncompleteDataException("subject is required");
		}
		if (agent == null) {
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
			repository.delete(id);
			return true;
		}
		else {
			throw new NotFoundException("Record with id:"+id +" does not exists");
		}
	}

	// get ticket list
	public List<Ticket> getTicketList() throws IOException, ClassNotFoundException {
		repository.updatePool();
		List<Ticket> list = repository.getList().values().stream().sorted((obj1, obj2) -> (obj1.getModified() < obj2.getModified()) ? 1 : -1).collect(Collectors.toList());
		if (!list.isEmpty()) {
			return list;
		}
		else return new LinkedList<>();
	}

	// search ticket count by agent / tags
	public List<Ticket> searchTicket(String key, String... value) throws IOException, ClassNotFoundException {
		List<Ticket> result = new LinkedList<>();
		repository.updatePool();
		if (value.length == 1) {
			String val = value[0];
			result = repository.getList().values()
					.stream()
					.filter(obj -> (key.equals("agent")) ? obj.getAgent().toLowerCase().equals(val.toLowerCase()) : obj.getTags().contains(val.toLowerCase()))
					.sorted((obj1, obj2) -> (obj1.getModified() < obj2.getModified()) ? 1 : -1)
					.collect(Collectors.toList());
		}

		return result;
	}

	/**
	 * 	REPORTS
	 */

	//get count of total tickets.
	public int getTicketCount() {
		return repository.getList().size();
	}

	//get oldest ticket from the system
	public Ticket getOldestTicket() {
		return repository.getList().values().stream()
				.min((Ticket obj1, Ticket obj2) -> (obj1.getModified() < (obj2.getModified())) ? 1 : -1).get();
	}

	//tag wise ticket count
	public Map<String, Integer> tagTicketCount() {
		List<Set> tagList = repository.getList().values().stream().map(Ticket::getTags).collect(Collectors.toList());
		HashMap tagCountMap = new HashMap();
		int i = 1;
		for (Set<String> s : tagList) {
			for (String st : s) {
				if (tagCountMap.containsKey(st)) {
					tagCountMap.put(st, i++);
				}
				else
					tagCountMap.put(st, 1);
			}
		}
		return tagCountMap;
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

	public List<Ticket> ticketOlderThanXdays(int days)
	{
		long time = LocalDateTime.now(ZoneId.of("UTC")).minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
		return repository.getList().values().stream().filter(ticket -> (ticket.getModified() <= time))
				.sorted((Ticket obj1, Ticket obj2) -> (obj1.getModified() < (obj2.getModified())) ? 1 : -1)
				.collect(Collectors.toList());
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
