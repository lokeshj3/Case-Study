package com.ticketmaster.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by root on 10/2/16.
 */
public class CustomLogger {

    /**
     * constructor
     * @param classz
     */
    private CustomLogger(Class classz){
        logger = LoggerFactory.getLogger(classz);
    }

    public static CustomLogger init(){
        return new CustomLogger(CustomLogger.class);
    }

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
