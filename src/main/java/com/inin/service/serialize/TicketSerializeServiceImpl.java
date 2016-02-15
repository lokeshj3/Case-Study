package com.inin.service.serialize;

import com.inin.dao.DAOFactory;
import com.inin.dao.SerializeDAO;
import com.inin.model.Ticket;
import java.util.List;
/**
 * Created by root on 11/2/16.
 */
public class TicketSerializeServiceImpl implements TicketSerializeService{
    SerializeDAO serializeDAO = DAOFactory.newSerializeDAOInstance();
    /**
     * Serialize single ticket into file
     * @param ticket
     */
    @Override
    public void serializeTicket(Ticket ticket) {
        serializeDAO.serializeTicket(ticket);
    }

    /**
     * Serialize list of ticket into file
     * @param ticketList
     */
    @Override
    public void serializeTickets(List<Ticket> ticketList) {
        serializeDAO.serializeTickets(ticketList);
    }

    /**
     * Deserialize single ticket from file
     * @return
     */
    @Override
    public Ticket deserializeTicket() {
        return serializeDAO.deserializeTicket();
    }

    /**
     * Deserialize List of Ticket from file
     * @return
     */
    @Override
    public List<Ticket> deserializeTickets() {
        return serializeDAO.deserializeTickets();
    }
}
