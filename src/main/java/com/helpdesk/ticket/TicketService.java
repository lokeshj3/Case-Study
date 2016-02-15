package com.helpdesk.ticket;

import com.helpdesk.exception.DuplicateTicketIdException;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.InvalidTicketDAOFactoryTypeException;
import com.helpdesk.exception.TicketNotFoundException;
import com.helpdesk.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketService {

    TicketDAO ticketDAO;
    static Logger logger = LoggerFactory.getLogger(TicketService.class);

    public TicketService() throws InvalidTicketDAOFactoryTypeException {
        ticketDAO = TicketDAOFactory.get(TicketDAOFactoryType.INMEMORY);
    }

    /**
     * create new Ticket
     *
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return
     * @throws DuplicateTicketIdException
     * @throws InvalidParamsException
     */

    public final Ticket createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags) throws DuplicateTicketIdException, InvalidParamsException {

        if (ticketId > 0 && Helper.isStringValid(subject) && Helper.isStringValid(agentName)) {
            if (setOfTags.size() > 0) {
                return ticketDAO.create(new Ticket.Builder(ticketId, subject, agentName).withTags(setOfTags).build());
            } else {
                return ticketDAO.create(new Ticket.Builder(ticketId, subject, agentName).build());
            }
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");

    }

    /**
     * update Agent
     *
     * @param ticketId
     * @param agentName
     * @return Ticket
     * @throws InvalidParamsException
     * @throws TicketNotFoundException
     */

    public final Ticket updateAgent(int ticketId, String agentName) throws InvalidParamsException, TicketNotFoundException {
        if (ticketId > 0 && Helper.isStringValid(agentName)) {
            Ticket ticket = ticketDAO.find(ticketId);
            ticket.setAgentName(agentName);
            return ticketDAO.update(ticket);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");
    }

    /**
     * update Tags
     *
     * @param ticketId
     * @param tags
     * @return Ticket
     * @throws InvalidParamsException
     * @throws TicketNotFoundException
     */
    public final Ticket updateTags(int ticketId, Set<String> tags) throws InvalidParamsException, TicketNotFoundException {
        if (ticketId > 0 && Helper.isCollectionValid(tags)) {
            Ticket ticket = ticketDAO.find(ticketId);
            ticket.setTags(tags);
            return ticketDAO.update(ticket);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");
    }

    /**
     * update Agent And Tags
     *
     * @param ticketId
     * @param agentName
     * @param tags
     * @return Ticket
     * @throws InvalidParamsException
     * @throws TicketNotFoundException
     */

    public final Ticket updateAgentAndTags(int ticketId, String agentName, Set<String> tags) throws InvalidParamsException, TicketNotFoundException {
        if (ticketId > 0 && Helper.isCollectionValid(tags) && Helper.isStringValid(agentName)) {
            Ticket ticket = ticketDAO.find(ticketId);
            ticket.setTags(tags);
            ticket.setAgentName(agentName);
            return ticketDAO.update(ticket);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");
    }

    /**
     * Delete Ticket
     *
     * @param ticketId
     * @return boolean
     * @throws InvalidParamsException
     * @throws TicketNotFoundException
     */

    public final boolean deleteTicket(int ticketId) throws InvalidParamsException, TicketNotFoundException {
        if (ticketId > 0) {
            return ticketDAO.delete(ticketId);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");
    }

    /**
     * get Ticket Detail
     *
     * @param ticketId
     * @return Ticket
     * @throws TicketNotFoundException
     * @throws InvalidParamsException
     */
    public final Ticket getTicketDetail(int ticketId) throws TicketNotFoundException, InvalidParamsException {
        if (ticketId > 0) {
            return ticketDAO.find(ticketId);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");

    }

    /**
     * get List
     *
     * @return List
     * @throws TicketNotFoundException
     */
    public final List<Ticket> getTicketList() throws TicketNotFoundException {
        return ticketDAO.findAll();
    }

    /**
     * get List By AgentName
     *
     * @param agentName
     * @return List
     * @throws InvalidParamsException
     * @throws TicketNotFoundException
     */
    public final List<Ticket> getTicketListDetailByAgentName(String agentName) throws InvalidParamsException, TicketNotFoundException {

        if (Helper.isStringValid(agentName)) {
            return ticketDAO.findAllByAgentName(agentName);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");
    }

    /**
     * get List by tags
     *
     * @param tag
     * @return List
     * @throws TicketNotFoundException
     * @throws InvalidParamsException
     */
    public final List<Ticket> getTicketListDetailByTag(String tag) throws TicketNotFoundException, InvalidParamsException {
        if (Helper.isStringValid(tag)) {
            return ticketDAO.findAllByTag(tag);
        }
        logger.error("Invalid Param");
        throw new InvalidParamsException("Invalid Param");
    }

    /**
     * Agent With Ticket Count
     *
     * @return Map
     * @throws TicketNotFoundException
     */
    public final Map<String, Integer> getAllAgentWithTicketCount() throws TicketNotFoundException {
        return ticketDAO.findAllAgentWithTicketCount();
    }
}
