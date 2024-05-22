package com.home.house.model;

public class FindDeal {
	private String sidoName;
	private String gugunName;
	private String dongName;
	private String year;
	private String month;
	
	public FindDeal() {
		super();
	}
	
	public FindDeal(String sidoName, String gugunName, String dongName, String year, String month) {
		super();
		this.sidoName = sidoName;
		this.gugunName = gugunName;
		this.dongName = dongName;
		this.year = year;
		this.month = month;
	}
	
	public String getSidoName() {
		return sidoName;
	}
	
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	
	public String getGugunName() {
		return gugunName;
	}
	
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	
	public String getDongName() {
		return dongName;
	}
	
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}

}
