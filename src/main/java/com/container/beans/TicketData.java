package com.container.beans;

import java.sql.Timestamp;

public class TicketData {

	private int ticket_id;
	private String ticket_title;
	private String ticket_desc ;
	private int project_id;
	private int team_id;
	private int user_id;
	private Timestamp ticketTimestamp;
	private int active;
	
	public TicketData(int ticket_id, String ticket_title, String ticket_desc, int project_id ,  int team_id, int user_id, Timestamp ticketTimestamp, int active) {
		this.ticket_id = ticket_id;
		this.ticket_title = ticket_title;
		this.ticket_desc = ticket_desc;
		this.project_id = project_id;
		this.team_id = team_id;
		this.user_id = user_id;
		this.ticketTimestamp = ticketTimestamp;
		this.active = active;
	}

	public int getTicket_id() {
		return ticket_id;
	}
	
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	
	public String getTickettitle() {
		return ticket_title;
	}
	
	public void setTickettitle(String ticket_title) {
		this.ticket_title = ticket_title;
	}

	public String getTicketdescription() {
		return ticket_desc;
	}
	
	public void setTicketdescription(String ticket_desc) {
		this.ticket_desc = ticket_desc;
	}
	

	public int getTicketproject() {
		return project_id;
	}
	
	public void setTicketproject(int project_id) {
		this.project_id = project_id;
	}

	public int getAssignteam() {
		return team_id;
	}
	public void setAssignteam(int team_id) {
		this.team_id = team_id;
	}

	public int getAssignuser() {
		return user_id;
	}
	public void setAssignuser(int user_id) {
		this.user_id = user_id;
	}

	
	public Timestamp getTicketTimestamp() {
		return ticketTimestamp;
	}

	public void setTicketTimestamp(Timestamp ticketTimestamp) {
		this.ticketTimestamp = ticketTimestamp;
	}

	
	public int getStatus() {
		return active;
	}

	public void setStatus(int active) {
		this.active = active;
	}

}
