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
 * Created by root on 15/2/16.
 */
public class ReportServiceTest {

    protected static class Data {
        protected static boolean initialized = false;

        private static int int_ticketId;
        private static int int_invalidNoOfDay;
        private static int int_noOfDay;
        private static int int_ticketId2;
        private static String text_subject;
        private static String text_subject2;
        private static String text_agent;
        private static String text_agent2;
        private static String text_tags;
        private static String text_tags2;
        private static Set<String> set_tagSet;
        private static Set<String> set_tagSet2;
        private static ReportService reportService;


        private static void initialize() throws InvalidTicketDAOFactoryTypeException {
            int_noOfDay = 0;
            int_invalidNoOfDay = -1;
            int_ticketId = 100;
            int_ticketId2 = 101;
            text_subject = "First Ticket";
            text_subject2 = "Second Ticket";
            text_agent = "Yogesh";
            text_agent2 = "sweta";
            text_tags = "tag1,tag2,tag3";
            text_tags2 = "tag1,tag2,tag3";
            set_tagSet = new HashSet<>(Arrays.asList(text_tags.toLowerCase().split(",")));
            set_tagSet2 = new HashSet<>(Arrays.asList(text_tags2.toLowerCase().split(",")));
            text_tags = text_tags + ", tag4";
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
        Data.reportService = new ReportService();
    }


    @Test
    public void testTicketsCountInSystem() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.reportService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.reportService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet2);
        Assert.assertEquals(2, Data.reportService.getTicketsCountInSystem());
    }


    @Test
    public void testTagsWithTicketCount() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.reportService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.reportService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet2);
        Map map = Data.reportService.getTagsWithTicketCount();
        Assert.assertEquals(2, map.get("tag1"));
        Assert.assertEquals(2, map.get("tag2"));
        Assert.assertEquals(2, map.get("tag3"));
    }


    @Test
    public void testOldestTicketInSystem() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.reportService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.reportService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet2);
        Assert.assertEquals(Data.int_ticketId, Data.reportService.getOldestTicketInSystem().getId());
    }

    @Test(expected = InvalidParamsException.class)
    public void testTicketsOlderThanNDaysWithInvalidNoofDays() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {

        Data.reportService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.reportService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet2);
        Data.reportService.getTicketsOlderThanNDays(Data.int_invalidNoOfDay);

    }

    @Test
    public void testTicketsOlderThanNDays() throws InvalidTicketDAOFactoryTypeException, InvalidParamsException, DuplicateTicketIdException, TicketNotFoundException {
        Data.reportService.createTicket(Data.int_ticketId, Data.text_subject, Data.text_agent, Data.set_tagSet);
        Data.reportService.createTicket(Data.int_ticketId2, Data.text_subject2, Data.text_agent2, Data.set_tagSet2);
        List<Ticket> list = Data.reportService.getTicketsOlderThanNDays(Data.int_noOfDay);
        Assert.assertEquals(2, list.size());

    }


}
