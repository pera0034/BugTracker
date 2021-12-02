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
import com.container.dao.ApplicationDao;


@WebServlet("/deleteproject")	
public class DeleteProject extends HttpServlet {
	private String querystatus = null;
	private String messageString = null;
	private String btnType = null;
	private Notifications notify = new Notifications();
	ApplicationDao dao = new ApplicationDao();		
	private String state;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		
		// Get data from form
		String action = req.getServletPath();
		
		switch(action){
			case "/deleteproject":
				try {
					state = deleteTeam(req, resp);
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
			    break;
			default:
				state = "";
				break;
		}

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());

		List<ListProjects> projects = dao.getAllProjects();
		req.setAttribute("projects", projects);
		req.setAttribute("btn", "Create");
		req.setAttribute("request", "dashboard");
 		req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
   }
   
   private String deleteTeam(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
	    ApplicationDao dao = new ApplicationDao();
		boolean methodState = dao.archiveProject(req.getParameter("id"));
		String message = null;
		
		if(methodState) {
			message = "<div class='alert alert-success' role='alert'>Moved to archives</div>";
		}else {
			message = "<div class='alert alert-primary' role='alert'>Unable to execute request</div>";
		}
		return message;
   }
}
