package com.container.beans;

public class GetTimeStamp {
	   public static java.sql.Timestamp getCurrentTimeStamp() {
			java.util.Date today = new java.util.Date();
			return new java.sql.Timestamp(today.getTime());
	   }
}	   
