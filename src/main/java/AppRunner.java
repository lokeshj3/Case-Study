import com.helpdesk.components.Util;
import com.helpdesk.controller.UserDataController;
import com.helpdesk.logger.TicketLogger;

import java.util.logging.Level;

/**
 * Created by root on 9/2/16.
 * application runner class.
 */
public class AppRunner {
    final static String[] menuItems = {"1) Create Ticket","2) Update Ticket","3) Delete Ticket","4) Get Ticket","5) Get all Ticket","6) Find Ticket assigned to Agent", "7) Get all Agent with Ticket Counts","8) Search Ticket By Tag","9) Total Ticket Count","10) Oldest Tickets Report", "11) Tickets older than n Days", "12) Count of Tickets with specific Tag", "13) Exit"};

    public static void main(String[] args) {
        TicketLogger.writeLog(Level.INFO, "Application start");
        UserDataController objUserDataController = new UserDataController();

        int choice;
        do{
            displayTicketMenu();
            choice =  Util.readInteger("Enter your choice :");

            switch (choice){
                case 1:
                    //Create New Ticket
                    objUserDataController.create();
                    break;
                case 2:
                    //Update Ticket By ID
                    objUserDataController.update();
                    break;
                case 3:
                    //Delete By ID
                    objUserDataController.delete();
                    break;
                case 4:
                    //Get Ticket By ID
                    objUserDataController.getTicket();
                    break;
                case 5:
                    //Get All tickets sorted BY UPDATED TIME
                    objUserDataController.getAllTickets();
                    break;
               case 6:
                    //Find Ticket assigned to Agent
                    objUserDataController.TicketsByAgent();
                    break;
                 case 7:
                    //Get all Agent with Ticket Counts
                    objUserDataController.allAgentsTicketCount();
                    break;
                /*case 8:
                    //Search Ticket By Tag
                    objUserDataController.getTicketsByTag();
                    break; */
                    case 9:
                    //Report on Number of Tickets in System
                    objUserDataController.TotalTicketCount();
                    break;
                /*case 10:
                    //Oldest Ticket(s) in System
                    objUserDataController.getOldestTicket();
                    break;
                case 11:
                    //Ticket Older than entered Days
                    objUserDataController.getOlderTicketsThanDays();
                    break;
                case 12:
                    //Number of Tickets with entered tag
                    //objUserDataController.ticketsReportForTag();*/
                case 13:
                    System.out.println("Good Bye!!");
                    break;
                default:
                    //Exit Case
                    System.out.println("Please enter correct option.");
                    break;
            }
        }
        while (choice != 13);

        TicketLogger.writeLog(Level.INFO, "End Application");
    }


    /**
     * Method to display ticket menu items
     * */
    public static void displayTicketMenu(){
        TicketLogger.writeLog(Level.INFO, "start display menu items");
        for (String options : menuItems){
            System.out.println(options);
        }
        TicketLogger.writeLog(Level.INFO, "end display menu items");
    }
}
