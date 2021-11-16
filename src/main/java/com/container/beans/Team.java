package com.container.beans;

import java.sql.Timestamp;

public class Team {
	private String team_name;
	private int team_id;
	private int active;
	
	public Team(int team_id, String team_name, int active) {
		this.team_name = team_name;
		this.team_id = team_id;
		this.active = active;
	}
	
	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
