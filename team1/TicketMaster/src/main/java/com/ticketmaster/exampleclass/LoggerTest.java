package com.ticketmaster.exampleclass;

import com.ticketmaster.service.TicketService;
import com.ticketmaster.utils.CustomLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by root on 10/2/16.
 */
public class LoggerTest {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(LoggerTest.class);

        CustomLogger.init(TicketService.class).error("Info Message");
        CustomLogger.init(LoggerTest.class).debug("Info Message");
        CustomLogger.init(LoggerTest.class).info("Info Message");
        CustomLogger.init(LoggerTest.class).trace("Info Message");

        CustomLogger.init().error("New Message");

    }
}
