package com.ticketmaster.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomLogger class
 * Wrapper class for Logging. It is used to log the details to file and to print to the screen
 * Created by Virendra on 10/2/16.
 */
public class CustomLogger {

    /**
     * constructor
     * @param classz
     */
    private CustomLogger(Class classz){
        logger = LoggerFactory.getLogger(classz);
    }

    /**
     *
     * @return CustomerLogger class
     */
    public static CustomLogger init(){
        return new CustomLogger(CustomLogger.class);
    }

    /**
     * init method
     * @param classz
     * @return
     */
    public static CustomLogger init(Class classz){
        return new CustomLogger(classz);
    }

    public void error(String name){
        logger.error(name);
    }
    public void info(String name){
        logger.info(name);
    }
    public void debug(String name){
        logger.debug(name);
    }
    public void warn(String name){
        logger.warn(name);
    }
    public void trace(String name){
        logger.trace(name);
    }

    //property
    private static Logger logger;
}