package com.inin;

import com.inin.factory.TicketFactory;
import com.inin.logger.TLogger;
import com.inin.model.Ticket;
import com.inin.service.serialize.TicketSerializeService;
import com.inin.util.TicketUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        TLogger.writeLog(Level.INFO,"asdfsdfs");

        System.out.println( "Hello World!" );

//        //Serialization test
//        TicketSerializeService ticketSerializeService = TicketFactory.newTicketSerializeServiceInstance();
//
//        Ticket ticket1 = TicketFactory.newTicketInstance("test","agent1",new HashSet<>(Arrays.asList("wewew1","wewew2")));
//        ticketSerializeService.serializeTicket(ticket1);
////        System.out.println(ticketSerializeService.deserializeTicket());
//        List<Ticket> ticketList = ticketSerializeService.deserializeTickets();
//        System.out.println(ticketList.size());
//        List<Set<String>> collect = ticketList.stream()
//                .map(ticket -> ticket.getTags())
//                .collect(Collectors.toList());
//        System.out.println(
//                ticketList.stream()
//                .map(ticket -> ticket.getTags())
//                .collect(Collectors.groupingBy(o -> o.toString()))
//
//        );
//        ticketSerializeService.serializeTickets(ticketList);

//        System.out.println(ticketSerializeService.deserializeTickets());


    }
}
