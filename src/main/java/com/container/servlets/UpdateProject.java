package com.container.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.container.beans.ListProjects;
import com.container.beans.Notifications;
import com.container.beans.Projects;
import com.container.dao.ApplicationDao;

@WebServlet("/updateproject")	
public class UpdateProject extends HttpServlet {

	private String querystatus = null;
	private Notifications notify = new Notifications();
	private String btn = "";
	private String btnType = "Create";
	private String formAction = null;
	private String pagerequest = null;
	private String message = null;
	
	private String currentrequest = "updateproject";

	Projects project = null;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ApplicationDao dao = new ApplicationDao();		
		
		String projectname = req.getParameter("projectname");
		String action = req.getServletPath();
		String state = null;
		
		switch(action){
			case "/updateproject":
				try {
					if(notify.checkDataEntry(projectname)){
						state = updateProjectEntry(req, resp);
					}else {
					   state = notify.checkFields(true);
					}
				} catch (SQLException | IOException | ServletException e1) {
					e1.printStackTrace();
				}
			    break;
			default:
				state = "Update"; // this is needed to reset the state of the form
				break;
		}

		List<ListProjects> projects = dao.getAllProjects();
		req.setAttribute("projects", projects);
		req.setAttribute("btn", "Update");
		req.setAttribute("messageString", state);
		req.setAttribute("request", currentrequest);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pagerequest = req.getServletPath();
		 
		try{
			switch(pagerequest){
				case "/updateproject":
					project = projectLoadEntry(req, resp);
				    btnType = "Update";
				    break;
				default:
					break;
				}
		}catch (SQLException ex) {
				throw new ServletException(ex);
		}		
		
		String projectId = null;

		ApplicationDao dao = new ApplicationDao();		
		List<ListProjects> projects = dao.getAllProjects();
		req.setAttribute("projects", projects);
		req.setAttribute("projectname", project.getProjectName());		
		req.setAttribute("projectid", req.getParameter("id")); // this will get the URL parameter id
		req.setAttribute("btn", btnType);
		req.setAttribute("request", currentrequest);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
   }
	
   public Projects projectLoadEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		ApplicationDao dao = new ApplicationDao();
		Projects project = dao.loadEntry(id);
		return project; 
   }
   
   public String updateProjectEntry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {		
	    String id = req.getParameter("projectid");
	    String projectname = req.getParameter("projectname");
		ApplicationDao dao = new ApplicationDao();
		boolean methodState = dao.update(id, projectname);
		
		if(methodState) {
			querystatus = notify.updateNotification(true);
		}else{
			querystatus = notify.updateNotification(false);;
		}
		return querystatus;
   }
}
