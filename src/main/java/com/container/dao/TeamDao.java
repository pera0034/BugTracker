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

import com.container.beans.ListTeam;
import com.container.beans.Team;
import com.mysql.jdbc.PreparedStatement;

public class TeamDao {
	
	/**
	 * Return all teams
	 * @return
	*/
	public List<ListTeam> getAllTeam() {		
		ListTeam team = null;
		List<ListTeam> teams = new ArrayList<ListTeam>();
		
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			String sql = "SELECT * FROM tblteam WHERE active = 1 ORDER BY team_id DESC";
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			// iterates all the returned data from db
			while(set.next()) {
				team = new ListTeam();
				team.setTeam_id(set.getInt("team_id"));
				team.setTeam_name(set.getString("team_name"));
				teams.add(team);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		return teams;
	}
	
	
	public int insertTeams(Team team) {
		int rowsAffected = 0;
		
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();
			
			// write the insert query
			String insertQuery = "INSERT INTO tblteam (team_name, active) VALUES (?, ?)";
			
			// set parameters with PreparedStatement
			java.sql.PreparedStatement preparedStatement  = connection.prepareStatement(insertQuery);
		
			preparedStatement.setString(1, team.getTeam_name());
			preparedStatement.setInt(2, 1);

			// execute the statement
			rowsAffected = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rowsAffected;
	}
	
	public boolean archiveTeam(String id) throws SQLException {
		boolean rowArchive = false;
		int rowid = 0;
		try{
			rowid = Integer.parseInt(id);
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tblteam SET active = ? WHERE team_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setInt(1, 0);
			statement.setInt(2, rowid);
			rowArchive = statement.executeUpdate() > 0;
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowArchive;
	}
	
	public boolean userAssign(String teamnumber, String usernumber) throws SQLException {
		boolean rowArchive = false;
		try{
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tblusers SET team_id = ? WHERE user_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setInt(1, Integer.parseInt(teamnumber));
			statement.setInt(2, Integer.parseInt(usernumber));
			rowArchive = statement.executeUpdate() > 0;	
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowArchive;
	}
	
	
	public Boolean updateTeamEntry(String id, String teamname) throws SQLException {
		//Projects logs = null;
		boolean rowUpdate = false;
		int rowid = 0;
		try{
			rowid = Integer.parseInt(id);
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tblteam SET team_name = ? WHERE team_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setString(1, teamname);
			statement.setInt(2, rowid);
			rowUpdate = statement.executeUpdate() > 0;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowUpdate;
	} 	 	
		
	public Team loadEntry(int id) {	
		Team teams = null;
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tblteam WHERE team_id = ?");
			preparedStatement.setInt(1, id);
			ResultSet set = preparedStatement.executeQuery();
			
			// iterates all the returned data from database
			while(set.next()) {
				int team_id = set.getInt("team_id");
				String team_name = set.getString("team_name");		 	
				int active = set.getInt("active");
				teams = new Team(team_id, team_name, active);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		
		return teams;
	}
	
}
