package com.helpdesk;

import org.junit.*;

import java.util.*;

public class TestTicketService {
    private static int ticketId;
    private static String invalidTicketId;
    private static String subject;
    private static String nullSubject;
    private static String emptySubject;
    private static String emptyAgent;
    private static String nullAgent;
    private static String updateAgent;
    private static String agent;
    private static String tags;
    private static Set<String> tagSet;
    private static Set<String> emptyTagSet;
    private static Set<String> duplicateTagSet;
    private static String updatetags;
    private static Set<String> updateTagSet;

    // you can add more variables if u want

    private static void initialize(){
        ticketId = 100;
        invalidTicketId = "xyz";
        subject = "First Ticket";
        nullSubject = null;
        emptySubject = "";
        agent = "Admin";
        nullAgent = null;
        emptyAgent = "";
        updateAgent = "New Admin";
        tags = "mumbai,delhi,pune";
        tagSet =  new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
        emptyTagSet = new HashSet<>();
        tags = tags +", mumbai";
        duplicateTagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
        updatetags = "goa, surat";
        updateTagSet = new HashSet<>(Arrays.asList(updatetags.toLowerCase().split(",")));
    }

    @BeforeClass
    public static void initializeClassResources(){
        initialize();
    }

    @Before
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

    }
}
