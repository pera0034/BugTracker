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

import com.container.beans.GetTimeStamp;
import com.container.beans.ListProjects;
import com.container.beans.ListTickets;
import com.container.beans.Projects;
import com.container.beans.TicketData;
import com.mysql.jdbc.PreparedStatement;

public class TicketDao {
		
	public int createTicket(TicketData ticket) {
		int rowsAffected = 0;
		
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();
			
			// write the insert query
			String insertQuery = "INSERT INTO tbltickets (ticket_title,	ticket_desc, project_id, team_id, user_id, ticketstatus, tickettimestamp, active ) VALUES (?,?,?,?,?,?,?,?)";
			
			// set parameters with PreparedStatement
			java.sql.PreparedStatement preparedStatement  = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, ticket.getTickettitle());
			preparedStatement.setString(2, ticket.getTicketdescription());
			preparedStatement.setInt(3, ticket.getTicketproject());
			preparedStatement.setInt(4, ticket.getAssignteam());
			preparedStatement.setInt(5, ticket.getAssignuser());
			preparedStatement.setInt(6, 1);
			preparedStatement.setTimestamp(7, GetTimeStamp.getCurrentTimeStamp());
			preparedStatement.setInt(8, 1);

			// execute the statement
			rowsAffected = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rowsAffected;
	}
		
	public List<ListTickets> getAllTickets() {		
		ListTickets ticket = null;
		List<ListTickets> tickets = new ArrayList<ListTickets>();
		
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			String sql = "SELECT * FROM tbltickets WHERE active = 1 ORDER BY tickettimestamp ASC";
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			// iterates all the returned data from db
			while(set.next()) {
				ticket = new ListTickets();
				ticket.setTicket_id(set.getInt("ticket_id"));
				ticket.setTickettitle(set.getString("ticket_title"));
				ticket.setTicketdescription(set.getString("ticket_desc"));
				ticket.setTicketproject(set.getInt("project_id"));
				ticket.setAssignteam(set.getInt("team_id"));
				ticket.setAssignuser(set.getInt("user_id"));
				ticket.setTickettimestamp(set.getTimestamp("tickettimestamp"));
				ticket.setStatus(set.getInt("active"));
				tickets.add(ticket);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		return tickets;
	}
	
	
	public List<ListTickets> getAllTicketsStatus(int status, int user_id) {
		ListTickets ticket = null;
		List<ListTickets> tickets = new ArrayList<ListTickets>();
		List<ListTickets> empty = new ArrayList<ListTickets>();
		String sql;
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();

			if (user_id == 1) {
				sql = "SELECT * FROM tbltickets WHERE active = 1 AND ticketstatus = " + status + " ORDER BY tickettimestamp DESC";
			}else{
				sql = "SELECT * FROM tbltickets WHERE active = 1 AND ticketstatus = " + status + " AND user_id = " + user_id +" ORDER BY tickettimestamp DESC";
			}

			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			// iterates all the returned data from db
			while(set.next()) {
				ticket = new ListTickets();
				ticket.setTicket_id(set.getInt("ticket_id"));
				ticket.setTickettitle(set.getString("ticket_title"));
				ticket.setTicketdescription(set.getString("ticket_desc"));
				ticket.setTicketproject(set.getInt("project_id"));
				ticket.setAssignteam(set.getInt("team_id"));
				ticket.setAssignuser(set.getInt("user_id"));
				ticket.setTickettimestamp(set.getTimestamp("tickettimestamp"));
				ticket.setStatus(set.getInt("active"));
				tickets.add(ticket);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		
		if(tickets.size() > 0) {
			return tickets;
		}else {
			return empty;
		}
		
	}
	
	public TicketData loadEntry(int id) {	
		TicketData tickets = null;
		try {
			// Connect to DB
			Connection connection = DBConnection.getConnectionToDatabase();
			
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tbltickets WHERE ticket_id = ?");
			preparedStatement.setInt(1, id);
			ResultSet set = preparedStatement.executeQuery();
			
			// iterates all the returned data from database
			while(set.next()) {			
				int ticket_id = set.getInt("ticket_id");
				String ticket_title = set.getString("ticket_title");
				String ticket_desc = set.getString("ticket_desc");
				int project_id = set.getInt("project_id");
				int team_id = set.getInt("team_id");
				int user_id = set.getInt("user_id");
				Timestamp ticketTimestamp = set.getTimestamp("tickettimestamp");
				int active = set.getInt("active");
				
				tickets = new TicketData(ticket_id, ticket_title, ticket_desc, project_id, team_id, user_id, ticketTimestamp, active);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			// TODO: handle exception
		}
		
		return tickets;
	}
	
	// fix me!!
	public Boolean updateTicket(String ticketnumber, String tickettitle, String ticketproject, String ticketdescription, String team, String user) throws SQLException {
	
		boolean rowUpdate = false;
		
		// Convert all string ID parameters to integer
		int rowid = Integer.parseInt(ticketnumber);
		int project_id = Integer.parseInt(ticketproject);
		int team_id = Integer.parseInt(team);
		int user_id = Integer.parseInt(user);
		
		try{
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tbltickets SET ticket_title = ?, project_id = ?, ticket_desc = ?, team_id = ?, user_id = ? WHERE ticket_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setString(1, tickettitle);
			statement.setString(3, ticketdescription);
			statement.setInt(2, project_id);
			statement.setInt(4, team_id);
			statement.setInt(5, user_id);
			statement.setInt(6, rowid);
			rowUpdate = statement.executeUpdate() > 0;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowUpdate;
	} 
	
	public Boolean updateTicketStatus(int rowid, int status) throws SQLException {
		
			boolean rowUpdate = false;
			try{
				Connection connection = DBConnection.getConnectionToDatabase();
				String updateQuery = "UPDATE tbltickets SET ticketstatus = ? WHERE ticket_id = ?";
				java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
				statement.setInt(1, status);
				statement.setInt(2, rowid);
				rowUpdate = statement.executeUpdate() > 0;
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			return rowUpdate;
	} 
	
	public boolean archiveTicket(String id) throws SQLException {
		boolean rowArchive = false;
		int rowid = 0;
		try{
			rowid = Integer.parseInt(id);
			Connection connection = DBConnection.getConnectionToDatabase();
			String updateQuery = "UPDATE tbltickets SET active = ? WHERE ticket_id = ?";
			java.sql.PreparedStatement statement  = connection.prepareStatement(updateQuery);
			statement.setInt(1, 0);
			statement.setInt(2, rowid);
			rowArchive = statement.executeUpdate() > 0;
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return rowArchive;
	}
	
}
