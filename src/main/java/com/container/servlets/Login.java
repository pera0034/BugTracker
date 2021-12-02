package com.container.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.container.beans.Users;
import com.container.dao.DBConnection;

@WebServlet("/authenticate")
public class Login extends HttpServlet{
	
	private String messageString = null;
	private String btnType = null;
	Users user = null;
	List<Users> users = new ArrayList<Users>();
	
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

				String userrole = String.valueOf(rs.getInt("userrole"));
				String user_id = String.valueOf(rs.getInt("user_id"));
				String fname = String.valueOf(rs.getString("firstname"));

				HttpSession usersession = req.getSession();
				usersession.setAttribute("userrole", userrole);
				usersession.setAttribute("userFname", fname);
				usersession.setAttribute("user_id", user_id);
				usersession.setAttribute("userStatus", true);

				if(rs.getInt("userrole") == 1){
					resp.sendRedirect(req.getContextPath() + "/dashboard");
				}else{
					resp.sendRedirect(req.getContextPath() + "/tickets");
				}
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
