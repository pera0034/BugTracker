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

import com.container.dao.ApplicationDao;

@WebServlet("/ticketbacklogs")
public class TicketBacklogs extends HttpServlet{
	
	private String messageString = null;
	private String btnType = null;
	
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		 // Load all tickets
		 // Functionality of the update  
		 // Get all the

		 UserRole user_role = new UserRole(req);
		 req.setAttribute("user_role", user_role.checkUser());
		 req.setAttribute("hello_user", user_role.helloUser());

 		 req.getRequestDispatcher("/ticketbacklogs.jsp").forward(req, resp);
   }
}
