package com.ticketmaster.service;

import com.ticketmaster.exceptions.IncompleteDataException;
import com.ticketmaster.models.Ticket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * TestTicketService class
 * This class is used to test the methods available in the TicketService class
 * Created by Virendra on 8/2/16.
 */
public class TestTicketService {

    TicketService service;

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
    @After
    public void afterTest(){
        service.clearTickets();
    }


    /**
     * scenario : all relevant data is supplied to create ticket
     */
    @Test
    public void testCreateTicket(){

        final String subject    =   "test-subject";
        final String agent      =   "agent1";
        final Set tags          =   new HashSet<>(Arrays.asList("tag1","tag2"));

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, agent, tags);
        }catch (IOException | IncompleteDataException e){
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

        final String subject    =   "test-subject";
        final String agent      =   "agent1";

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, agent, null);
        }catch (IOException | IncompleteDataException e){
            fail("Exception occurred :\n"+e.getMessage());
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

        final String subject    =   "test-subject";

        Ticket ticket = null;

        try{
            ticket = service.createTicket(subject, null, null);
        }catch (IOException | IncompleteDataException e){
            assertTrue(e.getMessage().equalsIgnoreCase("agent is required"));
        }
        assertFalse(ticket instanceof Ticket);
    }


    /**
     * scenario : agent is not supplied
     */
    @Test
    public void testCreateTicketWithNoSubject(){

        final String agent      =   "agent1";
        final Set tags          =   new HashSet<>(Arrays.asList("tag1","tag2"));

        Ticket ticket = null;

        try{
            ticket = service.createTicket(null, agent, tags);
        }catch (IOException | IncompleteDataException e){
            assertTrue(e.getMessage().equalsIgnoreCase("subject is required"));
        }
        assertFalse(ticket instanceof Ticket);
    }




}
