package com.helpdesk.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 8/2/16.
 */
public class ReportService {

    public long getTicketsCountInSystem(){
        //logic for calculating ticket count in system
        return 0L;
    }

    public Map<String, Integer> getTagsWithTicketCount(){
        //logic for calculating tags count in ticket
        return new HashMap<String, Integer>();
    }

    public List<Ticket> getTicketsOlderThanNDays(int days){
        //logic for calculating ticket older than x days
        return new ArrayList<Ticket>();
    }

    public Ticket getOldestTicketInSystem(){
        //logic for oldest ticket in the system
        return new Ticket();
    }
}
