package com.helpdesk.ticket;

import com.helpdesk.exception.InvalidTicketDAOFactoryTypeException;

/**
 * Created by root on 10/2/16.
 */
public class TicketDAOFactory {
    public static TicketDAO get(TicketDAOFactoryType ticketDAOFactoryType) throws InvalidTicketDAOFactoryTypeException {
        switch (ticketDAOFactoryType) {
            case INMEMORY:
                return new TicketDAOInMemoryImpl();
            default:
                throw new InvalidTicketDAOFactoryTypeException("invalid type");
        }
    }
}
