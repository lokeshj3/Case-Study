package com.helpdesk.logger;

import com.helpdesk.components.Util;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by root on 8/2/16.
 * To write into log files
 */
public class TicketLogger {

    private static FileHandler fileHandler;
    private static Logger logger;

    /**
     * initialize the logger and handler*/
    static {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setUseParentHandlers(false);
        //File file = Util.createFile("target/generated-sources/logs/","logs.log");
        try {
            // Why Not creating Directory and file by code? This will force to create directory and file manually first.
            fileHandler = new FileHandler("target/generated-sources/logs/logs.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);

        logger.addHandler(fileHandler);
    }


    /**
     * write into the log file
     * @param level
     * @param message
     * */
    public static void writeLog(Level level, String message){

            message = getClassMethodLine() + message;
            logger.setLevel(level);

            if(level == Level.INFO)
                logger.info(message);

            if(level == Level.WARNING)
                logger.warning(message);

            if(level == Level.SEVERE)
                logger.severe(message);

            if(level == Level.FINE)
                logger.fine(message);

            if(level == Level.CONFIG)
                logger.config(message);
    }


    /**
     * Get the class name method name and line number from which Log is written
     * @return string
     * */
    public static String getClassMethodLine(){
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        return "[ "+className+" : "+ methodName + " : "+ lineNumber+ " ] :: ";
    }
}
