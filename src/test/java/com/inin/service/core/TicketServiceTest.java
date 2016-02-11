package com.inin.service.core;

import com.inin.exception.TicketNotFoundException;
import com.inin.factory.TicketFactory;
import com.inin.model.Ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by root on 11/2/16.
 */
public class TicketServiceTest {

    private TicketService ticketService;

    @Before
    public void initializeService(){
        ticketService = TicketFactory.newTicketServiceInstance();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithNullSubject(){
        ticketService.create(null,"Agent1",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithBlankSubject(){
        ticketService.create("","Agent1",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithNullAgent(){
        ticketService.create("Test Subject",null,new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithBlankAgent(){
        ticketService.create("Test Subject","",new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketWithEmptyAgent(){
        ticketService.create("Test Subject","",new HashSet<>());
    }

    @Test
    public void testCreateTicketWithNullTags()
    {
        int id = ticketService.create("Test Subject","Agent1", null);
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertEquals(new HashSet<>(), ticket.getTags());
        ticketService.delete(id);
    }

    @Test
    public void testCreateTicket()
    {
        int id = ticketService.create("Test Subject","Agent1",this.getDummyTags());
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertEquals(this.getDummyTags(),ticket.getTags());
        ticketService.delete(id);
    }

    @Test
    public void testUpdateTicket(){
        Set<String> tags = this.getDummyTags();
        Set<String> newTags = new HashSet<>(Arrays.asList("NewTag1","NewTag2"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        Ticket ticket = ticketService.update(id, "Agent Vinod", newTags);
        Assert.assertEquals("Agent Vinod",ticket.getAgent());
        Assert.assertEquals(newTags,ticket.getTags());
        ticketService.delete(id);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTicketWithNullAgent(){
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        try
        {
            ticketService.update(id, null ,this.getDummyTags());
        }finally {

            ticketService.delete(id);
        }
    }

    @Test(expected = TicketNotFoundException.class)
    public void testUpdateTicketWithNullId(){
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        ticketService.update(-1, "agent name",tags);
    }

    @Test
    public void testUpdateTicketWithNullTags(){
        Set<String> tags = new HashSet<>(Arrays.asList("Tag1","Tag2"));
        int id = ticketService.create("Test Subject","Agent1",tags);
        Ticket ticket = ticketService.update(id, "AgentVinod",null);
        Assert.assertEquals(ticket.getTags(), tags );
        ticketService.delete(id);
    }

    @Test
    public void testDeleteTicketWithValidId(){
        int id = ticketService.create("Test Subject","Agent1",null);
        Assert.assertTrue(ticketService.delete(id));
    }

    @Test(expected = TicketNotFoundException.class)
    public void testDeleteTicketWithInValidId(){
        Assert.assertFalse(ticketService.delete(-1));
    }

    @Test(expected = TicketNotFoundException.class)
    public void testDeleteTicketWithInBlankId(){
        Assert.assertFalse(ticketService.delete(0));
    }


    @Test
    public void testGetTicketWithValidId(){
        Set<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        int id = ticketService.create("Test Subject for Get Ticket","Agent1",tags);
        Ticket ticket = ticketService.ticket(id);
        Assert.assertEquals("Test Subject for Get Ticket",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgent());
        Assert.assertEquals(tags,ticket.getTags());
        ticketService.delete(id);
    }


    @Test(expected = TicketNotFoundException.class)
    public void testGetTicketWithInValidId(){
        ticketService.ticket(-1);
    }

    @Test(expected = TicketNotFoundException.class)
    public void testGetTicketWithBlankId(){
        ticketService.ticket(0);

    }

    @Test
    public void testTicketListWithData(){
        List<Integer> ticketIdList = generateDummyTicket();
        List<Ticket> ticketList = ticketService.tickets();
        Assert.assertEquals(4,ticketList.size());
        deleteDummyTicket(ticketIdList,ticketService);
    }

    @Test
    public void testTicketListWithEmptyData(){
        List<Ticket> ticketList = ticketService.tickets();
        Assert.assertEquals(0,ticketList.size());
    }

    /**
     * Generate the dummy Ticket
     * @return List<Integer>
     */
    private List<Integer> generateDummyTicket(){
        List<Integer> ticketIdList = new ArrayList<>();
        ticketIdList.add(ticketService.create("Test Subject1","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3"))));
        ticketIdList.add(ticketService.create("Test Subject2","Agent2",new HashSet<>(Arrays.asList("tag4","tag1","tag2"))));
        ticketIdList.add(ticketService.create("Test Subject3","Agent2",new HashSet<>(Arrays.asList("tag1","tag4","tag3"))));
        ticketIdList.add(ticketService.create("Test Subject4","Agent1",new HashSet<>(Arrays.asList("tag2","tag5"))));
        return ticketIdList;
    }

    /**
     * generate dummy Tag data
     */
    private Set<String> getDummyTags(){
        return new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
    }
    /**
     * Delete all ticket from passed ticket list id
     * @param ticketIdList
     * @param ticketService
     */
    private void deleteDummyTicket(List<Integer> ticketIdList,TicketService ticketService){
        ticketIdList.forEach(id -> ticketService.delete(id));

    }
}
