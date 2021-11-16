package com.container.beans;

import java.sql.Timestamp;

public class ListTickets {

	private int ticket_id;
	private String tickettitle;
	private String ticketdescription;
	private int ticketproject;
	private int assignteam;
	private int assignuser;
	private Timestamp tickettimestamp;
	private int active; 	
	
	
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getTickettitle() {
		return tickettitle;
	}
	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}
	
	public int getTicketproject() {
		return ticketproject;
	}
	public void setTicketproject(int ticketproject) {
		this.ticketproject = ticketproject;
	}
	public String getTicketdescription() {
		return ticketdescription;
	}
	public void setTicketdescription(String ticketdescription) {
		this.ticketdescription = ticketdescription;
	}
	public int getAssignteam() {
		return assignteam;
	}
	public void setAssignteam(int assignteam) {
		this.assignteam = assignteam;
	}
	public int getAssignuser() {
		return assignuser;
	}
	public void setAssignuser(int assignuser) {
		this.assignuser = assignuser;
	}
	
	public Timestamp getTickettimestamp() {
		return tickettimestamp;
	}
	public void setTickettimestamp(Timestamp tickettimestamp) {
		this.tickettimestamp = tickettimestamp;
	}
	public int getStatus() {
		return active;
	}
	public void setStatus(int active) {
		this.active = active;
	}
}
