package com.inin.service;

import com.inin.exception.TicketNotFoundException;
import com.inin.model.Ticket;

import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public interface TicketSerializeService {
    int create(String subject, String agent, Set<String> tags);
    Ticket update(int id, String agent, Set<String> tags) throws TicketNotFoundException;
    boolean delete(int id);
    Ticket ticket(int id) throws TicketNotFoundException;
    List<Ticket> tickets();
}
