package com.helpdesk.controller;

import com.helpdesk.components.Util;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import com.helpdesk.logger.TicketLogger;
import com.helpdesk.model.Ticket;


import java.security.InvalidParameterException;
import java.util.*;
import java.util.logging.Level;

public class UserDataController extends  TicketController{
   /**
    * controller function to get the input from user
    * and call to create ticket */
   public void create(){
        TicketLogger.writeLog(Level.INFO, "start");

        String subject = Util.readString("Enter Ticket Subject : ");
        String agentName = Util.readString("Enter Agent Name : ");
        String tags = Util.readString("Enter Tags (separated by comma(,) : ");

        HashSet<String> tagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));

        try{
            Ticket tickets = this.create(subject,agentName,tagSet);
            System.out.println("Ticket Has been created successfully." + tickets.toString());
        } catch (InvalidParamsException e) {
            System.out.println(e.getMessage());
        } catch (TicketExceptions ticketFailure) {
            System.out.println(ticketFailure.getMessage());
        }
   }


    public  void update() {
        TicketLogger.writeLog(Level.INFO, "update start");
        int id = Util.readInteger("Enter ticket Id to update : ");
        String agentName = null;
        HashSet<String> tagSet = null;
        String action = null;

        String agentChoice = Util.readString("Do you want to update Agent? Enter y-Yes | n-No");
        if(agentChoice.trim().equals("y")){
            agentName = Util.readString("Enter Agent Name : ");
        }

        String tagsChoice = Util.readString("Do you want to update Tags? Enter y-Yes | n-No");
        if(tagsChoice.trim().equals("y")){
            action = Util.readString("Enter a-adding new tag(s) / r-remove existing tag(s) / n-no");

            if (!action.trim().isEmpty() && (action.trim().equals("a") || action.trim().equals("r"))) {
                String tag = Util.readString("Enter tags separated by comma(,) : ");
                tagSet = new HashSet<>(Arrays.asList(tag.trim().split(",")));
            }
        }

        if(agentChoice.trim().toLowerCase().equals("y") || (tagsChoice.trim().toLowerCase().equals("y") && !action.trim().toLowerCase().equals("n")))
        {
            try{
                Ticket ticket = this.update(id, agentName, tagSet, action);
                System.out.println("Ticket id " + id + " is updated successfully."+"\n Ticket Details are : \n"+ticket.toString());
            }
            catch (InvalidParamsException ie){
                System.out.println("Invalid params!!!");
            }
            catch(TicketExceptions ticketFailure){
                System.out.println(ticketFailure.getMessage());
            }
        }
        else
            System.out.println("Update ticket skipped");
    }

    public void delete(){
        TicketLogger.writeLog(Level.INFO, "delete start");
        int id = Util.readInteger("Enter ticket Id for deletion : ");
        if(id>0){
            try {
                this.delete(id);
                System.out.println("Ticket id " + id + " is deleted successfully.");
            }catch (TicketExceptions te){
                System.out.println(te.getMessage());
            }
        }
    }

    public void getTicket(){
        TicketLogger.writeLog(Level.INFO, "getTicket start");
        int id = Util.readInteger("Enter ticket Id for showing details : ");
        try {
            Ticket ticket = this.getDetails(id);;
            displaySingleTicket(ticket);
        }catch (InvalidParameterException e){
            System.out.println(e.getMessage());
        } catch (TicketExceptions ticketExceptions) {
            System.out.println(ticketExceptions.getMessage());
        }
    }

    public void TicketsByAgent() {
        TicketLogger.writeLog(Level.INFO, "TicketsByAgent start");
         String agentName = Util.readString("Please enter Agent Name : ");

         if(!agentName.isEmpty()){
             List<Ticket> agentTicketList = this.getTicketsByAgent(agentName);
             if(agentTicketList.size()>0){
                 this.displayTickets(agentTicketList);
             }
             else  System.out.println("No records found for entered agent " + agentName);
         }
         else    System.out.println("Invalid agent Name!!!");
    }

    public void getTicketsByTag() {
        String tags = Util.readString("Enter Tags (separated by comma(,) : ");
        HashSet<String> tagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().split(",")));
        if(!tagSet.isEmpty()){
         try {
             List<Ticket> ticketList = this.getTicketsByTag(tagSet);
             if(ticketList.size() > 0)
             {
                 this.displayTickets(ticketList);
             }
             else System.out.println("No tickets match with criteria!");

         }
         catch (TicketExceptions te){
             System.out.println(te.getMessage());
         }
     }
     else
         System.out.println("Invalid Tag!!!. Tag Name should be proper!");
    }


    public void getAllTickets() {
        TicketLogger.writeLog(Level.INFO, "getAllTickets start");
        List<Ticket> ticketList = this.getAll();
        this.displayTickets(ticketList);
    }


    public void allAgentsTicketCount(){
        TicketLogger.writeLog(Level.INFO, "allAgentsTicketCount start");

        Map<String, List<Ticket>> ticketCountList = this.getAllAgentsTicketCount();
        if(ticketCountList.size()>0){
            ticketCountList.forEach((String agentName, List<Ticket> ticketList) -> System.out.println(agentName + " :  " + ticketList.size()));
        }
        else     System.out.println("No Records Found!!!");
    }

    public void TotalTicketCount() {
        System.out.println("Total number of tickets present in the system : " + this.getTotalTicketCount());
    }

    public void oldestTicket(){
        try {
            Ticket ticket = this.getOldestTicket();
            System.out.println("Oldest ticket in the system is : \n" + ticket.toString());
        }catch (TicketExceptions e){
            System.out.println(e.getMessage());
        }
    }

    public void getOlderTicketsThanDays() {
        System.out.println("Enter day to get tickets older than a entered number of days");
        int days = Util.readInteger("Enter no of days before tickets required ");

        List<Ticket> ticketList = null;
        try {
            ticketList = this.olderTicketsThanDays(days);
        } catch (InvalidParamsException e) {
            System.out.println(e.getMessage());
        }

        if(ticketList.size()>0)
            this.displayTickets(ticketList);
        else System.out.println("No tickets found");
    }

    /*public void displayTickets(List ticketList){
        TicketLogger.writeLog(Level.INFO, "Display ticket list start");
        if(ticketList.size() > 0){
            ticketList
                    .stream()
                    .sorted((Ticket t1, Ticket t2) -> t2.getUpdated()
                    .compareTo(t1.getUpdated()));



            *//*ticketList.stream()
                    .forEach(System.out::println);*//*
        }
        else {
            TicketLogger.writeLog(Level.INFO, "No tickets in system");
            System.out.println("No tickets found!");
        }
    }*/


    public void displaySingleTicket(Ticket ticket){
        System.out.println(ticket.toString());
    }
}