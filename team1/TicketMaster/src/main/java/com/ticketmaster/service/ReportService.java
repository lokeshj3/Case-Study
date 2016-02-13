package com.ticketmaster.service;

import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by root on 12/2/16.
 */
public class ReportService {

	/**
	 * REPORTS
	 */
	TicketRepository repository;

	public ReportService() {
		repository = TicketRepository.init();
	}

	//get count of total tickets.
	public int getTicketCount() {
		return repository.getList().size();
	}

	//get oldest ticket from the system
	public Ticket getOldestTicket() {
		return repository.getStreamValues()
				.min((Ticket obj1, Ticket obj2) -> (obj1.getModified() > (obj2.getModified())) ? 1 : -1)
				.get();
	}

	//tag wise ticket count
	public Map<String, Integer> tagTicketCount() {
		List<Set> tagList = repository.getStreamValues().map(Ticket::getTags).collect(Collectors.toList());
		Map<String, Integer> tagCountMap = new HashMap();
		int i;
		for (Set<String> s : tagList) {
			for (String st : s) {
				if (tagCountMap.containsKey(st)) {
					i = tagCountMap.get(st) +1 ;
					tagCountMap.put(st, i);
				}
				else
					tagCountMap.put(st, 1);
			}
		}
		return tagCountMap;
	}

	public List<Ticket> ticketOlderThanXdays(int days) {
		long time = LocalDateTime.now(ZoneId.of("UTC")).minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
		return repository.getStreamValues().filter(ticket -> (ticket.getModified() <= time))
				.sorted((Ticket obj1, Ticket obj2) -> (obj1.getModified() > (obj2.getModified())) ? 1 : -1)
				.collect(Collectors.toList());
	}
}
