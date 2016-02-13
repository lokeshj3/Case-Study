package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Created by root on 12/2/16.
 */
public class TestReportService {

	@BeforeClass
	public static void dummyTicketGeneration() throws ClassNotFoundException, IncompleteDataException, NotFoundException, IOException {
		TestTicketService.beforeAll();
		TestReportService testReportService = new TestReportService();
		testReportService.dummyTicket(TestTicketService.Data.service);
	}

	@Test
	public void testGetTicketCount() throws Exception {
		int count = TestTicketService.Data.reportService.getTicketCount();
		assertEquals(count, TestTicketService.Data.service.getTicketList().size());
	}

	@Test
	public void testGetTicketCountIncorrect() throws Exception {
		int count = TestTicketService.Data.reportService.getTicketCount();
		assertNotEquals(count, 5);
	}

	@Test
	public void testGetOldestTicket() throws Exception {

		Ticket oldestTicket = TestTicketService.Data.reportService.getOldestTicket();
		Ticket ticket = TestTicketService.Data.service.getTicketList().get(0);
		assertEquals(oldestTicket.toString(), ticket.toString());
		assertEquals(oldestTicket.getId(), ticket.getId());
		assertEquals(oldestTicket.getAgent(), ticket.getAgent());
		assertEquals(oldestTicket.getSubject(), ticket.getSubject());
		assertEquals(oldestTicket.getTags(), ticket.getTags());
		assertEquals(oldestTicket.getCreated(), ticket.getCreated());
		assertEquals(oldestTicket.getModified(), ticket.getModified());
	}

	@Test
	public void testTagTicketCount() throws IOException, ClassNotFoundException{
		Map<String, Integer> tagCount = TestTicketService.Data.reportService.tagTicketCount();

		tagCount.forEach((tag, count) -> {
			try {
				assertEquals(count.toString(), Integer.toString(
						TestTicketService.Data.service.searchTicket("tag", tag).size()));
			}
			catch (ClassNotFoundException | IOException e)
			{
				fail("Exception occurred " + e.getMessage());
			}
			}
		);
	}

	@Test
	public void testTicketOlderThanXdays() throws Exception {
		List<Ticket> olderTicketList = TestTicketService.Data.reportService.ticketOlderThanXdays(0);
		assertEquals(olderTicketList.size(), TestTicketService.Data.service.getTicketList().size());
	}

	protected List<Ticket> dummyTicket(TicketService ticketService) throws ClassNotFoundException, IncompleteDataException, NotFoundException, IOException {
		List<Ticket> ticketIdList = new ArrayList<>();
		ticketIdList.add(ticketService.createTicket("Test Subject 1","Evans",new HashSet<>(Arrays.asList("a","b","c","t"))));
		ticketIdList.add(ticketService.createTicket("Test Subject 2","Leroy",new HashSet<>(Arrays.asList("d","e","c","f"))));
		ticketIdList.add(ticketService.createTicket("Test Subject 3","Evans",new HashSet<>(Arrays.asList("a","e","b","c"))));
		ticketIdList.add(ticketService.createTicket("Test Subject 4","Virendra",new HashSet<>(Arrays.asList("x","a","e"))));
		return ticketIdList;
	}
}