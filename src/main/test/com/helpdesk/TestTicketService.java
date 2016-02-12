package com.helpdesk;

import com.helpdesk.controller.TicketController;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class TestTicketService {
    private static int ticketId;
    private static int invalidTicketId;
    private static String subject;
    private static String nullSubject;
    private static String emptySubject;
    private static String emptyAgent;
    private static String nullAgent;
    private static String invalidAgent;
    private static String updateAgent;
    private static String agent;
    private static String tags;
    private static HashSet<String> tagSet;
    private static HashSet<String> emptyTagSet;
    private static HashSet<String> duplicateTagSet;
    private static String updatetags;
    private static HashSet<String> updateTagSet;
    private static String UpdateChoiceAgent;
    private static String UpdateChoiceTags;
    private static String UpdateChoiceNone;
    private static String UpdateChoiceBoth;
    private static String nullTag;
    private static String emptyTag;
    private static int day;
    private static String tagsActionAdd;
    private static String tagsActionRemove;
    private static String tagsActionNone;


    private TicketController ticketController;
    private static void initialize(){
        ticketId = 100;
        invalidTicketId = 000;
        subject = "First Ticket";
        nullSubject = null;
        emptySubject = "";
        agent = "Admin";
        nullAgent = null;
        invalidAgent = "xxxx";
        emptyAgent = "";
        updateAgent = "New Admin";
        tags = "mumbai,delhi,pune";
        tagSet =  new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
        emptyTagSet = new HashSet<>();
        tags = tags +", mumbai";
        duplicateTagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
        updatetags = "goa, surat,mumbai";
        updateTagSet = new HashSet<>(Arrays.asList(updatetags.toLowerCase().split(",")));
        UpdateChoiceAgent = "a";
        UpdateChoiceTags = "t";
        UpdateChoiceNone = "n";
        tagsActionAdd = "a";
        tagsActionRemove = "r";
        tagsActionNone = "n";
        nullTag = null;
        emptyTag = "";
        day = 5;
    }
    private static List<Ticket> createDummyTickets(TicketController ticketController) throws TicketExceptions{
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticketController.create(subject,agent,tagSet));
        ticketList.add(ticketController.create(subject,agent,tagSet));
        ticketList.add(ticketController.create(subject+"1",updateAgent,updateTagSet));
        ticketList.add(ticketController.create(subject+"1",updateAgent,updateTagSet));
        return ticketList;
    }

    private static void deleteDummyTicket(List<Ticket> ticketList, TicketController ticketController){
        ticketList.forEach(ticket -> {
            try {
                ticketController.delete(ticket.getId());
            } catch (TicketExceptions ticketExceptions) {
                ticketExceptions.printStackTrace();
            }
        });
    }

    @BeforeClass
    public static void initializeClassResources(){
        initialize();
    }

    @Before
    public void beforeTest(){
        ticketController = new TicketController();
    }

    @Test
    public void testCreateTicketWithNullSubject() throws TicketExceptions{
        Ticket ticket = ticketController.create(nullSubject, agent, tagSet);
    }

    @Test
    public void testCreateTicketWithEmptySubject() throws TicketExceptions{
        Ticket ticket = ticketController.create(emptySubject, agent, tagSet);
    }

    @Test
    public void testCreateTicketWithNullAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,nullAgent,tagSet);
    }

    @Test
    public void testCreateTicketWithEmptyAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,emptyAgent,tagSet);
    }

    @Test
    public void testCreateTicketWithNullSubjectAndAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(nullSubject,nullAgent,tagSet);
    }

    @Test
    public void testCreateTicketWithEmptySubjectAndAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(emptySubject,emptyAgent,tagSet);
    }

    @Test
    public void testCreateTicketWithSubjectAndAgentAndEmptyTagSet() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,emptyTagSet);
        Assert.assertEquals(subject,ticket.getSubject());
        Assert.assertEquals(agent,ticket.getAgent());
        Assert.assertEquals(emptyTagSet.size(),ticket.getTags().size());
        ticketController.delete(ticket.getId());
    }

    @Test
    public void testCreateTicketWithSubjectAndAgentAndTagset() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Assert.assertEquals(subject,ticket.getSubject());
        Assert.assertEquals(agent,ticket.getAgent());
        Assert.assertTrue(tagSet.equals(ticket.getTags()));
        ticketController.delete(ticket.getId());
    }

    //update ticket

    @Test
    public void testUpdateTicketWithInvalidTicketId() throws TicketExceptions{
        Ticket ticket = ticketController.update(invalidTicketId,agent,emptyTagSet,UpdateChoiceAgent);
    }

    @Test
    public void testUpdateTicketWithNullAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket updateTicket = ticketController.update(ticket.getId(),nullAgent,tagSet,UpdateChoiceAgent);
    }
    // need to delete file after class using @After
    @Test
    public void testUpdateTicketWithEmptyAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket updateTicket = ticketController.update(ticket.getId(),nullAgent,tagSet,UpdateChoiceAgent);
    }

    @Test
    public void testUpdateTicketWithAgentAndEmptyTags() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket updateTicket = ticketController.update(ticket.getId(),agent,emptyTagSet,tagsActionAdd);
        Assert.assertEquals(ticket.getId(),updateTicket.getId());
        Assert.assertEquals(agent,updateTicket.getAgent());
        Assert.assertEquals(emptyTagSet.size(),updateTicket.getTags().size());
        ticketController.delete(ticket.getId());
    }

    @Test
    public void testUpdateTicketWithAgentAndTags() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket updateTicket = ticketController.update(ticket.getId(),updateAgent,updateTagSet,tagsActionAdd);
        Assert.assertEquals(ticket.getId(),updateTicket.getId());
        Assert.assertEquals(updateAgent,updateTicket.getAgent());
        Assert.assertTrue(updateTagSet.equals(updateTicket.getTags()));
        ticketController.delete(updateTicket.getId());
    }

    // Delete

    @Test
    public void testDeleteTicketWithInvalidTicketId() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Boolean result = ticketController.delete(invalidTicketId);
        Assert.assertFalse(result);
    }

    @Test
    public void testDeleteTicketWithValidTicketId() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Boolean result = ticketController.delete(ticket.getId());
        Assert.assertTrue(result);
    }

    //Get Ticket Details By Id
    @Test
    public void testTicketDetailsByInvalidId()  throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket ticketDetails = ticketController.getDetails(invalidTicketId);
    }

    @Test
    public void testTicketDetailsByValidId()  throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket ticketDetails = ticketController.getDetails(ticket.getId());
        Assert.assertEquals(agent,ticketDetails.getAgent());
        Assert.assertEquals(subject,ticketDetails.getSubject());
        Assert.assertTrue(tagSet.equals(ticketDetails.getTags()));
        ticketController.delete(ticketDetails.getId());
    }

    // tickets by agent name

    @Test
    public void testGetTicketsByEmptyAgentName()  throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets(ticketController);
        List<Ticket> agentTickets = ticketController.getTicketsByAgent(emptyAgent);
        Assert.assertEquals(0,agentTickets.size());
        deleteDummyTicket(dummyTicketList,ticketController);
    }

    @Test
    public void testGetTicketsByInvalidAgentName()  throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets(ticketController);
        List<Ticket> agentTickets = ticketController.getTicketsByAgent(invalidAgent);
        Assert.assertEquals(0,agentTickets.size());
        deleteDummyTicket(dummyTicketList,ticketController);
    }

    @Test()
    public void testGetTicketsByAgentName()  throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets(ticketController);
        List<Ticket> agentTickets = ticketController.getTicketsByAgent(agent);
        Assert.assertEquals(2,agentTickets.size());
        agentTickets.forEach(tickets -> Assert.assertEquals(agent,tickets.getAgent()));
        deleteDummyTicket(dummyTicketList,ticketController);
    }

