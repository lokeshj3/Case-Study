package com.inin.service.core;

import com.inin.dao.MapTicketDAO;
import com.inin.dao.TicketServiceDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @InjectMocks
    TicketService ticketService = new TicketServiceImpl();

    @Mock
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
