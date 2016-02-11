package com.inin;

import com.inin.factory.TicketFactory;
import com.inin.model.Ticket;
import com.inin.service.serialize.TicketSerializeService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

//        //Serialization test
//        TicketSerializeService ticketSerializeService = TicketFactory.newTicketSerializeServiceInstance();
//
//        Ticket ticket = TicketFactory.newTicketInstance("test","agent1",new HashSet<>(Arrays.asList("wewew","wewew")));
//        ticketSerializeService.serializeTicket(ticket);
//        System.out.println(ticketSerializeService.deserializeTicket());
//        List<Ticket> ticketList = ticketSerializeService.deserializeTickets();
//        ticketSerializeService.serializeTickets(ticketList);
//
//        System.out.println(ticketSerializeService.deserializeTickets());


    }
}
