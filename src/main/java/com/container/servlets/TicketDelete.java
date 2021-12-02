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


@WebServlet("/deleteticket")	
public class TicketDelete extends HttpServlet {
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
		
		String id = req.getParameter("projectid");
		String action = req.getServletPath();
		String state = null;
		
		switch(action){
			case "/deleteticket":
				try {
					state = deleteTicketEntry(req, resp);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			    break;
			default:
				state = ""; // this is needed to reset the state of the form
				break;
		}
		
		TicketDao dao = new TicketDao();		
		List<ListTickets> tickets = dao.getAllTickets();
		req.setAttribute("tickets", tickets);
		
		// Create list of Users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);
		
		// Create list of Team
		TeamDao teamdao = new TeamDao();
		List<ListTeam> teams = teamdao.getAllTeam();
		req.setAttribute("teams", teams);
		
		req.setAttribute("btntype", "Update");
		req.setAttribute("message", state);
		req.setAttribute("request", "ticketupdate");
		
		ApplicationDao projectdao = new ApplicationDao();
		List<ListProjects> projects = projectdao.getAllProjects();
		req.setAttribute("projects", projects);
		req.setAttribute("tickets", tickets);
		req.setAttribute("message", state);
		
		req.setAttribute("project_id", 0);		
		req.setAttribute("team_id", 0);		
		req.setAttribute("user_id", 0);

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

		req.getRequestDispatcher("/createticket.jsp").forward(req, resp);
   }
   
   private String deleteTicketEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
	    TicketDao dao = new TicketDao();
		boolean methodState = dao.archiveTicket(req.getParameter("ticket"));

		if(methodState) {
			message = "<div class='alert alert-success' role='alert'>Moved to archives</div>";
		}else {
			message = "<div class='alert alert-primary' role='alert'>Unable to execute request</div>";
		}
		return message;
   }
}
