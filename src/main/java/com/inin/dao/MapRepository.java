package com.inin.dao;

import com.inin.model.Ticket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by root on 12/2/16.
 */
public class MapRepository {

    private static Map<Integer, Ticket> ticketMap;
    public MapRepository(){
        ticketMap = new ConcurrentHashMap<Integer, Ticket>(16, 0.9f, 2);
    }

    public Map<Integer, Ticket> getTicketMap(){
        return ticketMap;
    }
}
