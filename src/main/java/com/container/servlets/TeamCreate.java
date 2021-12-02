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

import com.container.beans.Users;
import com.container.dao.UserDao;

@WebServlet("/createteam")
public class TeamCreate extends HttpServlet{
	
	private String querystatus = null;
	private String messageString = null;
	private String btnType = "Create";
	private Notifications notify = new Notifications();
	TeamDao dao = new TeamDao();		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String state = null;
		
		// Get data from form
		String teamname = req.getParameter("teamname");
		String action = req.getServletPath();
		
		switch(action){
			case "/createteam":
				try {
					if(notify.checkDataEntry(teamname)){
						state = insertTeam(req, resp);
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
	    req.setAttribute("request", "createteam");
	    req.setAttribute("teambtn", btnType);
	    req.setAttribute("message", "");

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/teamcreate.jsp").forward(req, resp);
	}
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
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
	
   public String insertTeam(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
	    String teamname = req.getParameter("teamname");
	    Team teams = new Team(0, teamname, 1);
		
	    TeamDao dao = new TeamDao();
		int rows = dao.insertTeams(teams);		
		
		if(rows > 0){
			querystatus = notify.insertNotification(true);
		}else{
			querystatus = notify.insertNotification(false);;
		}
		
		return querystatus;
   }
}
