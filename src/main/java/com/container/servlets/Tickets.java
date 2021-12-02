package com.container.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.List;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.container.beans.ListTickets;
import com.container.dao.ApplicationDao;
import com.container.dao.TicketDao;
import com.container.beans.Notifications;

@WebServlet("/tickets")
public class Tickets extends HttpServlet{
	
	private String messageString = null;
	private String btnType = null;
	private String state = null;
	private String querystatus = null;
	private Notifications notify = new Notifications();

	List<ListTickets> ticketsToDo;
	List<ListTickets> ticketsInProgress;
	List<ListTickets> ticketsDeployed;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		 TicketDao dao = new TicketDao();		
 		 
		 String action = req.getServletPath();
		 String moveto = null;
		 String state = null;
		 
		 int ticketnumber = 0;
		 
		 switch(action){
			case "/tickets":
				try {
					try{
						moveto = req.getParameter("moveto");
						ticketnumber = Integer.parseInt(req.getParameter("ticketnumber"));
						state = updateTicket(moveto, ticketnumber);
					}catch(NumberFormatException ex){  }
				
				}catch (SQLException ex) {
					throw new ServletException(ex);
				}	
			    break;
			default:
				moveto = "";
				ticketnumber = 0;
				state = "";
				break;
		 }

		 UserRole user_role = new UserRole(req);
		 req.setAttribute("user_role", user_role.checkUser());
		 req.setAttribute("hello_user", user_role.helloUser());

		 HttpSession user_session = req.getSession();
		 int user_id = Integer.valueOf(user_session.getAttribute("user_id").toString());

		 if(user_role.checkUser()) {
			 ticketsToDo = dao.getAllTicketsStatus(1, user_id);
			 ticketsInProgress = dao.getAllTicketsStatus(2, user_id);
			 ticketsDeployed = dao.getAllTicketsStatus(3, user_id);
		 }else{
			 ticketsToDo = dao.getAllTicketsStatus(1, user_id);
			 ticketsInProgress = dao.getAllTicketsStatus(2, user_id);
			 ticketsDeployed = dao.getAllTicketsStatus(3, user_id);
		 }

		 req.setAttribute("ticketsTodo", ticketsToDo);
		 req.setAttribute("ticketsInProgress", ticketsInProgress);
		 req.setAttribute("ticketsDeployed", ticketsDeployed);

		Boolean isLogged = Boolean.valueOf(user_session.getAttribute("userStatus").toString());
		if(isLogged){
			req.getRequestDispatcher("/tickets.jsp").forward(req, resp);
		}else{
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
   }
	
   public String updateTicket(String moveto, int ticketnumber) throws SQLException, IOException, ServletException {		
	       
	       TicketDao dao = new TicketDao();
	   	   int action = 0;
	   	   boolean methodState = false;
	   	   String querystatus = null;
	   	   
	   	   switch(moveto){
			case "todo":
				action = 1;
				methodState = dao.updateTicketStatus(ticketnumber, action);
				break;
			case "progress":
				action = 2;
				methodState = dao.updateTicketStatus(ticketnumber, action);
				break;
			case "deployed":
				action = 3;
				methodState = dao.updateTicketStatus(ticketnumber, action);
				break;		
			default:
				moveto = "";
				ticketnumber = 0;
				break;
		   }		
	   			
	   		
		   if(methodState) {
				querystatus = notify.updateNotification(true);
		   }else{
				querystatus = notify.updateNotification(false);;
		   }
			
		   return querystatus;
   }
}
