package com.helpdesk;

import com.helpdesk.exception.TicketNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {

    /**DA:
     * What is the use of this class? If it is for testing your logs please remove. */
    static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        System.out.println("Hello World!");

        logger.info("Hello World");

        logger.warn("Hello World");

        logger.error("Hello World",new TicketNotFoundException("Ticket not Fount"));
    }
}
