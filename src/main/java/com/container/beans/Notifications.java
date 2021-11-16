package com.container.beans;

public class Notifications {
	
	private String status = null;
	private Boolean boolstatus;
	
	public String insertNotification(Boolean result) {
		if(result){
			status = "<div class='alert alert-success' role='alert'>Successfully Saved</div>";
		}else{
			status = "<div class='alert alert-primary' role='alert'>Failed</div>";
		}
		return status;
	}
	

	public String registerNotification(Boolean result) {
		if(result){
			status = "<div class='alert alert-success' role='alert'>Successfully Registered</div>";
		}else{
			status = "<div class='alert alert-primary' role='alert'>Failed</div>";
		}
		return status;
	}
	
	// Check if the form fields are empty
	public String checkFields(Boolean formfields) {
		if(formfields){
			status = "<div class='alert alert-warning' role='alert'>Fields are empty</div>";
		}
		return status;
	}
	
	// Check if the form fields are empty
	public Boolean checkDataEntry(String formfield) {
		if(formfield.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	public String updateNotification(Boolean result) {
		
		if(result){
			status = "<div class='alert alert-success' role='alert'>Successfully Updated</div>";
		
		}else{
			status = "<div class='alert alert-primary' role='alert'>Failed to update</div>";
		}
		
		return status;
	}
}
