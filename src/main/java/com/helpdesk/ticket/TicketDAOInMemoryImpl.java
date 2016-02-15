package com.helpdesk.ticket;


import com.helpdesk.exception.DuplicateTicketIdException;
import com.helpdesk.exception.TicketNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 9/2/16.
 */
public class TicketDAOInMemoryImpl implements TicketDAO {
    static Logger logger = LoggerFactory.getLogger(TicketDAOInMemoryImpl.class);

    TicketInMemoryStorage ticketInMemoryStorage = TicketInMemoryStorage.newInstance();

    /**
     * create eticket
     *
     * @param ticket
     * @return
     * @throws DuplicateTicketIdException
     */
    public Ticket create(Ticket ticket) throws DuplicateTicketIdException {
        int ticketId = ticket.getId();
        if (isExist(ticketId)) {
            logger.error("Duplicate Ticket Id");
            throw new DuplicateTicketIdException("Duplicate Ticket Id");
        }
        ticketInMemoryStorage.writeData(ticketId, ticket);

        return new Ticket(ticket);
    }

    /**
     * update Ticket
     *
     * @param ticket
     * @return
     * @throws TicketNotFoundException
     */
    public Ticket update(Ticket ticket) throws TicketNotFoundException {
        int ticketId = ticket.getId();
        if (isExist(ticketId)) {
            ticketInMemoryStorage.writeData(ticketId, ticket);
            return new Ticket(ticket);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    /**
     * @param ticketId
     * @return
     */
    public boolean isExist(int ticketId) {
        return ticketInMemoryStorage.getTicketData().containsKey(ticketId);
    }

    /**
     * delete ticket
     *
     * @param ticketId
     * @return
     * @throws TicketNotFoundException
     */
    public boolean delete(int ticketId) throws TicketNotFoundException {
        if (isExist(ticketId)) {
            ticketInMemoryStorage.deleteData(ticketId);
            return true;
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket not found");
    }

    /**
     * find Single ticket
     *
     * @param ticketId
     * @return
     * @throws TicketNotFoundException
     */
    public Ticket find(int ticketId) throws TicketNotFoundException {
        if (isExist(ticketId)) {
            return new Ticket(ticketInMemoryStorage.getTicketData().get(ticketId));
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    /**
     * FindAll
     *
     * @return
     * @throws TicketNotFoundException
     */
    public List<Ticket> findAll() throws TicketNotFoundException {

        List<Ticket> arrList = ticketInMemoryStorage.getTicketData().values()
                .stream()
                .sorted((Ticket o1, Ticket o2) -> o2.getModified().compareTo(o1.getModified()))
                .collect(Collectors.toList());
        if (!arrList.isEmpty()) {
            return Collections.unmodifiableList(arrList);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    /**
     * Calculate total number of tickets present in the system
     * @return count
     */
    public int getTotalTicketInSystem() {
        logger.info("Entered Calculate total number of tickets present in the system function");
        return ticketInMemoryStorage.getTicketData().size();
    }

    /**
     * Calculate oldest ticket present in the system
     * @return oldest ticket
     */
    public Ticket findOldestTicketInSystem() {
        logger.info("Entered find oldest ticket in system function");
        return ticketInMemoryStorage.getTicketData().values().stream().max( (ticket1, ticket2) -> ticket2.getCreated().compareTo(ticket1.getCreated())).get();
    }

    /**
     * Calculate ticket count for all tags
     * @return tags with ticket count
     */
    public Map<String, Integer> findAllTagsWithTicketCount() {
        logger.info("Entered tags with ticket count function");
        Map<String, Integer> hashMap = new HashMap<>();
        ticketInMemoryStorage.getTicketData().values().forEach(ticket -> {
                    for (String tags : ticket.getTags()) {
                        if (hashMap.containsKey(tags)) {
                            hashMap.put(tags, hashMap.get(tags) + 1);
                        } else {
                            hashMap.put(tags, 1);
                        }
                    }

                }
        );
        return hashMap;
    }

    /**
     * Calculate tickets older than specified N number of days
     * @param noofdays
     * @return list of tickets
     */
    public List<Ticket> findAllOlderThanNDays(int noofdays) {
        logger.info("In find oldest ticket in system function");
        LocalDateTime olderDays = LocalDateTime.now().minus(noofdays, ChronoUnit.DAYS);
        return ticketInMemoryStorage.getTicketData().values().stream().filter(ticket -> ticket.getCreated().isBefore(olderDays)).collect(Collectors.toList());

    }

    /**
     * find ticket by AgentName
     *
     * @param agentName
     * @return
     * @throws TicketNotFoundException
     */
    public List<Ticket> findAllByAgentName(String agentName) throws TicketNotFoundException {

        List<Ticket> arrList = ticketInMemoryStorage.getTicketData().values().stream()
                .filter(t -> t.getAgentName().equalsIgnoreCase(agentName))
                .collect(Collectors.toList());

        if (!arrList.isEmpty()) {
            return Collections.unmodifiableList(arrList);
        }
        logger.error("");
        throw new TicketNotFoundException("Ticket Not Found");

    }

    /**
     * find ticket by tag
     *
     * @param tag
     * @return
     * @throws TicketNotFoundException
     */
    public List<Ticket> findAllByTag(String tag) throws TicketNotFoundException {

        List<Ticket> arrList = ticketInMemoryStorage.getTicketData().values().stream()
                .filter(t -> t.getTags().contains(tag))
                .collect(Collectors.toList());

        if (!arrList.isEmpty()) {
            return Collections.unmodifiableList(arrList);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }

    /**
     * agent with ticket count
     *
     * @return
     * @throws TicketNotFoundException
     */
    public Map<String, Integer> findAllAgentWithTicketCount() throws TicketNotFoundException {

        TreeMap<String, Integer> tmCount = new TreeMap<>();

        ticketInMemoryStorage.getTicketData().values().stream().forEach(ticket -> {
            if (tmCount.containsKey(ticket.getAgentName())) {
                tmCount.put(ticket.getAgentName(), tmCount.get(ticket.getAgentName()) + 1);
            } else {
                tmCount.put(ticket.getAgentName(), 1);
            }
        });

        if (!tmCount.isEmpty()) {
            return Collections.unmodifiableMap(tmCount);
        }
        logger.error("Ticket not found");
        throw new TicketNotFoundException("Ticket Not Found");
    }
}
