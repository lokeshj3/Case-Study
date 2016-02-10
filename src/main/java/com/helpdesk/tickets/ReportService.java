package com.helpdesk.tickets;

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

    public List<TicketModel> getTicketsOlderThanNDays(int days){
        //logic for calculating ticket older than x days
        return new ArrayList<TicketModel>();
    }

    public TicketModel getOldestTicketInSystem(){
        //logic for oldest ticket in the system
        return new TicketModel();
    }
}
