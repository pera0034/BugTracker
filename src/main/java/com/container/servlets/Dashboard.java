package com.container.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.container.beans.Notifications;
import com.container.beans.Projects;
import com.container.beans.GetTimeStamp;
import com.container.beans.ListProjects;
import com.container.dao.ApplicationDao;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet{
	private Notifications notify = new Notifications();
	private String btn = "";

	GetTimeStamp timeStamp = new GetTimeStamp();
	private String currentrequest = "dashboard";
	private String projectname = "";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ApplicationDao dao = new ApplicationDao();		

		String state = null;
		
		// Get data from form
		String projectname = req.getParameter("projectname");
		String action = req.getParameter("btnaction");
		switch(action){
			case "Create":
				try {
					if(notify.checkDataEntry(projectname)){
						state = insertProject(req, resp);
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
		
		List<ListProjects> projects = dao.getAllProjects();
		req.setAttribute("btn", "Create");
		req.setAttribute("projects", projects);
		req.setAttribute("messageString", state);
		req.setAttribute("request", currentrequest);
		req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ApplicationDao dao = new ApplicationDao();		
		List<ListProjects> projects = dao.getAllProjects();
		req.setAttribute("projects", projects);
		req.setAttribute("btn", "Create");
		req.setAttribute("request", currentrequest);
		req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
   }
	
   public String insertProject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {

	    String querystatus = null;

	    projectname = req.getParameter("projectname");
		Projects projects = new Projects(0, projectname, GetTimeStamp.getCurrentTimeStamp(), 1);
		
		ApplicationDao dao = new ApplicationDao();
		int rows = dao.insertProjects(projects);		
		
		if(rows > 0){
			querystatus = notify.insertNotification(true);
		}else{
			querystatus = notify.insertNotification(false);;
		}
		
		return querystatus;
   }
}
