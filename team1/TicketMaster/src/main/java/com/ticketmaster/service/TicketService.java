package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.CustomLogger;

import java.io.IOException;
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

		ticket = this.getSingleTicket(id);
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
	public Ticket getSingleTicket(int id) throws NotFoundException {
		return repository.getTicket(id);
	}

	// delete ticket
	public boolean deleteTicket(int id) throws NotFoundException, IOException, ClassNotFoundException {
		ticket = this.getSingleTicket(id);
		if (ticket != null) {
			if(repository.delete(id));
			return true;
		}
		return false;
	}

	// get ticket list
	public List<Ticket> getTicketList() throws IOException, ClassNotFoundException {
		repository.updatePool();
		List<Ticket> list = repository.getStreamValues()
				.sorted((obj1, obj2) -> (obj1.getModified() > obj2.getModified()) ? 1 : -1)
				.collect(Collectors.toList());
		if (!list.isEmpty()) {
			return list;
		}
		return new LinkedList<>();
	}

	// search ticket count by agent / tags
	public List<Ticket> searchTicket(String key, String... value) throws IOException, ClassNotFoundException {
		repository.updatePool();
		if (value.length == 1) {
			String val = value[0];
			return repository.getStreamValues()
					.filter(obj -> (key.equals("agent")) ? obj.getAgent().toLowerCase().equals(val.toLowerCase()) : obj.getTags().contains(val.toLowerCase()))
					.sorted((obj1, obj2) -> (obj1.getModified() < obj2.getModified()) ? 1 : -1)
					.collect(Collectors.toList());
		}
		return new LinkedList<>();
	}
/*

	*/
/**
	 * 	REPORTS
	 *//*


	//get count of total tickets.
	public int getTicketCount() {
		return repository.getList().size();
	}

	//get oldest ticket from the system
	public Ticket getOldestTicket() {
		return repository.getStreamValues()
				.min((Ticket obj1, Ticket obj2) -> (obj1.getModified() < (obj2.getModified())) ? 1 : -1)
				.get();
	}

	//tag wise ticket count
	public Map<String, Integer> tagTicketCount() {
		List<Set> tagList = repository.getStreamValues().map(Ticket::getTags).collect(Collectors.toList());
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

	public List<Ticket> ticketOlderThanXdays(int days)
	{
		long time = LocalDateTime.now(ZoneId.of("UTC")).minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
		return repository.getStreamValues().filter(ticket -> (ticket.getModified() <= time))
				.sorted((Ticket obj1, Ticket obj2) -> (obj1.getModified() < (obj2.getModified())) ? 1 : -1)
				.collect(Collectors.toList());
	}
*/

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

    public void cleanTestData()
            throws IOException, NotFoundException{
        repository.delete(--Ticket.masterId);
    }

    //properties
    TicketRepository repository;
    Ticket ticket ;
    Class classz = TicketService.class;

}
