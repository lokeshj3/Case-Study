package com.helpdesk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        logger.trace("Hello World");

        logger.debug("Hello World");

        logger.info("Hello World");

        logger.warn("Hello World");

        logger.error("Hello World");
    }
}
