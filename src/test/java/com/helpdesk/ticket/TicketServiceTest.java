package com.helpdesk.ticket;

import com.helpdesk.exception.DuplicateTicketIdException;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.InvalidTicketDAOFactoryTypeException;
import com.helpdesk.exception.TicketNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

/**
 * Created by root on 8/2/16.
 */
public class TicketServiceTest {

    protected static class Data {
        protected static boolean initialized = false;

        private static int int_ticketId;
        private static int int_ticketId2;
        private static int text_invalidTicketId;
        private static String text_subject;
        private static String text_subject2;
        private static String text_nullSubject;
        private static String text_emptySubject;
        private static String text_emptyAgent;
        private static String text_nullAgent;
        private static String text_invalidAgent;
        private static String text_updateAgent;
        private static String text_agent;
        private static String text_agent2;
        private static String text_tags;
        private static Set<String> set_tagSet;
        private static Set<String> set_emptyTagSet;

        /** DA: use camelCase for variables, using Underscore is not advisable.
         */
        private static String text_updatetags;
        private static Set<String> updateTagSet;
        private static String text_searchTag;
        private static TicketService ticketService;


        private static void initialize() throws InvalidTicketDAOFactoryTypeException {
            int_ticketId = 100;
            int_ticketId2 = 101;
            text_invalidTicketId = 000;
            text_subject = "First Ticket";
            text_subject2 = "Second Ticket";
            text_nullSubject = null;
            text_emptySubject = "";
            text_agent = "Yogesh";
            text_agent2 = "sweta";
            text_nullAgent = null;
            text_invalidAgent = "xxxx";
            text_emptyAgent = "";
            text_updateAgent = "Ganesh";
            text_tags = "tag1,tag2,tag3";
            //text_tags = "tag1,tag2,tag3,tag1 ,   tag2"; // if same tags added with space(s) then ticket tags set will update with duplicate values




            /**DA:test for duplicate tags also.
             * use like this:
             *  (Arrays.asList(tags.toLowerCase().trim().split(",")));
             *  if we gives params like "tag,   tag,tag   "
             *  it will consider all above as different and put into tag SET.
             *  */

            text_tags.replaceAll("\\s+","");
            set_tagSet = new HashSet<>(Arrays.asList(text_tags.toLowerCase().split(",")));
            set_emptyTagSet = new HashSet<>();
            text_tags = text_tags + ", tag4";
            text_updatetags = "tag4, tag5";// if same tags added with space(s) then ticket tags set will update with duplicate values
            text_updatetags.replaceAll("\\s+","");
            updateTagSet = new HashSet<>(Arrays.asList(text_updatetags.toLowerCase().split(",")));
            text_searchTag = "tag1";

            /**DA: why do you required to create ticketDAO object here?
             * and you again recreating in TicketService constructor */
            Data.initialized = true;

        }

    }


    @BeforeClass
    public static void classSetUp() throws InvalidTicketDAOFactoryTypeException {   
        if (!Data.initialized) {
            Data.initialize();
        }
    }


    @Before
    public void classInit() throws InvalidTicketDAOFactoryTypeException {
        Data.ticketService = new TicketService();
    }

    // EB: Since you are creating a new TicketService object every time, it would be better if you include it in @Before to avoid code duplication.

