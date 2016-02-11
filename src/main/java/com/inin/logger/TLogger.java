package com.inin.logger;

import com.inin.util.TicketUtil;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by root on 8/2/16.
 */
public class TLogger {
    private static FileHandler fileHandler;
    private static Logger logger;
    private static String file = TicketUtil.getProperty("logFile");

    /**
     * initialize the logger and handler*/
    public static void setLogger() throws IOException {

        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        fileHandler = new FileHandler("src/main/resources/"+file, true);
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
        try {
            setLogger();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            logger.removeHandler(fileHandler);
            fileHandler.close();
        }

    }

}
