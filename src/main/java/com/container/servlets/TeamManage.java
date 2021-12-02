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

import com.container.beans.ListTeam;
import com.container.dao.TeamDao;

import com.container.beans.Users;
import com.container.dao.UserDao;

@WebServlet("/manageteam")
public class TeamManage extends HttpServlet{
	
	private String messageString = null;
	private String btnType = null;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// load all teams
		TeamDao dao = new TeamDao();		
		List<ListTeam> teams = dao.getAllTeam();
		req.setAttribute("teams", teams);
		req.setAttribute("teamslists", teams);

		// Load all the users
		UserDao userdao = new UserDao();		
		List<Users> users = userdao.getAllUsers();
		req.setAttribute("users", users);

		UserRole user_role = new UserRole(req);
		req.setAttribute("user_role", user_role.checkUser());
		req.setAttribute("hello_user", user_role.helloUser());
		
		// Execute save
 		req.getRequestDispatcher("/teammanage.jsp").forward(req, resp);
   }
}
