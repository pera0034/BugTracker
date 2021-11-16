package com.container.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.container.beans.Users;
import com.mysql.jdbc.PreparedStatement;

public class UserDao {
	
	/**
	 * Return all teams
	 * @return
	*/
	public List<Users> getAllUsers() {		
		Users user = null;
		List<Users> users = new ArrayList<Users>();
		
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			String sql = "SELECT * FROM tblusers WHERE active = 1";
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			// iterates all the returned data from db
			while(set.next()) {
				user = new Users();
				user.setUser_id(set.getInt("user_id"));
				user.setUsername(set.getString("username"));
				user.setEmailaddress(set.getString("emailaddress"));
				user.setLastname(set.getString("firstname"));
				user.setFirstname(set.getString("lastname"));
				user.setTeam_id(set.getInt("team_id"));
				user.setUserrole(set.getInt("userrole"));
				users.add(user);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		return users;
	}
}
