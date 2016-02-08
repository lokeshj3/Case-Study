package com.inin.service;

import com.inin.exception.TicketNotFoundException;
import com.inin.model.Ticket;

import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public interface TicketService {
    public int create(String subject, String agent, Set<String> tags);
    public Ticket update(int id, String agent, Set<String> tags) throws TicketNotFoundException;
    public boolean delete(int id);
    public Ticket ticket(int id) throws TicketNotFoundException;
    public List<Ticket> tickets();
}
