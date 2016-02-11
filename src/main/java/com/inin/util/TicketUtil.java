package com.inin.util;

import com.inin.constant.TicketAttribute;

import java.util.Collection;

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
     * Return the TicketAttribute constant value
     * @param ticketAttribute
     * @return
     */
    public static String getAtributeNameValue(TicketAttribute ticketAttribute)
    {
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
