package com.inin.util;

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
}
