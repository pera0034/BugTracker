package com.container.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.container.beans.ListTeam;
import com.container.beans.Notifications;
import com.container.beans.Projects;
import com.container.beans.Users;
import com.container.dao.TeamDao;
import com.container.dao.UserDao;


@WebServlet("/deleteteam")	
public class DeleteTeam extends HttpServlet {
	private Notifications notify = new Notifications();
	private String btn = "";
	private String btnType = "Create";
	private String pagerequest = null;
	private String message = null;
	
	Projects project = null;
	TeamDao dao = new TeamDao();		
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		
		String action = req.getServletPath();
		String state = null;
		
		switch(action){
			case "/deleteteam":
				try {
					state = deleteProjectEntry(req, resp);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			    break;
			default:
				state = ""; // this is needed to reset the state of the form 	 
				break;
		}
		
		List<ListTeam> teams = dao.getAllTeam();
		req.setAttribute("teams", teams);
		// Load all the users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);
		req.setAttribute("teamname", "");	
	    req.setAttribute("request", "createteam");
	    req.setAttribute("teambtn", btnType);
	    req.setAttribute("message", "");

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/teamcreate.jsp").forward(req, resp);
   }
   
   private String deleteProjectEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
	    TeamDao dao = new TeamDao();
		boolean methodState = dao.archiveTeam(req.getParameter("teamcode"));

		if(methodState) {
			message = "<div class='alert alert-success' role='alert'>Moved to archives</div>";
		}else {
			message = "<div class='alert alert-primary' role='alert'>Unable to execute request</div>";
		}
		return message;
   }
}
