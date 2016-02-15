package com.inin.util;

import com.inin.constant.TicketAttribute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

/**
 * Created by root on 8/2/16.
 */
public class TicketUtil {

    /**
     * Checking string for null and empty
     * @param str
     * @return boolean
     */
    public static boolean isValidString(String str)
    {
        return str != null && !str.isEmpty();
    }

    /**
     * Checking collection for null and empty
     * @param collection
     * @return boolean
     */
    public static boolean isValidCollection(Collection collection)
    {
        return collection !=null && !collection.isEmpty();
    }

    /**
     * Read the property file and return the specified property value
     * @return
     * @param property
     */
    public static String getProperty(String property)
    {
        Properties prop = new Properties();
        String propFileName = "config.properties";
        String propertyValue="";
        File file  = FileHandler.createFile(propFileName);
        try(FileInputStream fis =  new FileInputStream(file)) {
            if (fis != null)
                prop.load(fis);
            else
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            propertyValue = prop.getProperty(property);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return propertyValue;
    }

    /**
     * Return the TicketAttribute constant value
     * @param ticketAttribute
     * @return
     */
    public static String getAtributeNameValue(TicketAttribute ticketAttribute)
    {
        //VD: unused method
        String value = "";
        switch (ticketAttribute)
        {
            case ID:
                value = "id";
                break;

            case SUBJECT:
                value = "subject";
                break;

            case AGENT:
                value = "agent";
                break;

            case TAGS:
                value = "tags";
                break;

            case CREATED:
                value = "created";
                break;

            case MODIFIED:
                value = "modified";
                break;
        }
        return value;
    }
}
