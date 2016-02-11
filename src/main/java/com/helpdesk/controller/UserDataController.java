package com.helpdesk.controller;

import com.helpdesk.components.Util;
import com.helpdesk.model.Ticket;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class UserDataController extends  TicketController{
   public void create(){
        System.out.println("inside usercontroller");
        System.out.println("Enter Ticket Subject : ");
        String subject = Util.readString();
        System.out.println("Enter Agent Name : ");
        String agentName = Util.readString();
        System.out.println("Enter Tags (separated by comma(,) : ");
        String tags = Util.readString();
        HashSet<String> tagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));

        try{
            Ticket tickets = this.create(subject,agentName,tagSet);
            System.out.println("Ticket Has been created successfully." + tickets.toString());
        }
        catch (InvalidParameterException ie){
            System.out.println("There is error while creating a ticket.");
        }
    }

    public  void update() {
        System.out.println("Enter ticket Id to update : ");
        int id = Util.readInteger();
        if(this.isTicketExist(id)) {
            String agentName = null;
            HashSet<String> tagSet = null;
            String action = null;

            String choice = Util.readString("Do you want to update Agent? Enter y-Yes | n-No");
            if(choice.equals('y')){
                agentName = Util.readString("Enter Agent Name : ");
            }

            choice = Util.readString("Do you want to update Tags? Enter y-Yes | n-No");

            if(choice.equals('y')){
                action = Util.readString("Enter A-adding new tags / R-remove existing tag / N-no");

                if (!action.isEmpty() && (action.equals("A") || action.equals("R"))) {
                    String tag = Util.readString("Enter tags separated by comma(,) : ");
                    tagSet = new HashSet<>(Arrays.asList(tag.split(",")));
                }
            }

            if(!agentName.isEmpty() || !tagSet.isEmpty()) {
                try{
                    Ticket ticket = this.update(id, agentName, tagSet, action);
                    System.out.println("Ticket id " + id + " is updated successfully");
                    System.out.println(ticket.toString());
                }
                catch (InvalidParameterException ie){
                    System.out.println("Invalid params!!!");
                }
            }
            else
            System.out.println("Update Operation Skipped");
        }
        else
            System.out.println("Entered Ticket id " + id + " is not present in the system.");
    }

    public void delete() {
        System.out.println("Enter ticket Id for deletion : ");
        int id = Util.readInteger();

        if(this.delete(id)) {
            System.out.println("Ticket id " + id + " is deleted successfully.");
        }
        else  System.out.println("Entered ticket id " + id + " is not present in the system." );
    }

    public void getTicket(){
        System.out.println("Enter ticket Id for showing details : ");
        int id = Util.readInteger();
        try {
            Ticket ticket = this.getTicket(id);;
            System.out.println("Details of ticket id : " + id + "\n\n" + ticket.toString());
        }catch (InvalidParameterException e){
            System.out.println("Invalid ticket Id . Entered ticket id " + id + " is not present in the system.");
        }
    }

    public void getTicketsByAgent() {
        System.out.println("Please enter Agent Name : ");
        String agentName = Util.readString();

        if(!agentName.isEmpty()){
            List<Ticket> agentTicketList = this.getTicketsByAgent(agentName);
            if(agentTicketList.size()>0){
                agentTicketList.forEach(ticket -> System.out.println(ticket.toString()));
            }
            else  System.out.println("No records found for entered agent " + agentName);
        }
        else    System.out.println("Invalid agent Name!!!");
    }

    public void getTicketsByTag() {
        System.out.println("Enter tag to search ticket(s) : ");
        String tag = Util.readString();

        if(!tag.isEmpty()){
            List<Ticket> ticketList = this.getTicketsByTag(tag);
            if(ticketList.size()>0)
            {
                ticketList.forEach(ticket -> System.out.println(ticket.toString()));
            }
            else    System.out.println("No tickets are found for entered tag " + tag);
        }
        else    System.out.println("Invalid Tag!!!. Tag Name should not be empty!!!");
    }

    public void Tickets() {
        List<Ticket> tickets = this.getTickets();
        if(tickets.size() >0 ){
            //tickets.values().stream().sorted((Ticket t1, Ticket t2) -> -t1.getModified().compareTo(t2.getModified())).forEach(ticket -> System.out.println(ticket.toString()));
        }
        else System.out.println("No tickets found!!!");
    }

    public void AllAgentsTicketCount(){
        Map<String, Integer> ticketCountList = this.getAllAgentsTicketCount();
        if(ticketCountList.size()>0){
            //display logic
        }
        else     System.out.println("No Records Found!!!");
    }

    public void TotalTicketCount() {
        System.out.println("Total number of tickets present in the system : " + this.getTotalTicketCount());
    }

    public void OldestTicket(){
        try {
            Ticket ticket = this.getOldestTicket();
            System.out.println("Oldest ticket in the system is : \n" + ticket.toString());
        }catch (InvalidParameterException e){
            System.out.println("No Ticket Found in the system");
        }
    }

    public void getOlderTicketsThanDays() {
        System.out.println("Enter day to get tickets older than a entered number of days");
        int days = Util.readInteger();

        List<Ticket> ticketList = this.getOlderTicketsThanDays(days);
        if(ticketList.size()>0)
            ticketList.forEach(ticket -> System.out.println(ticket.toString()));
        else System.out.println("No tickets found");
    }
}