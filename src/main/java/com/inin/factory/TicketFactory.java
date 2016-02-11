package com.inin.factory;

import com.inin.model.Ticket;
import com.inin.service.core.TicketService;
import com.inin.service.core.TicketServiceImpl;
import com.inin.service.report.TicketReportService;
import com.inin.service.report.TicketReportServiceImpl;
import com.inin.service.serialize.TicketSerializeServiceImpl;
import com.inin.service.serialize.TicketSerializeService;

import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketFactory {
    public static int counter;

    /**
     * Create new Ticket with subject, name and tags
     * @param subject
     * @param agent
     * @param tags
     * @return Ticket
     */
    public static Ticket newTicketInstance(String subject, String agent, Set<String> tags){
        int id = getTicketId();
        return new Ticket(id, subject, agent, tags);
    }

    /**
     * Create new copy of passed Ticket
     * @param ticket
     * @return Ticket
     */
    public static Ticket newTicketInstance(Ticket ticket){
        return new Ticket(ticket);
    }

    /**
     * provides new TicketServiceImpl object for TicketService
     * @return Object
     */
    public static TicketService newTicketServiceInstance(){  return new TicketServiceImpl(); }

    /**
     * provides new TicketReportServiceImpl object for TicketReportService
     * @return Object
     */
    public static TicketReportService newTicketReportServiceInstance(){ return new TicketReportServiceImpl();}

    /**
     * provides new TicketSerializeServiceImpl object for TicketSerializeService
     * @return Object
     */
    public static TicketSerializeService newTicketSerializeServiceInstance(){  return new TicketSerializeServiceImpl(); }

    private static int getTicketId(){
        return ++counter;
    }
}
