


/// This is incomplete file waiting for deepak's service. Test cases will be changed according to deepak's service.
// 10% test cases will be changed




package com.helpdesk;

import com.helpdesk.controller.TicketController;
/*import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.model.Ticket;
import com.helpdesk.services.TicketReportService;
import com.helpdesk.services.TicketService;
import org.junit.*;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;*/
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
        updatetags = "goa, surat";
        updateTagSet = new HashSet<>(Arrays.asList(updatetags.toLowerCase().split(",")));
        UpdateChoiceAgent = "A";
        UpdateChoiceTags = "T";
        UpdateChoiceNone = "N";
        UpdateChoiceBoth = "B";
        nullTag = null;
        emptyTag = "";
        day = 5;
    }
/*
    private static List<Ticket> createDummyTickets(TicketService ticketService) throws InvalidParamsException, TicketFailure{
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticketService.createTicket(subject,agent,tagSet);
        ticketList.add(ticketService.createTicket(subject,agent,tagSet);
        ticketList.add(ticketService.createTicket(subject+"1",updateAgent,updateTagSet);
        ticketList.add(ticketService.createTicket(subject+"1",updateAgent,updateTagSet);
        return ticketList;
    }

    private static void deleteDummyTicket(List<Ticket> ticketList,TicketService ticketService){
        ticketList.forEach(ticket -> ticketService.delete(ticket.getId()));
    }

    @BeforeClass
    public static void initializeClassResources(){
        initialize();
        // create file to add records
    }

    @Before
    public void beforeTest(){
        ticketController = new TicketController();
    }

    @Test
    public void testCreateTicketWithNullSubject() throws InvalidParamsException, TicketFailure{
        ticketController = new TicketController();
        Ticket ticket = ticketController.create(nullSubject, agent, tagSet);
    }

    @Test
    public void testCreateTicketWithEmptySubject() throws InvalidParamsException, TicketFailure{
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(emptySubject, agent, tagSet);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateTicketWithNullAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,nullAgent,tagSet);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateTicketWithEmptyAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,emptyAgent,tagSet);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateTicketWithNullSubjectAndAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(nullSubject,nullAgent,tagSet);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateTicketWithEmptySubjectAndAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(emptySubject,emptyAgent,tagSet);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateTicketWithSubjectAndAgentAndEmptyTagSet(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,emptyTagSet);
        Assert.assertEquals(subject,ticket.getSubject());
        Assert.assertEquals(agent,ticket.getAgent());
        Assert.assertEquals(emptyTagSet.size(),ticket.getTags().size());
        ticketService.delete(ticket.getId());
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateTicketWithSubjectAndAgentAndTagset(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Assert.assertEquals(subject,ticket.getSubject());
        Assert.assertEquals(agent,ticket.getAgent());
        Assert.assertTrue(tagSet.equals(ticket.getTags()));
        ticketService.delete(ticket.getId());
    }

    //update ticket

    @Test(expected = InvalidParameterException.class)
    public void testUpdateTicketWithInvalidTicketId(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.update(invalidTicketId,agent,emptyTagSet,UpdateChoiceAgent);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUpdateTicketWithNullAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Ticket updateTicket = ticketService.update(ticket.getId(),nullAgent,tagSet,UpdateChoiceAgent);
    }
    // need to delete file after class using @After
    @Test(expected = InvalidParameterException.class)
    public void testUpdateTicketWithEmptyAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Ticket updateTicket = ticketService.update(ticket.getId(),nullAgent,tagSet,UpdateChoiceAgent);
    }

    @Test
    public void testUpdateTicketWithAgentAndEmptyTags(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Ticket updateTicket = ticketService.update(ticket.getId(),agent,emptyTagSet,UpdateChoiceBoth);
        Assert.assertEquals(ticket.getId(),updateTicket.getId());
        Assert.assertEquals(agent,updateTicket.getAgent());
        Assert.assertEquals(emptyTagSet.size(),updateTicket.getTags().size());
        ticketService.delete(ticket.getId());
    }

    @Test
    public void testUpdateTicketWithAgentAndTags(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Ticket updateTicket = ticketService.update(ticket.getId(),updateAgent,updateTagSet,UpdateChoiceBoth);
        Assert.assertEquals(ticket.getId(),updateTicket.getId());
        Assert.assertEquals(updateAgent,updateTicket.getAgent());
        Assert.assertTrue(updateTagSet.equals(updateTicket.getTags()));
        ticketService.delete(updateTicket.getId());
    }

    // Delete

    @Test
    public void testDeleteTicketWithInvalidTicketId(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Boolean result = ticketService.delete(ticket.getId());
        Assert.assertFalse(result);
        ticketService.delete(ticket.getId());
    }

    @Test
    public void testDeleteTicketWithValidTicketId(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Boolean result = ticketService.delete(ticket.getId());
        Assert.assertTrue(result);
        ticketService.delete(ticket.getId());
    }

    //Get Ticket Details By Id
    @Test(expected = InvalidParameterException.class)
    public void testTicketDetailsByInvalidId() {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Ticket ticketDetails = ticketService.ticketDetails(invalidTicketId);
    }

    @Test
    public void testTicketDetailsByValidId() {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.createTicket(subject,agent,tagSet);
        Ticket ticketDetails = ticketService.ticketDetails(ticket.getId());
        Assert.assertEquals(agent,ticketDetails.getAgent());
        Assert.assertEquals(subject,ticketDetails.getSubject());
        Assert.assertTrue(tagSet.equals(ticketDetails.getTags()));
        ticketService.delete(ticketDetails.getId());
    }

    // tickets by agent name
    @Test
    public void testGetTicketsByNullAgentName(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> agentTicketList = ticketReportService.ticketsByAgentName(nullAgent);
        Assert.assertEquals(0,agentTicketList.size());
        deleteDummyTicket(dummyTicketList,ticketService);
    }

    @Test(expected = InvalidParameterException.class)
    public void testGetTicketsByEmptyAgentName(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> agentTickets = ticketReportService.ticketsByAgentName(emptyAgent);
        Assert.assertEquals(0,agentTickets.size());
        deleteDummyTicket(dummyTicketList,ticketService);
    }

    @Test(expected = InvalidParameterException.class)
    public void testGetTicketsByInvalidAgentName(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> agentTickets = ticketReportService.ticketsByAgentName(invalidAgent);
        Assert.assertEquals(0,agentTickets.size());
        deleteDummyTicket(dummyTicketList,ticketService);
    }

    @Test()
    public void testGetTicketsByAgentName(){
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketList = createDummyTickets(ticketService);
        TicketReportService ticketReportService = new TicketReportService();
        List<Ticket> agentTickets = ticketReportService.ticketsByAgentName(agent);
        Assert.assertEquals(0,agentTickets.size());
        agentTickets.forEach(tickets -> Assert.assertEquals(agent,tickets.getAgent()));
        deleteDummyTicket(dummyTicketList,ticketService);// need to delete file
    }

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