    @Test(expected = InvalidParamsException.class)
    public void testCreateTicketWithNullSubject() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_nullSubject, Data.text_agent, Data.set_tagSet);
    }


    @Test(expected = InvalidParamsException.class)
    public void testCreateTicketWithEmptySubject() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_emptySubject, Data.text_agent, Data.set_tagSet);
    }

    @Test(expected = InvalidParamsException.class)
    public void testCreateTicketWithNullAgent() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_emptySubject, Data.text_nullAgent, Data.set_tagSet);
    }

    @Test(expected = InvalidParamsException.class)
    public void testCreateTicketWithEmptyAgent() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_emptySubject, Data.text_agent, Data.set_tagSet);
    }

    @Test(expected = InvalidParamsException.class)
    public void testCreateTicketWithNullSubjectAndAgent() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_nullSubject, Data.text_nullAgent, Data.set_tagSet);
    }


    @Test(expected = InvalidParamsException.class)
    public void testCreateTicketWithEmptySubjectAndAgent() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_emptySubject, Data.text_emptyAgent, Data.set_tagSet);

    }

    @Test(expected = DuplicateTicketIdException.class)
    public void testCreateTicketWithDuplicateTicketId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
    }


    @Test
    public void testCreateTicketWithSubjectAndAgentAndEmptyTagSet() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Ticket ticket = Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_emptyTagSet);
        Assert.assertEquals(Data.int_ticketId, ticket.getId());
        Assert.assertEquals(Data.text_subject, ticket.getSubject());
        Assert.assertEquals(Data.text_agent, ticket.getAgentName());
        Assert.assertEquals(Data.set_emptyTagSet.size(), ticket.getTags().size());
        Data.ticketService.deleteTicket(ticket.getId());
    }

    @Test
    public void testCreateTicketWithSubjectAndAgentAndTagSet() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Ticket ticket = Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Assert.assertEquals(Data.int_ticketId, ticket.getId());
        Assert.assertEquals(Data.text_subject, ticket.getSubject());
        Assert.assertEquals(Data.text_agent, ticket.getAgentName());
        Assert.assertEquals(Data.set_tagSet, ticket.getTags());

        //Check here duplicate tags with spaces are accepted
        //System.out.println("tags : "+ticket.getTags());

    }

    @Test(expected = InvalidParamsException.class)
    public void testUpdateTicketWithInvalidTicketId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, TicketNotFoundException, DuplicateTicketIdException {
        TicketService ticketService = new TicketService();
        ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);

        // MS : Here first you are creating the ticket then deleting the ticket & then updating the ticket
        // Is this the correct way
        // refer doc of @afterClass  : here delete all tickets

        ticketService.updateAgent(Data.text_invalidTicketId, Data.text_agent);

    }

    @Test(expected = InvalidParamsException.class)
    public void testUpdateTicketWithNullAgent() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.updateAgent(Data.int_ticketId, Data.text_nullAgent);
        TicketService ticketService = new TicketService();
        ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        /**DA: Why you need to delete ticket to update with null agent?
         * It should be valid ticket(not deleted ticket) and null agent name */
        ticketService.deleteTicket(Data.int_ticketId);
        ticketService.updateAgent(Data.int_ticketId, Data.text_nullAgent);

    }

    @Test()
    public void testUpdateTicketWithAgent() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Ticket ticket = Data.ticketService.updateAgent(Data.int_ticketId, Data.text_updateAgent);
        Assert.assertEquals(Data.text_updateAgent, ticket.getAgentName());
        Assert.assertEquals(Data.int_ticketId, ticket.getId());

        /**DA: while updating agent or any other attribute, there should be some other check also required like check ticket id */

    }

    @Test()
    public void testUpdateTicketWithTags() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Ticket ticket = Data.ticketService.updateTags(Data.int_ticketId, Data.updateTagSet);
        Assert.assertEquals(Data.updateTagSet, ticket.getTags());


    }

    @Test(expected = InvalidParamsException.class)
    public void testUpdateTicketWithTagsInvalidId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
       Data.ticketService.updateTags(Data.text_invalidTicketId, Data.updateTagSet);

    }

    @Test()
    // missing camel case
    public void testUpdateTicketWithTagsandAgentName() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Ticket ticket = Data.ticketService.updateAgentAndTags(Data.int_ticketId, Data.text_updateAgent, Data.updateTagSet);
        Assert.assertEquals(Data.updateTagSet, ticket.getTags());
        Assert.assertEquals(Data.text_updateAgent, ticket.getAgentName());

    }

    @Test(expected = InvalidParamsException.class)
    public void testUpdateTicketWithTagsandAgentNameInvalidIds() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.updateAgentAndTags(Data.text_invalidTicketId,Data.text_agent, Data.updateTagSet);
    }



    @Test(expected = InvalidParamsException.class)
    public void testDeleteTicketWithInvalidTicketId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.deleteTicket(Data.text_invalidTicketId);
    }

    @Test
    public void testDeleteTicketWithValidTicketId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Boolean result = Data.ticketService.deleteTicket(Data.int_ticketId);
        Assert.assertTrue(result);
    }


    //Get Ticket Details By Id
    @Test(expected = InvalidParamsException.class)
    public void testTicketDetailsByInvalidId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.getTicketDetail(Data.text_invalidTicketId);
    }

    @Test
    public void testTicketDetailsByValidId() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Ticket ticketDetails = Data.ticketService.getTicketDetail(Data.int_ticketId);
        Assert.assertEquals(Data.text_agent, ticketDetails.getAgentName());
        Assert.assertEquals(Data.text_subject, ticketDetails.getSubject());
        Assert.assertEquals(Data.set_tagSet, ticketDetails.getTags());

    }


    // tickets by agent name
    @Test(expected = InvalidParamsException.class)
    public void testGetTicketsByNullAgentName() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId + 1, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.getTicketListDetailByAgentName(Data.text_nullAgent);

    }

    @Test(expected = InvalidParamsException.class)
    public void testGetTicketsByEmptyAgentName() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId + 1, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.getTicketListDetailByAgentName(Data.text_emptyAgent);

    }

    @Test(expected = TicketNotFoundException.class)
    public void testGetTicketsByInvalidAgentName() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        TicketService ticketService = new TicketService();
        ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        ticketService.createTicket(Data.int_ticketId + 1, Data.text_subject, Data.text_agent, Data.set_tagSet);
        ticketService.getTicketListDetailByAgentName(Data.text_invalidAgent);

    }

    @Test
    public void testGetTicketsByAgentName() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet);
        List<Ticket> agentTickets = Data.ticketService.getTicketListDetailByAgentName(Data.text_agent);
        Ticket ticket = agentTickets.get(0);
        Assert.assertEquals(Data.int_ticketId, ticket.getId());
        Assert.assertEquals(Data.text_subject, ticket.getSubject());
        Assert.assertEquals(Data.text_agent, ticket.getAgentName());
        Assert.assertEquals(Data.set_tagSet.size(), ticket.getTags().size());

    }

    @Test(expected = TicketNotFoundException.class)
    public void testGetTicketsByMisMatchTag() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId + 1, Data.text_subject, Data.text_agent, Data.set_tagSet);
        List<Ticket> agentTickets = Data.ticketService.getTicketListDetailByTag(Data.text_agent);
    }

    @Test(expected = InvalidParamsException.class)
    public void testGetTicketsByInvalidTag() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId + 1, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.getTicketListDetailByTag(Data.text_emptySubject);

    }

    @Test()
    public void testGetTicketsByValidTag() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId + 1, Data.text_subject, Data.text_agent, Data.set_tagSet);
        List<Ticket> agentTickets = Data.ticketService.getTicketListDetailByTag(Data.text_searchTag);
        Ticket ticket = agentTickets.get(0);
        Assert.assertEquals(Data.int_ticketId, ticket.getId());
        Assert.assertEquals(Data.text_subject, ticket.getSubject());
        Assert.assertEquals(Data.text_agent, ticket.getAgentName());
        Assert.assertEquals(Data.set_tagSet.size(), ticket.getTags().size());

    }

    // Missing camel case
    // Here you can create few dummy tickets & then delete all dummy tickets then you can call getTicketList()
    @Test(expected = TicketNotFoundException.class)
    public void testGetAllTicketswithNoRecord() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.getTicketList();
    }

    @Test
    public void testGetAllTickets() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_emptyTagSet);
        Data.ticketService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet);
        List<Ticket> agentTickets = Data.ticketService.getTicketList();
        Ticket ticket = agentTickets.get(0);
        Assert.assertEquals(Data.int_ticketId, ticket.getId());
        Assert.assertEquals(Data.text_subject, ticket.getSubject());
        Assert.assertEquals(Data.text_agent, ticket.getAgentName());
        Assert.assertEquals(Data.set_emptyTagSet, ticket.getTags());
        Ticket ticket_2 = agentTickets.get(1);
        Assert.assertEquals(Data.int_ticketId2, ticket_2.getId());
        Assert.assertEquals(Data.text_subject2, ticket_2.getSubject());
        Assert.assertEquals(Data.text_agent2, ticket_2.getAgentName());
        Assert.assertEquals(Data.set_tagSet.size(), ticket_2.getTags().size());


    }


    @Test
    public void testGetAllAgentWithTicketCount() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.ticketService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.ticketService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet);
        Map<String, Integer> tmCounts = Data.ticketService.getAllAgentWithTicketCount();

        int agentCount = tmCounts.get(Data.text_agent);
        Assert.assertEquals(1, agentCount);
        int agentCount2 = tmCounts.get(Data.text_agent2);
        Assert.assertEquals(1, agentCount2);

    }

}
