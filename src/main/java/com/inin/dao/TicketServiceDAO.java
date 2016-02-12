package com.inin.dao;

import com.inin.model.Ticket;

import java.util.List;
import java.util.Set;

/**
 * Created by root on 11/2/16.
 */
public interface TicketServiceDAO {
    int create(String subject,String agent, Set<String> tags);
    Ticket update(int id, String agent, Set<String> tags);
    boolean delete(int id);
    Ticket findById(int id);
    List<Ticket> findAll();
}
