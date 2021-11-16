package com.container.beans;

import java.sql.Timestamp;

public class Projects {
	private String project_name;
	private int project_id;
	private Timestamp projectTimeStamp;	
	private int active;
	
	public Projects(int project_id, String project_name, Timestamp projectTimeStamp, int active) {
		this.project_id = project_id;
		this.project_name = project_name;
		this.projectTimeStamp = projectTimeStamp;
		this.active = active;
	}
	
	public void setProjectID(int project_id) {
		this.project_id = project_id;
	}

	public int getProjectID() {
		return project_id;
	}
	
	public void setProjectName(String project_name) {
		this.project_name = project_name;
	}

	public String getProjectName() {
		return project_name;
	}
	
	public void setTimeStamp(Timestamp projectTimeStamp) {
		this.projectTimeStamp = projectTimeStamp;
	}

	public Timestamp getTimeStamp() {
		return projectTimeStamp;
	}
	
	public void setActive(int active) {
		this.active = active;
	}

	public int getActive() {
		return active;
	}
	
}
