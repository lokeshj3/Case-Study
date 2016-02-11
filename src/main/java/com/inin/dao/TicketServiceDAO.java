package com.inin.dao;

import com.inin.model.Ticket;

import java.util.List;

/**
 * Created by root on 11/2/16.
 */
public interface TicketServiceDAO {
    int create(Ticket ticket);
    Ticket update(int id,Ticket ticket);
    boolean delete(int id);
    Ticket findById(int id);
    List<Ticket> findAll();
}
