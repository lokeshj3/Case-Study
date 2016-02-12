package com.inin.service.core;

import com.inin.dao.MapRepository;
import com.inin.dao.MapTicketDAO;
import com.inin.dao.TicketReportDAO;
import com.inin.dao.TicketServiceDAO;
import com.inin.factory.TicketFactory;
import com.inin.model.Ticket;
import com.inin.service.report.TicketReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * Created by root on 12/2/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTestWithMockito {

    TicketService ticketService = new TicketServiceImpl();
    @InjectMocks
    private TicketServiceDAO ticketServiceDAO = new MapTicketDAO();

    // Create Test Cases here
    @Test
     public void testCreateTicket(){
        when(ticketServiceDAO.create("Test Subject", "Agent1", this.getDummyTags())).thenReturn(1);
        Assert.assertEquals(1, ticketService.create("Test Subject", "Agent1", this.getDummyTags()));
    }


    public Set<String> getDummyTags(){
        return new HashSet<>(Arrays.asList("Tag1", "Tag2", "Tag3"));
    }
}
