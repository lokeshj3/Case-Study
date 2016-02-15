package com.ticketmaster.service;

import com.ticketmaster.constants.SearchKeys;
import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.AppUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * TestTicketService class
 * This class is used to test the methods available in the TicketService class
 * Created by Virendra on 8/2/16.
 */
public class TestTicketService {


    protected static class Data {
        protected static boolean isSet  =   false;
        protected static Integer INVALID_ID;
        protected static Integer ID;

        protected static String SUBJECT;
        protected static String AGENT  ;
        protected static String NEW_AGENT;
        protected static String TAG_STRING;
        protected static String TAG_STRING1;
        protected static String TAG_STRING2;
        protected static String NEW_TAG_STRING;

        protected static final String NULL_SUBJECT    =   null;
        protected static final String NULL_AGENT      =   null;
        protected static final String NEW_NULL_AGENT  =   null;

        protected static Set<String> TAGS;
        protected static Set<String> NEW_TAGS;

        protected static final Set<String> NULL_TAGS          =   null;
        protected static final Set<String> NEW_NULL_TAGS      =   null;

        protected static TicketService service;
        protected static ReportService reportService;

        protected static void initialize(){

            NEW_AGENT  =   "test-agent2";
            Data.TAG_STRING1=   "tag1";
            Data.TAG_STRING2=   "tag2";

            Data.ID         =   0;
            Data.INVALID_ID =   -1;
            Data.SUBJECT    =   "test-subject";
            Data.AGENT      =   "agent1";
            Data.NEW_AGENT  =   "agent2";
            Data.TAG_STRING =   "new tag";
            Data.TAGS       =   new HashSet<>(asList(Data.TAG_STRING1,Data.TAG_STRING2));
            Data.NEW_TAGS   =   new HashSet<>(Collections.singletonList(TAG_STRING));
            Data.service    =   new TicketService();
            Data.reportService = new ReportService();

            NEW_TAG_STRING="new tag";
            TAGS       =   new HashSet<>(asList(Data.TAG_STRING1,Data.TAG_STRING2));
            NEW_TAGS   =   new HashSet<>(asList(Data.NEW_TAG_STRING));
            service    =   new TicketService();

            isSet = true;

        }
    }

    @BeforeClass
    public static void beforeAll() throws IOException, ClassNotFoundException {
        if (!Data.isSet)
            Data.initialize();
        AppUtil.initializeApp();
    }

    /**
     * method that runs before every test case in this class
     */
    @Before
    public void beforeTest(){
    }

    /**
     * method that runs after every test case in the class
     */
    @After
    public void afterTest()
            throws IOException, NotFoundException{

    }

    /* ====================== Create Ticket test cases ====================== */
    /**
     * scenario : all relevant data is supplied to create ticket
     */
    @Test
    public void testCreateTicket()
            throws IOException, NotFoundException,
            IncompleteDataException, ClassNotFoundException {

        Ticket ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);

