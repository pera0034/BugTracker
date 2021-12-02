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

import com.container.beans.ListProjects;
import com.container.beans.ListTeam;
import com.container.beans.ListTickets;
import com.container.beans.Notifications;
import com.container.beans.TicketData;
import com.container.beans.Users;
import com.container.dao.ApplicationDao;
import com.container.dao.TeamDao;
import com.container.dao.TicketDao;
import com.container.dao.UserDao;

@WebServlet("/ticketupdate")
public class TicketUpdate extends HttpServlet{	

	private String querystatus = null;
	private String btnType = "Create";
	private String pagerequest = null;
	private Notifications notify = new Notifications();

	private String state = null;
	
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String currentrequest = "ticketupdate";
		String action = req.getServletPath();
		TicketDao dao = new TicketDao();	
		
		switch(action){
			case "/ticketupdate":
				try {
					state = updateTicketEntry(req, resp);
				} catch (SQLException | IOException | ServletException e1) {
					e1.printStackTrace();
				}
			    break;
			default:
				state = "Update"; // this is needed to reset the state of the form
				break;
		}
		
		ApplicationDao projectdao = new ApplicationDao();
		List<ListProjects> projects = projectdao.getAllProjects();
		req.setAttribute("projects", projects);
		
		// Create list of Users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);
		
		// Create list of Team
		TeamDao teamdao = new TeamDao();
		List<ListTeam> teams = teamdao.getAllTeam();
		req.setAttribute("teams", teams);

		List<ListTickets> tickets = dao.getAllTickets();
		req.setAttribute("btntype", btnType);
		req.setAttribute("tickets", tickets);
		req.setAttribute("message", state);
		req.setAttribute("request", currentrequest);
		req.setAttribute("project_id", 0);		
		req.setAttribute("team_id", 0);		
		req.setAttribute("user_id", 0);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/createticket.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String currentrequest = "ticketupdate";
		pagerequest = req.getServletPath();
		
		TicketData ticket = null;
		
		try{
			switch(pagerequest){
				case "/ticketupdate":
					ticket = ticketLoadEntry(req, resp);
					btnType = "Update";
				    break;
				default:
					break;
				}
		}catch (SQLException ex) {
			throw new ServletException(ex);
		}		
		
		
		TicketDao dao = new TicketDao();		
		List<ListTickets> tickets = dao.getAllTickets();
		req.setAttribute("tickets", tickets);
		req.setAttribute("btntype", "Update");
		
		ApplicationDao projectdao = new ApplicationDao();
		List<ListProjects> projects = projectdao.getAllProjects();
		req.setAttribute("projects", projects);
				
		// Create list of Users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);
		
		// Create list of Team
		TeamDao teamdao = new TeamDao();
		List<ListTeam> teams = teamdao.getAllTeam();
		req.setAttribute("teams", teams);
				
		// loads data to the form
		req.setAttribute("ticketnumber", ticket.getTicket_id());		
		req.setAttribute("ticket_title", ticket.getTickettitle());		
		req.setAttribute("ticket_desc", ticket.getTicketdescription());		
		req.setAttribute("project_id", ticket.getTicketproject());		
		req.setAttribute("team_id", ticket.getAssignteam());		
		req.setAttribute("user_id", ticket.getAssignuser());
		req.setAttribute("message", "");
		req.setAttribute("request", currentrequest);
		req.setAttribute("ticketnumber", req.getParameter("ticket"));

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/createticket.jsp").forward(req, resp);
   }
   
   public TicketData ticketLoadEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("ticket"));
		TicketDao dao = new TicketDao();
		TicketData project = dao.loadEntry(id);
		return project; 
  }
  
   public String updateTicketEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {		

		String ticketnumber = req.getParameter("ticketnumber");
	    String tickettitle = req.getParameter("tickettitle");
		String ticketproject = req.getParameter("ticketproject");
		String ticketdescription = req.getParameter("ticketdescription");
		String team = req.getParameter("assignteam");
		String user = req.getParameter("assignuser");
		
		TicketDao dao = new TicketDao();
		boolean methodState = dao.updateTicket(ticketnumber, tickettitle, ticketproject, ticketdescription, team, user);
		
		if(methodState) {
			querystatus = notify.updateNotification(true);
		}else{
			querystatus = notify.updateNotification(false);;
		}
		return querystatus;
  }
   
}