package com.inin;

import com.inin.exception.TicketNotFoundException;
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
        TicketService ticketService = getTicketServiceInstance();
        ticketService.create(null,"Agent1",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithNullAgent(){
        TicketService ticketService = getTicketServiceInstance();
        ticketService.create("Test Subject",null,new HashSet<>());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithEmptySubject(){
        TicketService ticketService = getTicketServiceInstance();
        ticketService.create("","Agent1",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithEmptyAgent(){
        TicketService ticketService = getTicketServiceInstance();
        ticketService.create("Test Subject","",new HashSet<>());
    }

    @Test
    public void testCreateTicketWithNullTags()
    {
        TicketService ticketService = getTicketServiceInstance();
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
        TicketService ticketService = getTicketServiceInstance();
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertEquals(tags,ticket.getTags());
        ticketService.delete(ticket.getId());
    }

    @Test
    public void testUpdateTicket(){
        TicketService ticketService = getTicketServiceInstance();
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        Set<String> newTags = new HashSet<>(Arrays.asList("NewTag1","NewTag2"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        Ticket ticket = ticketService.update(1, "Agent Vinod", newTags);
        Assert.assertEquals("Agent Vinod",ticket.getAgent());
        Assert.assertEquals(newTags,ticket.getTags());
        ticketService.delete(id);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTicketWithNullAgent(){
        TicketService ticketService = getTicketServiceInstance();
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        ticketService.update(id, null ,null);
        ticketService.delete(id);
    }

    @Test(expected = TicketNotFoundException.class)
    public void testUpdateTicketWithNullId(){
        TicketService ticketService = getTicketServiceInstance();
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        ticketService.update(-1, "agent name",tags);
    }

    @Test
    public void testUpdateTicketWithNullTags(){
        TicketService ticketService = getTicketServiceInstance();
        Set<String> tags = new HashSet<>(Arrays.asList("Tag1","Tag2"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        Ticket ticket = ticketService.update(id, "AgentVinod",null);
        Assert.assertEquals(ticket.getTags(), tags );
        ticketService.delete(id);
    }

    @Test
    public void testDeleteTicketWithValidId(){
        TicketService ticketService = getTicketServiceInstance();
        int id = ticketService.create("Test Subject","Agent1",null);
        Assert.assertTrue(ticketService.delete(id));
    }
    @Test
    public void testDeleteTicketWithInValidId(){
        TicketService ticketService = getTicketServiceInstance();
        Assert.assertFalse(ticketService.delete(-1));
    }

    @Test
    public void testGetTicketWithValidId(){
        TicketService ticketService = getTicketServiceInstance();
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        int id = ticketService.create("Test Subject for Get Ticket","Agent1",tags);
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject for Get Ticket",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertEquals(tags,ticket.getTags());
        ticketService.delete(ticket.getId());
    }

    @Test(expected = TicketNotFoundException.class)
    public void testGetTicketWithInValidId(){
        TicketService ticketService = getTicketServiceInstance();
        ticketService.ticket(-1);

    }

    @Test
    public void testTicketListWithData(){
        TicketService ticketService = getTicketServiceInstance();
        List<Integer> ticketIdList = generateDummyTicket(ticketService);
        List<Ticket> ticketList = ticketService.tickets();
        Assert.assertEquals(4,ticketList.size());
        deleteDummyTicket(ticketIdList,ticketService);
    }

    @Test
    public void testTicketListWithEmptyData(){
        TicketService ticketService = getTicketServiceInstance();
        List<Ticket> ticketList = ticketService.tickets();
        Assert.assertEquals(0,ticketList.size());
    }

    /**
     * Generate the dummy Ticket
     * @param ticketService
     * @return List<Integer>
     */
    private  List<Integer> generateDummyTicket(TicketService ticketService){
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
    private void deleteDummyTicket(List<Integer> ticketIdList,TicketService ticketService){
        ticketIdList.forEach(id -> ticketService.delete(id));

    }

    private TicketService getTicketServiceInstance()
    {
        return new TicketServiceImpl();
    }
}