/*
    //tickets by tag
    @Test
    public void testGetTicketsByNullTag(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> ticketList = ticketReportService.ticketsByTag(nullTag);
        Assert.assertEquals(0,ticketList.size());
        deleteDummyTicket(dummyTicketList,ticketService); // need to delete file
    }

    @Test
    public void testGetTicketsByEmptyTag(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> ticketList = ticketReportService.ticketsByTag(emptyTag);
        Assert.assertEquals(0,ticketList.size());
        deleteDummyTicket(dummyTicketList,ticketService); // need to delete file
    }

    @Test
    public void testGetTicketsByInvalidTag(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> ticketList = ticketReportService.ticketsByTag(agent);
        Assert.assertEquals(0,ticketList.size());
        deleteDummyTicket(dummyTicketList,ticketService); // need to delete file
    }

    @Test()
    public void testGetTicketsByValidTag(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> ticketList = ticketReportService.ticketsByAgentName(agent);
        ticketList.forEach(tickets -> Assert.assertTrue(tickets.getTags().contains("mumbai")));
        deleteDummyTicket(dummyTicketList,ticketService); // need to delete file
    }

    @Test()
    public void testGetTotalTicketCount(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
       // List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        Assert.assertEquals(4,ticketReportService.getTotalTicketCounts()); // dummyTicketList.size()
        deleteDummyTicket(dummyTicketList,ticketService);// need to delete file
    }

    @Test(expected = InvalidParameterException.class)
    public void testGetOldestTicket(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        Ticket oldestTicket = ticketReportService.oldestTicket();
        List<Ticket> ticketList = ticketService.tickets();
        ticketList.forEach(ticket -> Assert.assertTrue(oldestTicket.getCreated().compareTo(ticket.getCreated()) <= 0 ));
        deleteDummyTicket(dummyTicketList,ticketService);
    }

    @Test
    public void testTicketOlderByDay()
    {
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> ticketList = ticketReportService.ticketsOlderByDays(day);
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getCreated().compareTo(LocalDateTime.now()) <= 0));
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }*/

    //
   /* @Before
    private void doThisPriorToEveryTest(){

    }

    // @Test (excepted = InvalidParameterException.class)
    @Test
    public void testCreate(){
        //User asserts after calling services to compare actual and expected result
    }

    @After
    private void doThisAfterEveryTest(){

    }

    @AfterClass
    public void doThisAfterUnloadingClass(){
        //release all resources like file deletion
    }*/
}
