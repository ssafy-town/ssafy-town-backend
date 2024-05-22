package com.home.member.model;


public class ZzimApt {
	private String userId;
	private long aptCode;
	
	public ZzimApt() {
		
	}
	
	public ZzimApt(String userId, long aptCode) {
		super();
		this.userId = userId;
		this.aptCode = aptCode;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public long getAptCode() {
		return aptCode;
	}
	
	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

}

