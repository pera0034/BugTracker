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

import com.container.beans.GetTimeStamp;
import com.container.beans.ListProjects;
import com.container.beans.ListTeam;
import com.container.beans.Team;
import com.container.beans.Users;
import com.container.dao.ApplicationDao;
import com.container.dao.TeamDao;
import com.container.dao.UserDao;
import com.container.beans.Notifications;
import com.container.beans.Projects;
import com.container.beans.Users;
import com.container.dao.UserDao;

@WebServlet("/updateteam")
public class TeamUpdate extends HttpServlet{
	
	private String querystatus = null;
	private String messageString = null;
	private String btnType = "Create";
	private Notifications notify = new Notifications();
	TeamDao dao = new TeamDao();		

	private String pagerequest = null;
	Team team = null;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String state = null;
		
		// Get data from form
		String teamname = req.getParameter("teamname");
		String action = req.getServletPath();
		
		switch(action){
			case "/updateteam":
				try {
					if(notify.checkDataEntry(teamname)){
						state = updateTeam(req, resp);
					    btnType = "Create";
					}else {
					    state = notify.checkFields(true);
					}
				} catch (SQLException | IOException | ServletException e) {
					e.printStackTrace();
				}
			    break;
			default:
				state = "";
				break;
		}
		
		List<ListTeam> teams = dao.getAllTeam();
		req.setAttribute("teams", teams);
		
		// Load all the users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);
	    req.setAttribute("request", "updateteam");
	    req.setAttribute("teambtn", btnType);
	    req.setAttribute("message", state);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/teamcreate.jsp").forward(req, resp);
	}
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		pagerequest = req.getServletPath();
		try{
			switch(pagerequest){
				case "/updateteam":
					team = projectLoadEntry(req, resp);
				    btnType = "Update";
				    break;
				default:
					break;
				}
		}catch (SQLException ex) {
				throw new ServletException(ex);
		}		
		
		List<ListTeam> teams = dao.getAllTeam();
		req.setAttribute("teams", teams);
		// Load all the users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		
		req.setAttribute("users", users);
		req.setAttribute("teamname", team.getTeam_name());	
		req.setAttribute("teamid", req.getParameter("teamcode"));	
	    req.setAttribute("request", "updateteam");
	    req.setAttribute("teambtn", btnType);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

 		req.getRequestDispatcher("/teamcreate.jsp").forward(req, resp);
   }
	

   public Team projectLoadEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("teamcode"));
	    TeamDao dao = new TeamDao();
	    Team team = dao.loadEntry(id);
		return team; 
   }	
   	
   public String updateTeam(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
	    String teamcode = req.getParameter("teamcode");
	    String teamname = req.getParameter("teamname");
	    
	    TeamDao dao = new TeamDao();
		boolean rows = dao.updateTeamEntry(teamcode, teamname);		

		if(rows){
			querystatus = notify.insertNotification(true);
		}else{
			querystatus = notify.insertNotification(false);;
		}
		
		return querystatus;
   }
}
