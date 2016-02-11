package com.helpdesk.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class Helper {

    /**
     * convert Set to string
     *
     * @param set
     * @return
     */
    public static String convertSetToString(Set set) {
        return isCollectionValid(set) ? set.toString() : "";
    }

    /**
     * check if collections is valid
     *
     * @param collection
     * @return
     */
    public static boolean isCollectionValid(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * check if map is valid
     *
     * @param map
     * @return
     */
    public static boolean isMapValid(Map map) {
        return map != null && !map.isEmpty();
    }

    /**
     * check if string is valid
     *
     * @param s
     * @return
     */
    public static boolean isStringValid(String s) {
        return s != null && !s.isEmpty();
    }
}
