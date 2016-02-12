package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.AppUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.*;

/**
 * TestTicketService class
 * This class is used to test the methods available in the TicketService class
 * Created by Virendra on 8/2/16.
 */
public class TestTicketService {


    protected static class Data{
        protected static boolean isSet  =   false;
        protected static Integer INVALID_ID;
        protected static Integer ID;

        protected static String SUBJECT;
        protected static String AGENT  ;
        protected static String NEW_AGENT;
        protected static String TAG_STRING;

        protected static String NULL_SUBJECT    =   null;
        protected static String NULL_AGENT      =   null;
        protected static String NEW_NULL_AGENT  =   null;

        protected static Set TAGS;
        protected static Set NEW_TAGS;

        protected static Set NULL_TAGS          =   null;
        protected static Set NEW_NULL_TAGS      =   null;

        protected static TicketService service;

        protected static void initialize(){

            Data.ID         =   0;
            Data.INVALID_ID =   -1;
            Data.SUBJECT    =   "test-Data.SUBJECT";
            Data.AGENT      =   "test-Data.AGENT1";
            Data.NEW_AGENT  =   "test-Data.AGENT2";
            Data.TAG_STRING =   "new tag";
            Data.TAGS       =   new HashSet<>(Arrays.asList("tag1","tag2"));
            Data.NEW_TAGS   =   new HashSet<>(Arrays.asList(TAG_STRING));
            Data.service    =   new TicketService();

            Data.isSet = true;

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
        Data.service = new TicketService();
    }

    /**
     * method that runs after every test case in the class
     */
    @After
    public void afterTest()
            throws IOException, NotFoundException{
//        service.cleanTestData();
    }

    /* ====================== Create Ticket test cases ====================== */
    /**
     * scenario : all relevant data is supplied to create ticket
     */
    @Test
    public void testCreateTicket(){

        Ticket ticket = null;

        try{
            ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        }catch (IOException | IncompleteDataException | ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        assertEquals(Data.SUBJECT,ticket.getSubject());
        assertEquals(Data.AGENT,ticket.getAgent());
        assertEquals(Data.TAGS, ticket.getTags());

    }
    /**
     * scenario : Data.TAGS are not supplied
     */
    @Test
    public void testCreateTicketWithNoTags(){

        Ticket ticket = null;

        try{
            ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.NULL_TAGS);
        }catch (IOException | IncompleteDataException | ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        assertEquals(Data.SUBJECT,ticket.getSubject());
        assertEquals(Data.AGENT,ticket.getAgent());
        assertTrue(ticket.getTags().isEmpty());

    }
    /**
     * scenario : Data.AGENT is not supplied
     */
    @Test(expected=IncompleteDataException.class)
    public void testCreateTicketWithNoAgent()
            throws IncompleteDataException, IOException,
            NotFoundException, ClassNotFoundException {

        Data.AGENT = null;
        Data.service.createTicket(Data.SUBJECT, Data.NULL_AGENT, Data.TAGS);
    }
    /**
     * scenario : Data.AGENT is not supplied
     */
    @Test(expected=IncompleteDataException.class)
    public void testCreateTicketWithNoSubject()
            throws IncompleteDataException, IOException,
            NotFoundException, ClassNotFoundException {

        Data.SUBJECT = null;
        Data.service.createTicket(Data.NULL_SUBJECT, Data.AGENT, Data.TAGS);
    }

    /* ====================== Update Ticket test cases ====================== */
    @Test
    public void testUpdateTicketWithAgent(){

        Ticket ticket, result = null;

        try{

            ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
            //update Data.AGENT
            Data.ID = ticket.getId();
            result = Data.service.updateTicket(Data.ID, Data.NEW_AGENT, Data.NEW_NULL_TAGS);

        }catch (IOException | IncompleteDataException | NoUpdateException |
                ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        //check updates
        assertEquals(Data.NEW_AGENT.toLowerCase(), result.getAgent().toLowerCase());

    }

    @Test
    public void testUpdateTicketWithTag(){

        Ticket ticket, result = null;

        try{
            ticket = Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);

            Data.ID = ticket.getId();
            result = Data.service.updateTicket(Data.ID, Data.NEW_NULL_AGENT, Data.NEW_TAGS);

        }catch (IOException | IncompleteDataException | NoUpdateException
                | ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        //check updates
        assertTrue(result.getTags().contains(Data.TAG_STRING));

    }

    @Test(expected = NoUpdateException.class)
    public void testUpdateTicketWithNoData()
            throws NoUpdateException, IOException ,IncompleteDataException ,
            ClassNotFoundException , NotFoundException{


        Ticket ticket=Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();
        Data.service.updateTicket(Data.ID, Data.NEW_NULL_AGENT, Data.NEW_NULL_TAGS);

    }

    @Test(expected = NotFoundException.class)
    public void testUpdateTicketWithInvalidId()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.service.updateTicket(Data.INVALID_ID, Data.NEW_AGENT, Data.NEW_TAGS);
    }

    /* ====================== delete Ticket test cases ====================== */

    @Test
    public void testDeleteTicket()
            throws NoUpdateException, IOException ,IncompleteDataException ,
            ClassNotFoundException , NotFoundException{

        Ticket ticket=Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.ID = ticket.getId();
        Data.service.deleteTicket(Data.ID);


    }

   /* @Test(expected = NotFoundException.class)
    public void testDeleteTicketWithInvalidId()
            throws NotFoundException, IOException, NoUpdateException,
            ClassNotFoundException, IncompleteDataException{

        Integer invalidId = -1;
        String newAgent = "new Data.AGENT";
        String tagString = "new tag";
        Set<String> newTag = new HashSet<>(Arrays.asList(tagString));
        Data.service.createTicket(Data.SUBJECT, Data.AGENT, Data.TAGS);
        Data.service.delete(invalidId, newAgent, newTag);
    }*/
    /* ====================== get Ticket test cases ====================== */
    @Test
    public void testGetTicket(){
        

    }





}
