package com.ticketmaster.service;

import com.ticketmaster.models.Ticket;
import com.ticketmaster.models.TicketRepository;
import com.ticketmaster.utils.AppUtil;

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
		for (Set<String> set : tagList) {
			for (String tag : set) {
				i = AppUtil.prepareCount(Collections.unmodifiableMap(tagCountMap), tag);
				tagCountMap.put(tag, i);
			}
		}
		return tagCountMap;
	}

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
