package com.helpdesk.services;

import com.helpdesk.controller.TicketController;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.model.Ticket;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.*;

public class TestTicketService {
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
    private static HashSet<String> updateTagSet;
    private static String UpdateChoiceAgent;
    private static String nullTag;
    private static String updateTags;
    private static int day;
    private static String tagsActionAdd;
    private static String tagsActionRemove;
    private static String tagsActionNone;
    private static TicketController ticketController;

    private static void initialize(){
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
        updateTags = "goa,surat,mumbai";
        updateTagSet = new HashSet<>(Arrays.asList(updateTags.toLowerCase().split(",")));
        UpdateChoiceAgent = "a";
        tagsActionAdd = "a";
        tagsActionRemove = "r";
        tagsActionNone = "n";
        day = 5;
    }
    private static List<Ticket> createDummyTickets() throws TicketExceptions{
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticketController.create(subject,agent,tagSet));
        ticketList.add(ticketController.create(subject,agent,tagSet));
        ticketList.add(ticketController.create(subject+"1",updateAgent,updateTagSet));
        ticketList.add(ticketController.create(subject+"1",updateAgent,updateTagSet));
        return ticketList;
    }

    private static void deleteDummyTicket(List<Ticket> ticketList){
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

    private static void deleteAllTickets(){
        ticketController.removeAllTickets();
    }

    @Test(expected = TicketExceptions.class)
    public void testCreateTicketWithNullSubject() throws TicketExceptions{
        Ticket ticket = ticketController.create(nullSubject, agent, tagSet);
    }

    @Test(expected = TicketExceptions.class)
    public void testCreateTicketWithEmptySubject() throws TicketExceptions{
        Ticket ticket = ticketController.create(emptySubject, agent, tagSet);
    }

    @Test(expected = TicketExceptions.class)
    public void testCreateTicketWithNullAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,nullAgent,tagSet);
    }

    @Test(expected = TicketExceptions.class)
    public void testCreateTicketWithEmptyAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,emptyAgent,tagSet);
    }

    @Test(expected = TicketExceptions.class)
    public void testCreateTicketWithNullSubjectAndAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(nullSubject,nullAgent,tagSet);
    }

    @Test(expected = TicketExceptions.class)
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
    @Test(expected = TicketExceptions.class)
    public void testUpdateTicketWithInvalidTicketId() throws TicketExceptions{
        Ticket ticket = ticketController.update(invalidTicketId,agent,emptyTagSet,UpdateChoiceAgent);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateTicketWithNullAgent() throws TicketExceptions{
        Ticket ticket = ticketController.create(subject,agent,tagSet);
        Ticket updateTicket = ticketController.update(ticket.getId(),nullAgent,tagSet,UpdateChoiceAgent);
    }

    @Test(expected = NullPointerException.class)
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

    @Test(expected = TicketExceptions.class)
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
    @Test(expected = TicketExceptions.class)
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
        List<Ticket> dummyTicketList = createDummyTickets();
        List<Ticket> agentTickets = ticketController.getTicketsByAgent(emptyAgent);
        Assert.assertEquals(0,agentTickets.size());
        deleteDummyTicket(dummyTicketList);
    }

    @Test
    public void testGetTicketsByInvalidAgentName()  throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets();
        List<Ticket> agentTickets = ticketController.getTicketsByAgent(invalidAgent);
        Assert.assertEquals(0,agentTickets.size());
        deleteDummyTicket(dummyTicketList);
    }

    @Test
    public void testGetTicketsByAgentName()  throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets();
        List<Ticket> agentTickets = ticketController.getTicketsByAgent(agent);
        Assert.assertEquals(3,agentTickets.size());
        agentTickets.forEach(tickets -> Assert.assertEquals(agent,tickets.getAgent()));
        deleteDummyTicket(dummyTicketList);
    }

    //tickets by tag
    @Test
    public void testGetTicketsByEmptyTag() throws  TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets();
        List<Ticket> ticketList = ticketController.getTicketsByTag(emptyTagSet);
        Assert.assertEquals(0,ticketList.size());
        deleteDummyTicket(dummyTicketList);
    }

    @Test
    public void testGetTicketsByValidTagSet() throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets();
        List<Ticket> ticketList = ticketController.getTicketsByTag(tagSet);
        ticketList.forEach(tickets -> Assert.assertTrue(tickets.getTags().contains("mumbai")));
        deleteDummyTicket(dummyTicketList);
    }

    @Test()
    public void testGetTotalTicketCount() throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets();
        List<Ticket> getAllTickets = ticketController.getAll();
        Assert.assertEquals(getAllTickets.size(),ticketController.getTotalTicketCount());
        deleteDummyTicket(dummyTicketList);
    }  //till done

    @Test
    public void testGetOldestTicketWithEmptyTicketList() throws TicketExceptions{  // no tickets in the system
        Ticket oldestTicket = ticketController.getOldestTicket();
        List<Ticket> ticketList = ticketController.getAll();
        ticketList.forEach(ticket -> Assert.assertTrue(oldestTicket.getCreated().compareTo(ticket.getCreated()) <= 0 ));
    }

    @Test
    public void testGetOldestTicket() throws TicketExceptions{
        List<Ticket> dummyTicketList = createDummyTickets();
        Ticket oldestTicket = ticketController.getOldestTicket();
        List<Ticket> ticketList = ticketController.getAll();
        ticketList.forEach(ticket -> Assert.assertTrue(oldestTicket.getCreated().compareTo(ticket.getCreated()) <= 0 ));
        deleteDummyTicket(dummyTicketList);
    }

    @Test
    public void testTicketOlderByDay() throws TicketExceptions{
        List<Ticket> dummyTicketTicketList = createDummyTickets();
        List<Ticket> ticketList = ticketController.olderTicketsThanDays(day);
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getCreated().compareTo(LocalDateTime.now()) <= 0));
        deleteDummyTicket(dummyTicketTicketList);
    }

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
*/
    @AfterClass
    public static void doThisAfterUnloadingClass(){
        //release all resources like file deletion
        deleteAllTickets();
    }
}
