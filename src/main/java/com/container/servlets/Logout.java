package com.container.servlets;

import com.container.beans.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/logout")
public class Logout extends HttpServlet{
	
	private String messageString = null;
	private String btnType = null;
	Users user = null;
	List<Users> users = new ArrayList<Users>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserRole user = new UserRole(req);
		user.logoutUser();

 		req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
