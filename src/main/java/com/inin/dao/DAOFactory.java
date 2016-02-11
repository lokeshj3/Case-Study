package com.inin.dao;

/**
 * Created by root on 11/2/16.
 */
public class DAOFactory {
    /**
     * Returns TicketServiceDAO instance
     * @return TicketReportDAO
     */
    public static TicketServiceDAO newTicketServiceDAOInstance(){  return new MapTicketDAO();}

    /**
     * Returns TicketReportDAO instance
     * @return TicketReportDAO
     */
    public static TicketReportDAO newTicketReportDAOInstance(){  return new MapTicketDAO();}

    /**
     * Returns SerializeDAO instance
     * @return SerializeDAO
     */
    public static SerializeDAO newSerializeDAOInstance(){  return new SerializeDAOImpl();}

}
