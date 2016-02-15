package com.helpdesk.controller;

import com.helpdesk.components.Util;
import com.helpdesk.exception.InvalidParamsException;
import com.helpdesk.exception.TicketExceptions;
import static com.helpdesk.logger.TicketLogger.*;
import com.helpdesk.model.Ticket;


import java.util.*;
import java.util.logging.Level;

public class UserDataController extends  TicketController{
   /**
    * controller function to get the input from user
    * and call to create ticket */

   // Static import can be used for TicketLogger

   public void create(){
        writeLog(Level.INFO, "start");

        String subject = Util.readString("Enter Ticket Subject : ");
        String agentName = Util.readString("Enter Agent Name : ");
        String tags = Util.readString("Enter Tags (separated by comma(,) : ");
        //MD: Not necessary, but better to use Parent type reference when it is available
        HashSet<String> tagSet = new HashSet<>(Arrays.asList(tags.toLowerCase().trim().split(",")));

        try{
            //MD:Bad use of Inheritance, here you are override create method not extending the functionality of create
            // method of parent class . Here you use child class create method only for User interaction.This can also done
            // by creating TicketController class.

            //Here single ticket return by the create method, name also should be singular form e.g. ticket
            Ticket tickets = this.create(subject,agentName,tagSet);
            this.displaySingleTicket(tickets);
        } catch (TicketExceptions e) {
            writeLog(Level.WARNING, "error occurred: "+e.getMessage());
            System.out.println(e.getMessage());
        }
   }


    public  void update() {
        writeLog(Level.INFO, "update start");
        int id = Util.readInteger("Enter ticket Id to update : ");
        String agentName = "";
        HashSet<String> tagSet = null;
        String action = "";

        String agentChoice = Util.readString("Do you want to update Agent? Enter y-Yes | n-No");
        if(agentChoice.trim().equals("y")){
            agentName = Util.readString("Enter Agent Name : ");
        }

        String tagsChoice = Util.readString("Do you want to update Tags? Enter y-Yes | n-No");
        if(tagsChoice.trim().equals("y")){
            action = Util.readString("Enter a-adding new tag(s) / r-remove existing tag(s) / n-no");

            if (!action.trim().isEmpty() && (action.trim().equals("a") || action.trim().equals("r"))) {
                String tag = Util.readString("Enter tags separated by comma(,) (Enter 'all' to remove all tags) : ");
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
        writeLog(Level.INFO, "delete start");
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

    /**
     * get ticket by id
     *  */
    public void getTicket(){
        writeLog(Level.INFO, "getTicket start");
        int id = Util.readInteger("Enter ticket Id for showing details : ");
        try {
            Ticket ticket = this.getDetails(id);
            displaySingleTicket(ticket);
        }catch (TicketExceptions e) {
            writeLog(Level.WARNING, "error occurred: "+e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void TicketsByAgent() {
        writeLog(Level.INFO, "TicketsByAgent start");
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

    /**
     * get ticket by tags
     *  */
    public void getTicketsByTag() {
        writeLog(Level.INFO, "getTickets by tag start");
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
         catch (TicketExceptions e){
             writeLog(Level.WARNING, "error occurred: "+e.getMessage());
             System.out.println(e.getMessage());
         }
     }
     else
         System.out.println("Invalid Tag!!!. Tag Name should be proper!");
    }


    /**
     * get all tickets
     *  */
    public void getAllTickets() {
        writeLog(Level.INFO, "getAllTickets start");
        List<Ticket> ticketList = this.getAll();
        this.displayTickets(ticketList);
    }


    /**
     * get ticket count by agent name
     *  */
    public void allAgentsTicketCount(){
        writeLog(Level.INFO, "allAgentsTicketCount start");

        //Ganesh D: if you just required ticket count, then it make sense to have an Integer value
        // rather than List<Ticket>, as if you using list it makes process heavy
        Map<String, List<Ticket>> ticketCountList = this.getAllAgentsTicketCount();
        if(ticketCountList.size()>0){
            ticketCountList.forEach((String agentName, List<Ticket> ticketList) -> System.out.println(agentName + " :  " + ticketList.size()));
        }
        else     System.out.println("No Records Found!!!");
    }

    /**
     * total ticket counts
     *  */
    public void TotalTicketCount() {
        System.out.println("Total number of tickets present in the system : " + this.getTotalTicketCount());
    }

    /**
     * get oldest ticket
     * */
    public void oldestTicket(){
        writeLog(Level.INFO, "oldest ticket start");
        //MD:
        // 1:- You handle TicketExceptions which never thrown.
        // 2:- Not here , In reporting function you first fetch the all record and then apply filter on them.Why not we ,
        // query it directly in data base.It is more faster than fetching and applying filter.
        try {
            Ticket ticket = this.getOldestTicket();
            this.displaySingleTicket(ticket);
        }catch (TicketExceptions e){
            writeLog(Level.WARNING, "error occurred: "+e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /**
     * get older ticket than no of days
     * */
    public void getOlderTicketsThanDays() {
        writeLog(Level.INFO, "older than days ticket start");
        int days = Util.readInteger("Enter day(s) to get tickets older than a entered number of days");

        List<Ticket> ticketList = null;
        try {
            ticketList = this.olderTicketsThanDays(days);
        } catch (InvalidParamsException e) {
            writeLog(Level.WARNING, "error occurred: "+e.getMessage());
            System.out.println(e.getMessage());
        }

        if(ticketList.size()>0)
            this.displayTickets(ticketList);
        else System.out.println("No tickets found");
    }

    /**
     * get display single ticket
     * */
    public void displaySingleTicket(Ticket ticket){
        writeLog(Level.INFO, "display single ticket start");
        //MD: No need to call toString method explicitly
        System.out.println(ticket.toString());
    }

    /**
     * get ticket count by tag
     * */
    public void ticketsReportForTag(){
        writeLog(Level.INFO, "ticket count by tag starts");
        this.getTicketCountByTag();
    }

    public void deleteAllTickets(){
        writeLog(Level.INFO, "delete all tickets controller starts");
        if(this.removeAllTickets())
            System.out.println("Deleted all tickets Successfully!");
        else
            System.out.println("Operation failed!");
    }
}