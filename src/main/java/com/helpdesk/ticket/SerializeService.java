package com.helpdesk.ticket;


import com.helpdesk.exception.DuplicateTicketIdException;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.InvalidTicketDAOFactoryTypeException;
import com.helpdesk.util.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 12/2/16.
 */
public class SerializeService {

    TicketService ticketService;
    TicketSerialize ticketSerialize;

    static int limit = 10;

    SerializeService() throws InvalidTicketDAOFactoryTypeException {
        ticketService = new TicketService();
        ticketSerialize = new TicketSerializeFileImpl();
    }

    public static void main(String[] args) throws InvalidTicketDAOFactoryTypeException {
        SerializeService serializeService = new SerializeService();
        serializeService.singleTicketSerializeDeserialize();
        serializeService.multiTicketSerializeDeserialize();
    }

    /**
     * Serialize and deserialize single ticket
     */
    public void singleTicketSerializeDeserialize() {
        try {
            Set<String> setOfTags = new HashSet<>();
            setOfTags.add("issue");
            setOfTags.add("Hardware");

            Ticket ticket = ticketService.createTicket(1, "subject", "agent", setOfTags);

            if (ticketSerialize.serializeSingleTicket(ticket)) {
                System.out.println("serialization successfull");

                Ticket ticket1 = ticketSerialize.deSerializeSingleTicket();
                if (ticket1 != null) {
                    System.out.println("De - serialization done successfully " + ticket1);
                } else {
                    System.out.println("De - serialization unsuccessfull");
                }

            } else {
                System.out.println("serialization un-successfull");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Ticket class not found");
        } catch (InvalidParamsException e) {
            System.out.println("Invalid input");
        } catch (DuplicateTicketIdException e) {
            System.out.println("Ticket ID already exist");
        }
    }

    /**
     * Serialize and deserialize multiple tickets
     */

    public void multiTicketSerializeDeserialize() {
        try {
            List<Ticket> arrListTickets = new ArrayList<>();

            for (int i = 2; i <= limit; i++) {
                Set<String> setOfTags = new HashSet<>();
                setOfTags.add("issue" + i);
                setOfTags.add("Hardware" + i);
                arrListTickets.add(ticketService.createTicket(i, "subject" + i, "agent" + i, setOfTags));
            }

            if (ticketSerialize.serializeMultiTicket(arrListTickets)) {
                System.out.println("serialization successfully for multiple tickets");
                arrListTickets.clear();
                arrListTickets = ticketSerialize.deSerializeMultiTicket();
                if (Helper.isCollectionValid(arrListTickets)) {
                    System.out.println("De - serialization done successfully" + arrListTickets);
                    arrListTickets.forEach(ticket -> System.out.println(ticket));
                } else {
                    System.out.println("De - serialization unsuccessfully");
                }
            } else {
                System.out.println("serialization un-successfully for multiple tickets");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Ticket class not found");
        } catch (InvalidParamsException exp) {
            System.out.println("Invalid input");
        } catch (DuplicateTicketIdException e) {
            System.out.println("Ticket ID already exist");
        }
    }
}
