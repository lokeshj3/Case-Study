package com.inin.service.report;

import com.inin.factory.TicketFactory;
import com.inin.model.Ticket;
import com.inin.service.core.TicketService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by root on 11/2/16.
 */
public class TicketReportServiceTest {

    private TicketReportService ticketReportService;
    private TicketService ticketService;
    private List<Integer> ticketIdList;

    @Before
    public void setUp()
    {
        ticketReportService = TicketFactory.newTicketReportServiceInstance();
        ticketService = TicketFactory.newTicketServiceInstance();
        ticketIdList = generateDummyTicket(ticketService);
    }

    @After
    public void flushData(){
        deleteDummyTicket(ticketIdList,ticketService);
    }

    //Test Cases for ticketsByTag
    @Test
    public void testTicketsByTagInUppercase(){
        List<Ticket> ticketList = ticketReportService.ticketsByTag("Tag1");
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getTags().contains("tag1")));
    }
    @Test
    public void testTicketsByTag(){
        List<Ticket> ticketList = ticketReportService.ticketsByTag("tag1");
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getTags().contains("tag1")));
    }

    @Test
    public void testTicketsByTagWithEmptyTag(){
        List<Ticket> ticketList = ticketReportService.ticketsByTag("");
        Assert.assertEquals(0,ticketList.size());
    }

    @Test
    public void testTicketsByTagWithNullTag(){
        List<Ticket> ticketList = ticketReportService.ticketsByTag(null);
        Assert.assertEquals(0,ticketList.size());
    }

    @Test
    public void testTicketsByTagWithTagNotInSystem(){
        List<Ticket> ticketList = ticketReportService.ticketsByTag("12154@8545");
        Assert.assertEquals(0,ticketList.size());
    }


    //Test Cases for ticketsByAgent
    @Test
    public void testTicketsByAgent(){
        List<Ticket> ticketList = ticketReportService.ticketsByAgent("Agent1");
        System.out.println(ticketList);
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getAgent().equals("Agent1")));
    }
    @Test
    public void testTicketsByAgentWithUpperCase(){
        List<Ticket> ticketList = ticketReportService.ticketsByAgent("AGENT1");
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getAgent().equals("Agent1")));
    }

    @Test
    public void testTicketsByTagWithEmptyAgent(){
        List<Ticket> ticketList = ticketReportService.ticketsByAgent("");
        Assert.assertEquals(0,ticketList.size());
    }

    @Test
    public void testTicketsByTagWithNullAgent(){
        List<Ticket> ticketList = ticketReportService.ticketsByAgent(null);
        Assert.assertEquals(0,ticketList.size());
    }

    @Test
    public void testTicketsByTagWithAgentNotInSystem(){
        List<Ticket> ticketList = ticketReportService.ticketsByAgent("121#$548545");
        Assert.assertEquals(0,ticketList.size());
    }



    //Test Cases for ticketsCountByAgent
    @Test
    public void testTicketsCountByAgent()
    {
        Map<String, Long> agentTicketCount = ticketReportService.ticketsCountByAgent();
        agentTicketCount.forEach((agent,count)-> Assert.assertEquals(count.toString(),Integer.toString(ticketReportService.ticketsByAgent(agent).size())));
    }


    //Test Cases for ticketsCountByTag
    @Test
    public void testTicketsCountByTag()
    {
        Map<String, Long> agentTicketCount = ticketReportService.ticketsCountByTag();
        agentTicketCount.forEach((tag,count)-> Assert.assertEquals(count.toString(),Integer.toString(ticketReportService.ticketsByTag(tag).size())));
    }

    //Test Cases for totalTicketCount
    @Test
    public void testTotalTicketCount(){
        int count = ticketReportService.totalTicketCount();
        Assert.assertEquals(count,ticketReportService.getAllTickets().size());
    }

    //Test Cases for oldestTicket
    @Test
    public void testOldestTicket(){
        Ticket ticket = ticketReportService.oldestTicket();
        Ticket firstTicket = ticketReportService.getTicket(ticketIdList.get(0));
        Assert.assertEquals(firstTicket.getId(),ticket.getId());
        Assert.assertEquals(firstTicket.getSubject(),ticket.getSubject());
        Assert.assertEquals(firstTicket.getAgent(),ticket.getAgent());
        Assert.assertEquals(firstTicket.getTags(),ticket.getTags());
        Assert.assertEquals(firstTicket.getCreated(),ticket.getCreated());
        Assert.assertEquals(firstTicket.getModified(),ticket.getModified());


    }

        //Test Cases for ticketOlderByDate
    @Test
    public void testTicketOlderByDate(){
        List<Ticket> ticketList = ticketReportService.ticketOlderByDate(LocalDateTime.now());
        Assert.assertEquals(ticketIdList.size(),ticketList.size());
    }


    /**
     * Generate the dummy Ticket
     * @param ticketService
     * @return List<Integer>
     */
    private List<Integer> generateDummyTicket(TicketService ticketService){
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


}
