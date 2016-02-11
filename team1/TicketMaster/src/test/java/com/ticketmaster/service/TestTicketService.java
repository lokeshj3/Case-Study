package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.exceptions.NoUpdateException;
import com.ticketmaster.exceptions.NotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.AppUtil;
import com.ticketmaster.utils.CustomLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * TestTicketService class
 * This class is used to test the methods available in the TicketService class
 * Created by Virendra on 8/2/16.
 */
public class TestTicketService {

    protected String subject    =   "test-subject";
    protected String agent      =   "agent1";
    protected Set tags          =   new HashSet<>(Arrays.asList("tag1","tag2"));

    protected TicketService service;


    @BeforeClass
    public static void beforeAll() throws IOException, ClassNotFoundException {
        AppUtil.initializeApp();
    }

    /**
     * method that runs before every test case in this class
     */
    @Before
    public void beforeTest(){
        service = new TicketService();
    }

    /**
     * method that runs after every test case in the class
     */
   /* @After
    public void afterTest()
            throws IOException, NotFoundException{
        service.cleanTestData();
    }*/

    /* ====================== Create Ticket test cases ====================== */
    /**
     * scenario : all relevant data is supplied to create ticket
     */
    @Test
    public void testCreateTicket(){

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, agent, tags);
        }catch (IOException | IncompleteDataException | ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        assertEquals(subject,ticket.getSubject());
        assertEquals(agent,ticket.getAgent());
        assertEquals(tags, ticket.getTags());

    }
    /**
     * scenario : tags are not supplied
     */
    @Test
    public void testCreateTicketWithNoTags(){

        tags = null;

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, agent, tags);
        }catch (IOException | IncompleteDataException | ClassNotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }catch (NotFoundException e){

        }

        assertEquals(subject,ticket.getSubject());
        assertEquals(agent,ticket.getAgent());
        assertTrue(ticket.getTags().isEmpty());

    }
    /**
     * scenario : agent is not supplied
     */
    @Test
    public void testCreateTicketWithNoAgent(){

        agent = null;

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, agent, tags);
        }catch (IOException | NotFoundException | ClassNotFoundException e){

        }catch (IncompleteDataException e){
            assertTrue(e.getMessage().equalsIgnoreCase("agent is required"));
        }

        assertFalse(ticket instanceof Ticket);
    }
    /**
     * scenario : agent is not supplied
     */
    @Test
    public void testCreateTicketWithNoSubject(){

        subject = null;

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, agent, tags);
        }catch (IOException | NotFoundException | ClassNotFoundException e){

        }catch (IncompleteDataException e){
            assertTrue(e.getMessage().equalsIgnoreCase("subject is required"));
        }
        assertFalse(ticket instanceof Ticket);
    }

    /* ====================== Update Ticket test cases ====================== */
    @Test
    public void updateTicketWithAgent(){

        Integer id = 0;
        String newAgent = "agent2";
        Set<String> newTag = null;
        Ticket ticket = null, result = null;

        try{

            ticket = service.createTicket(subject, agent, tags);
            //update agent
            id = ticket.getId();
            result = service.updateTicket(id, newAgent, newTag);

        }catch (IOException | IncompleteDataException | NoUpdateException |
                ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        //check updates
        assertEquals(newAgent.toLowerCase(), result.getAgent().toLowerCase());

    }

    @Test
    public void updateTicketWithTag(){

        Integer id = 0;
        String newAgent = null;
        String tagString = "new tag";
        Set<String> newTag = new HashSet<>(Arrays.asList(tagString));
        Ticket ticket = null, result = null;

        try{
            ticket = service.createTicket(subject, agent, tags);

            id = ticket.getId();
            result = service.updateTicket(id, newAgent, newTag);

        }catch (IOException | IncompleteDataException | NoUpdateException
                | ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }

        //check updates
        assertTrue(result.getTags().contains(tagString));

    }

    @Test
    public void updateTicketWithNoData(){

        Integer id = 0;
        String newAgent = null;
        Set<String> newTag = null;
        Ticket ticket = null, result = null;

        try{
            ticket = service.createTicket(subject, agent, tags);

            id = ticket.getId();
            result = service.updateTicket(id, newAgent, newTag);

        }catch ( NoUpdateException  e){

            String expected = "Nothing to update";
            assertEquals(expected.toLowerCase(),e.getMessage().toLowerCase());
            assertNull(result);

        }catch(IOException | IncompleteDataException | ClassNotFoundException | NotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }


    }

    @Test
    public void updateTicketWithInvalidId(){

        Integer invalidId = -1;
        String newAgent = "new agent";
        String tagString = "new tag";
        Set<String> newTag = new HashSet<>(Arrays.asList(tagString));
        Ticket ticket = null, result = null;

        try{
            ticket = service.createTicket(subject, agent, tags);

            result = service.updateTicket(invalidId, newAgent, newTag);

        }catch ( NoUpdateException  e){
            String expected = "Nothing to update";
            assertEquals(expected.toLowerCase(),e.getMessage().toLowerCase());
            assertNull(result);

        }catch (NotFoundException e){
            String expected = "ticket not found";
            assertEquals(expected.toLowerCase(),e.getMessage().toLowerCase());
            assertNull(result);

        }catch(IOException | IncompleteDataException | ClassNotFoundException e){
            fail("Exception occurred :\n"+e.getMessage());
        }


    }

    /* ====================== get Ticket test cases ====================== */
   /* @Test
    public void testGetTicket(){

    }*/





}
