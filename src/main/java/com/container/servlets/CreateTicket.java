package com.container.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.container.beans.GetTimeStamp;
import com.container.beans.ListTickets;
import com.container.beans.Notifications;
import com.container.beans.TicketData;
import com.container.beans.Users;
import com.container.dao.ApplicationDao;
import com.container.dao.TeamDao;
import com.container.dao.TicketDao;
import com.container.dao.UserDao;
import com.container.beans.ListProjects;
import com.container.beans.ListTeam;

@WebServlet("/createticket")
public class CreateTicket extends HttpServlet{

	private String querystatus = null;
	private Notifications notify = new Notifications();
	private String messageString = null;
	private String btnType = "Create";
	GetTimeStamp timeStamp;

	private String currentrequest = "createticket";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String state = null;
		String action = req.getServletPath();
		TicketDao dao = new TicketDao();	
		
		switch(action){
		case "/createticket":
			try {
				state = insertTicket(req, resp);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    break;
		default:
			state = "insert"; // this is needed to reset the state of the form
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
		ApplicationDao projectdao = new ApplicationDao();
		
		
		List<ListProjects> projects = projectdao.getAllProjects();
		
		// Create list of Users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);
		
		// Create list of Team
		TeamDao teamdao = new TeamDao();
		List<ListTeam> teams = teamdao.getAllTeam();
		req.setAttribute("teams", teams);
		
		req.setAttribute("projects", projects);
	    TicketDao dao = new TicketDao();		
		List<ListTickets> tickets = dao.getAllTickets();
		req.setAttribute("btntype", btnType);
		req.setAttribute("tickets", tickets);
		req.setAttribute("request", currentrequest);
		req.setAttribute("project_id", 0);		
		req.setAttribute("team_id", 0);		
		req.setAttribute("user_id", 0);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/createticket.jsp").forward(req, resp);
    }
 	
	public String insertTicket(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
		String tickettitle = req.getParameter("tickettitle");
		String ticketdescription = req.getParameter("ticketdescription");
		
		// Convert all string ID parameters to integer
		int project_id = Integer.parseInt(req.getParameter("ticketproject"));
		int team_id = Integer.parseInt(req.getParameter("assignteam"));
		int user_id = Integer.parseInt(req.getParameter("assignuser"));
		
		
		TicketData tickets = new TicketData(0, tickettitle, ticketdescription, project_id, team_id, user_id, GetTimeStamp.getCurrentTimeStamp(), 1);
		
		TicketDao dao = new TicketDao();
		int rows = dao.createTicket(tickets);		
		
		if(rows > 0){
			querystatus = notify.insertNotification(true);
		}else{
			querystatus = notify.insertNotification(false);;
		}
		
		return querystatus;
    }
}
