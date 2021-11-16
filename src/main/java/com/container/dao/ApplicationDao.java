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

import com.container.beans.ListProjects;
import com.container.beans.Projects;
import com.mysql.jdbc.PreparedStatement;

public class ApplicationDao {
	
	/**
	 * Return all projects
	 * @return
	*/
	public List<ListProjects> getAllProjects() {		
		ListProjects project = null;
		List<ListProjects> projects = new ArrayList<ListProjects>();
		
		
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			String sql = "SELECT * FROM tblprojects WHERE active = 1 ORDER BY projectTimeStamp DESC";
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			// iterates all the returned data from db
			while(set.next()) {
				project = new ListProjects();
				project.setProjectID(set.getInt("project_id"));
				project.setProjectName(set.getString("project_name"));
				projects.add(project);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		return projects;
	}
	
	public int insertProjects(Projects project) {
		int rowsAffected = 0;
		
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();
			
			// write the insert query
			String insertQuery = "INSERT INTO tblprojects (project_name, projectTimeStamp, active) VALUES (?, ?, ?)";
			
			// set parameters with PreparedStatement
			java.sql.PreparedStatement preparedStatement  = connection.prepareStatement(insertQuery);
		
			preparedStatement.setString(1, project.getProjectName());
			preparedStatement.setTimestamp(2, getCurrentTimeStamp());
			preparedStatement.setInt(3, 1);

			// execute the statement
			rowsAffected = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rowsAffected;
	}
	
	
	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
    }
	
	
	public Projects loadEntry(int id) {	
		Projects projects = null;
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tblprojects WHERE project_id = ?");
			preparedStatement.setInt(1, id);
			ResultSet set = preparedStatement.executeQuery();
			
			// iterates all the returned data from database
			while(set.next()) {
				int project_id = set.getInt("project_id");
				String project_name = set.getString("project_name");		 	
				Timestamp projectTimeStamp = set.getTimestamp("projectTimeStamp");
				int active = set.getInt("active");
				projects = new Projects(project_id, project_name, projectTimeStamp, active);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		
		return projects;
	}
	
	
	public Boolean update(String id, String projectname) throws SQLException {
		//Projects logs = null;
		boolean rowUpdate = false;
		int rowid = 0;
		try{
			rowid = Integer.parseInt(id);
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tblprojects SET project_name = ? WHERE project_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setString(1, projectname);
			statement.setInt(2, rowid);
			rowUpdate = statement.executeUpdate() > 0;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowUpdate;
	} 	 	 	 
	
	
	public boolean archiveProject(String id) throws SQLException {
		boolean rowArchive = false;
		int rowid = 0;
		try{
			rowid = Integer.parseInt(id);
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tblprojects SET active = ? WHERE project_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setInt(1, 0);
			statement.setInt(2, rowid);
			rowArchive = statement.executeUpdate() > 0;
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowArchive;
	}
	
	public int storeCredentials(String user, String firstname, String lastname, String pass, String email, String token) throws SQLException {
		int rowsAffected = 0;
		String rs = null;
		Connection connection = DBConnection.getConnectionToDatabase();
		try {
			
			/*String insertQuery = "INSERT INTO profile (profile_id, username, password, email, verification_status, validation_token) VALUES (?,?,?,?,?,?)";
			//final String PENDING = "Pending";
			java.sql.PreparedStatement preparedStatement  = connection.prepareStatement(insertQuery);
			
			int credId = (int) (Math.random() * 1000);
			preparedStatement.setInt(1, credId);
			preparedStatement.setString(2, user);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, "verified"); //"Pending" refers to the validation status of the stored credentials, which will be changed upon successful email verification.
			preparedStatement.setString(6, token); //validation token
			rowsAffected = preparedStatement.executeUpdate();*/
			
			String insertQuery = "INSERT INTO tblusers (username, firstname, lastname, userpassword, emailaddress, userrole, team_id, active ) VALUES (?,?,?,?,?,?,?,?)";
			java.sql.PreparedStatement preparedStatement  = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, firstname);
			preparedStatement.setString(3, lastname);
			preparedStatement.setString(4, pass);
			preparedStatement.setString(5, email);
			preparedStatement.setInt(6, 2);  // userrole
			preparedStatement.setInt(7, 0);  // team_id
			preparedStatement.setInt(8, 1); //validation token
			rowsAffected = preparedStatement.executeUpdate();
		}
		finally {
			connection.close();
		}
		
		return rowsAffected;
		
	}

	public boolean verifyToken(String token) throws SQLException {
		Connection c = DBConnection.getConnectionToDatabase();
		try {
			String updateQuery = "UPDATE PROFILE SET VERIFICATION_STATUS = 'verified' WHERE VALIDATION_TOKEN = ?";
			System.out.println("verifying token..."+token);
			java.sql.PreparedStatement statement  = c.prepareStatement(updateQuery);
			statement.setString(1, token);
			
			return statement.execute();
			
		}
		finally {
			c.close();
		}
	}
}
