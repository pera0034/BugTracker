package com.container.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.container.dao.DBConnection;

@WebServlet("/authenticate")
public class Login extends HttpServlet{
	
	private String messageString = null;
	private String btnType = null;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getServletPath();
		String jspstate = null;
		
		Connection c = DBConnection.getConnectionToDatabase();
		String username = req.getParameter("username");
		String password = req.getParameter("password");	
		
		String hashedPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); 
		
		String selectQuery = "SELECT * FROM tblusers WHERE username = ? AND userpassword = ? AND active = 1 ";
		try {
			PreparedStatement statement = c.prepareStatement(selectQuery);
			statement.setString(1,username);
			statement.setString(2,hashedPass);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				resp.sendRedirect(req.getContextPath() + "/dashboard");
			}
			else {
				jspstate = "<div class='alert alert-warning' role='alert'>Login Failed</div>";
				req.setAttribute("msg", jspstate);
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		} catch (SQLException e) {
			jspstate = "<div class='alert alert-warning' role='alert'>Login Failed</div>";
			req.setAttribute("msg", jspstate);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
			e.printStackTrace();
		}
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 		 req.getRequestDispatcher("/login.jsp").forward(req, resp);
   }
 
}
