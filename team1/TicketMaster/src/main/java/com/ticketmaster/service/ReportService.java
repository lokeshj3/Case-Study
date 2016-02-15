package com.ticketmaster.service;

import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ReportService class
 * used to work with reporting section
 * Created by Evans on 12/2/16.
 */
public class ReportService {

	public ReportService() {
		repository = TicketRepository.init();
	}

	/**
	 * getTicketCount method is used to get the count of tickets present in the system
	 * @return int count
	 */
	public int getTicketCount() {
		return repository.getList().size();
	}

	/**
	 * getOldestTicket method is used to collect the oldest ticket present in the system
	 * @return <p>Ticket object</p>
	 */
	public Ticket getOldestTicket() {
		return repository.getStreamValues()
				.min((Ticket obj1, Ticket obj2) -> (obj1.getModified() > (obj2.getModified())) ? 1 : -1)
				.get();
	}

	/**
	 * tagTicketCount method is used to collect the ticket count for each tags present in the system.s
	 * @return <p>Map of collection with tag name and count of tickets</p>
	 */
	public Map<String, Integer> tagTicketCount() {
		List<Set> tagList = repository.getStreamValues().map(Ticket::getTags).collect(Collectors.toList());
		Map<String, Integer> tagCountMap = new HashMap();
		int i;
		for (Set<String> set : tagList) {
			for (String tag : set) {
				i = repository.prepareCount(Collections.unmodifiableMap(tagCountMap), tag);
				tagCountMap.put(tag, i);
			}
		}
		return tagCountMap;
	}

	/**
	 * ticketOlderThanXdays method is used to get the tickets which are older than the specified days
	 * @param days <p>int</p> days
	 * @return <p>List collection of ticket objects</p>
	 */
	public List<Ticket> ticketOlderThanXdays(int days) {
		long time = LocalDateTime.now(ZoneId.of("UTC")).minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
		return repository.getStreamValues()
				.filter(ticket -> (ticket.getModified() <= time))
				.sorted((Ticket obj1, Ticket obj2) -> (obj1.getModified() < (obj2.getModified())) ? 1 : -1)
				.collect(Collectors.toList());
	}

	//properties
	TicketRepository repository;
}
