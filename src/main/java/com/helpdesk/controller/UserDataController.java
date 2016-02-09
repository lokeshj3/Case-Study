package com.helpdesk.controller;

import com.helpdesk.components.Util;

import java.util.Arrays;
import java.util.HashSet;

public class UserDataController extends  TicketController{
   public void create(){
        System.out.println("Enter Ticket Subject : ");
        String subject = Util.readString();
        System.out.println("Enter Agent Name : ");
        String agentName = Util.readString();
        System.out.println("Enter Tags (separated by comma(,) : ");
        String tags = Util.readString();
        HashSet<String> tagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
        this.create(subject,agentName,tagSet);
    }

    public  void update() {
        System.out.println("Enter ticket Id to update : ");
        int id = Util.readInteger();
        if(this.isTicketExist(id)) {
            System.out.println("Enter Agent Name : ");
            String agentName = Util.readString();
            System.out.println("Enter A-adding new tags / R-remove existing tag / N-no");
            String action = Util.readString();
            HashSet<String> tags = null;
            if (!action.isEmpty() && (action.equals("A") || action.equals("R"))) {
                System.out.println("Enter tags separated by comma(,) : ");
                String tag = Util.readString();
                tags = new HashSet<>(Arrays.asList(tag.split(",")));
            }
            this.update(id, agentName, tags, action);
        }
        else    System.out.println("Entered Ticket id " + id + " is not present in the system.");
    }

    public void delete() {
        System.out.println("Enter ticket Id for deletion : ");
        int id = Util.readInteger();
        this.delete(id);
    }

    public void getTicket(){
        System.out.println("Enter ticket Id for showing details : ");
        int id = Util.readInteger();
        this.getTicket(id);
    }

    public void getTicketsByAgent() {
        System.out.println("Please enter Agent Name : ");
        String agentName = Util.readString();
        this.getTicketsByAgent(agentName);
    }

    public void getTicketsByTag() {
        System.out.println("Enter tag to search ticket(s) : ");
        String tag = Util.readString();
        this.getTicketsByTag(tag);
    }

    public void Tickets() {
        this.getTickets();
    }

    public void AllAgentsTicketCount(){
        this.getAllAgentsTicketCount();
    }

    public void TotalTicketCount(){
        this.getTotalTicketCount();
    }

    public void OldestTicket(){
        this.getOldestTicket();
    }

    public void getOlderTicketsThanDays() {
        System.out.println("Enter day to get tickets older than a entered number of days");
        int days = Util.readInteger();
        this.getOlderTicketsThanDays(days);
    }
}
