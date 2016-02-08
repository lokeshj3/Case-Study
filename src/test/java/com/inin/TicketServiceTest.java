package com.inin;

import com.inin.model.Ticket;
import com.inin.service.TicketService;
import com.inin.service.TicketServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by root on 8/2/16.
 */
public class TicketServiceTest {


    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithNullSubject(){
        TicketService ticketService = getTicketServiceinstance();
        ticketService.create(null,"Agent1",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithNullAgent(){
        TicketService ticketService = getTicketServiceinstance();
        ticketService.create("Test Subject",null,new HashSet<>());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithEmptySubject(){
        TicketService ticketService = getTicketServiceinstance();
        ticketService.create("","Agent1",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithEmptyAgent(){
        TicketService ticketService = getTicketServiceinstance();
        ticketService.create("Test Subject","",new HashSet<>());
    }

    @Test
    public void testCreateTicketWithNullTags()
    {
        TicketService ticketService = getTicketServiceinstance();
        int id = ticketService.create("Test Subject","Agent1",null);
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertNull(ticket.getTags());
        ticketService.delete(id);
    }

    @Test
    public void testCreateTicket()
    {
        TicketService ticketService =getTicketServiceinstance();
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertEquals(tags,ticket.getTags());
        ticketService.delete(ticket.getId());
    }


    @Test
    public void testDeleteTicketWithValidId(){
        TicketService ticketService =getTicketServiceinstance();
        int id = ticketService.create("Test Subject","Agent1",null);
        Assert.assertTrue(ticketService.delete(id));
    }
    @Test
    public void testDeleteTicketWithInValidId(){
        TicketService ticketService =getTicketServiceinstance();
        Assert.assertFalse(ticketService.delete(-1));
    }
    /**
     * Generate the dummy Ticket
     * @param ticketService
     * @return List<Integer>
     */
    private static synchronized List<Integer> generateDummyTicket(TicketService ticketService){
        List<Integer> ticketIdList = new ArrayList<>();
        ticketIdList.add(ticketService.create("Test Subject1","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3"))));
        ticketIdList.add(ticketService.create("Test Subject2","Agent2",new HashSet<>(Arrays.asList("tag4","tag1","tag2"))));
        ticketIdList.add(ticketService.create("Test Subject3","Agent2",new HashSet<>(Arrays.asList("tag1","tag4","tag3"))));
        ticketIdList.add(ticketService.create("Test Subject4","Agent1",new HashSet<>(Arrays.asList("tag2","tag5"))));
        return ticketIdList;
    }

    /**
     * Delete all ticket from passed ticket list id
     * @param ticketIdList
     * @param ticketService
     */
    private static synchronized void deleteDummyTicket(List<Integer> ticketIdList,TicketService ticketService){
        ticketIdList.forEach(id -> ticketService.delete(id));

    }

    private TicketService getTicketServiceinstance()
    {
        return new TicketServiceImpl();
    }
}