        assertEquals(Data.SUBJECT,ticket.getSubject());
        assertEquals(Data.AGENT,ticket.getAgent());
        assertEquals(Data.TAGS, ticket.getTags());
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }
    /**
     * scenario : tags are not supplied
     */
    @Test
    public void testCreateTicketWithNoTags()
            throws IOException, NotFoundException,
            IncompleteDataException, ClassNotFoundException {

        Ticket ticket =  Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.NULL_TAGS);

        assertEquals(Data.SUBJECT,ticket.getSubject());
        assertEquals(Data.AGENT,ticket.getAgent());
        assertTrue(ticket.getTags().isEmpty());
        //clean data
        Data.service.cleanTestData(ticket.getId());

    }
    /**
     * scenario : Data.AGENT is not supplied
     */
    @Test(expected=IncompleteDataException.class)
    public void testCreateTicketWithNoAgent()
            throws IncompleteDataException, IOException,
            NotFoundException, ClassNotFoundException {
        Data.service.createTicket(Data.SUBJECT, Data.NULL_AGENT, Data.TAGS);
    }
    /**
     * scenario : Data.AGENT is not supplied
     */
    @Test(expected=IncompleteDataException.class)
    public void testCreateTicketWithNoSubject()
            throws IncompleteDataException, IOException,
            NotFoundException, ClassNotFoundException {

        Data.service.createTicket(Data.NULL_SUBJECT, Data.AGENT, Data.TAGS);
    }

    /* ====================== Update Ticket test cases ====================== */
    @Test
    public void testUpdateTicketWithAgent()
            throws IncompleteDataException, IOException,
            NotFoundException, ClassNotFoundException, NoUpdateException {
        Ticket ticket, result;


        ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        //update Data.AGENT
        Data.ID = ticket.getId();
        result = Data.service.updateTicket(Data.ID, Data.NEW_AGENT, Data.NEW_NULL_TAGS);

        //check updates
        assertEquals(Data.NEW_AGENT.toLowerCase(), result.getAgent().toLowerCase());
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }

    @Test
    public void testUpdateTicketWithTag()
            throws IncompleteDataException, IOException,
            NotFoundException, ClassNotFoundException, NoUpdateException {

        Ticket ticket, result;

        ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();
        result = Data.service.updateTicket(Data.ID, Data.NEW_NULL_AGENT, Data.NEW_TAGS);
        //check updates
        assertTrue(result.getTags().contains(Data.NEW_TAG_STRING));
        //clean data
        Data.service.cleanTestData(ticket.getId());

    }

    @Test(expected = NoUpdateException.class)
    public void testUpdateTicketWithNoData()
            throws NoUpdateException, IOException ,IncompleteDataException ,
            ClassNotFoundException , NotFoundException {

        Ticket ticket = null;

        try{
            ticket=Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
            Data.ID = ticket.getId();
            Data.service.updateTicket(Data.ID, Data.NEW_NULL_AGENT, Data.NEW_NULL_TAGS);
        }catch (IOException  | IncompleteDataException  |
                ClassNotFoundException   e){

            assert ticket != null;
            //clean data
            Data.service.cleanTestData(ticket.getId());
        }


    }

    @Test(expected = NotFoundException.class)
    public void testUpdateTicketWithInvalidId()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = null;

        try{
            ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
            Data.service.updateTicket(Data.INVALID_ID, Data.NEW_AGENT, Data.NEW_TAGS);
        }catch (IOException  | IncompleteDataException  |
                ClassNotFoundException e){
            assert ticket != null;
            //clean data
            Data.service.cleanTestData(ticket.getId());
        }
    }

    /* ====================== delete Ticket test cases ====================== */

    @Test
    public void testDeleteTicket()
            throws NoUpdateException, IOException ,
            IncompleteDataException , ClassNotFoundException , NotFoundException{

        Ticket ticket=Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();
        boolean result = Data.service.deleteTicket(Data.ID);
        assertTrue(result);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteTicketWithInvalidId()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = null;

        try{
            ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
            Data.service.deleteTicket(Data.INVALID_ID);
        }catch ( IOException  | IncompleteDataException  |
                ClassNotFoundException e){
            assert ticket != null;
            //clean data
            Data.service.cleanTestData(ticket.getId());
        }

    }
    @Test(expected = NotFoundException.class)
    public void testDeleteTicketTwice()
            throws NoUpdateException, IOException ,
            IncompleteDataException , ClassNotFoundException , NotFoundException{

        Ticket ticket=Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();
        boolean result = Data.service.deleteTicket(Data.ID);
        try{
            assertTrue(result);
        }finally {
            //delete once more
            Data.service.deleteTicket(Data.ID);
        }

    }

    /* ====================== get Ticket test cases ====================== */
    @Test
    public void testGetTicket()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{
        Ticket ticket, result;
        ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();

        result = Data.service.getTicket(Data.ID);
        try{
            assertEquals(Data.ID, result.getId());
            assertEquals(ticket.hashCode(), result.hashCode());
            assertEquals(ticket.toString().toLowerCase(), result.toString().toLowerCase());
        }finally {
            //clean data
            Data.service.cleanTestData(ticket.getId());
        }
    }
    @Test(expected = NotFoundException.class)
    public void testGetTicketWithInvalidId()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();
        Data.service.getTicket(Data.INVALID_ID);
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }

    /* ====================== get all tickets from the system ====================== */
    @Test
    public void testGetAllTickets()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        List<Ticket> result = Data.service.getTicketList();
        assertTrue(result.size() >0);
        assertFalse(result.isEmpty());
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }

    /* ====================== search ticket for specified agent in system ====================== */
    @Test
    public void testAgentTicketCount()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{
        Ticket ticket1, ticket2 ;
        ticket1 = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        List<Ticket> result = Data.service.searchTicket(SearchKeys.AGENT,Data.AGENT);

        assertFalse(result.isEmpty());

        ticket2 = result.stream().findFirst().get();
        assertEquals(ticket1.getAgent().toLowerCase(), ticket2.getAgent().toLowerCase());
        Assert.assertNotEquals(Data.NEW_AGENT.toLowerCase(), ticket2.getAgent().toLowerCase());
        //clean data
        Data.service.cleanTestData(ticket1.getId());
    }

    @Test
    public void testInvalidAgentTicketCount()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        List<Ticket> result = Data.service.searchTicket(SearchKeys.AGENT,Data.NEW_AGENT);
        assertTrue(result.isEmpty());
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }

    /* ====================== search ticket for specified tag in system ====================== */
    @Test
    public void testTagsTicketCount()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{
        Ticket ticket1, ticket2 ;
        ticket1 = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        List<Ticket> result = Data.service.searchTicket(SearchKeys.TAGS,Data.TAG_STRING1);

        assertFalse(result.isEmpty());

        ticket2 = result.stream().findFirst().get();
        assertTrue(ticket2.getTags().contains(Data.TAG_STRING1));
        //clean data
        Data.service.cleanTestData(ticket1.getId());
    }
    @Test
    public void testInvalidTagsTicketCount()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        List<Ticket> result = Data.service.searchTicket(SearchKeys.TAGS,Data.NEW_TAG_STRING);
        assertTrue(result.isEmpty());
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }

    /* ====================== ticket count for agent in system ====================== */
    @Test
    public void testTotalTicketForValidAgent()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Ticket ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Map<String, Integer> result = Data.service.agentTicketCount();
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(Data.AGENT));
        assertTrue(result.get(Data.AGENT) >0);
        //clean data
        Data.service.cleanTestData(ticket.getId());
    }

}
