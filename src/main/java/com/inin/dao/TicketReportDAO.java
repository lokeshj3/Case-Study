package com.inin.dao;

import com.inin.model.Ticket;

import java.util.List;

/**
 * Created by root on 11/2/16.
 */
public interface TicketReportDAO {
    Ticket find(int id);
    List<Ticket> findAll();
    List<Ticket> findByAttribute();
    int count();
}
