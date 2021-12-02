package com.container.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.container.beans.ListProjects;
import com.container.beans.ListTeam;
import com.container.beans.ListTickets;
import com.container.beans.Notifications;
import com.container.beans.Projects;
import com.container.beans.Users;
import com.container.dao.ApplicationDao;
import com.container.dao.TeamDao;
import com.container.dao.TicketDao;
import com.container.dao.UserDao;


@WebServlet("/archivetickets")	
public class TicketArchive extends HttpServlet {
	private Notifications notify = new Notifications();
	private String btn = "";
	private String btnType = "Create";
	private String pagerequest = null;
	private String message = null;
	
	Projects project = null;

	List<ListTickets> ticketsToDo;
	List<ListTickets> ticketsInProgress;
	List<ListTickets> ticketsDeployed;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		 
		 TicketDao dao = new TicketDao();		
 		 String action = req.getServletPath();
		 String moveto = null;
		 String state = null;
		 
		 String ticketnumber = null;
		 
		 switch(action){
			case "/archivetickets":
				try {
					try{
						ticketnumber = req.getParameter("ticketnumber");
						state = deleteTicketEntry(ticketnumber);
					}catch(NumberFormatException ex){  }
				
				}catch (SQLException ex) {
					throw new ServletException(ex);
				}	
			    break;
			default:
				moveto = "";
				ticketnumber = "";
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
		req.setAttribute("message", state);

		req.getRequestDispatcher("/tickets.jsp").forward(req, resp);
   }
   
   private String deleteTicketEntry(String ticketnumber) throws SQLException, IOException {
	    TicketDao dao = new TicketDao();
		boolean methodState = dao.archiveTicket(ticketnumber);

		if(methodState) {
			message = "<div class='alert alert-success' role='alert'>Moved to archives</div>";
		}else {
			message = "<div class='alert alert-primary' role='alert'>Unable to execute request</div>";
		}
		return message;
   }
}
