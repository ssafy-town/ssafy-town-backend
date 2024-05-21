package com.home.house.model;

public class HouseInfo {
	private long no;
	private String dong;
	private String apartmentName;
	private long aptCode;
	private String dealAmount;
	private int buildYear;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private double area;
	private int floor;
	private String lat;
	private String lng;
	private String jibun;
	private String img;

	public HouseInfo() {}

	public HouseInfo(int no) {
		this.no = no;
	}

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public int getDealYear() {
		return dealYear;
	}

	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}

	public int getdealMonth() {
		return dealMonth;
	}

	public void setdealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}

	public int getdealDay() {
		return dealDay;
	}

	public void setdealDay(int dealDay) {
		this.dealDay = dealDay;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getJibun() {
		return jibun;
	}

	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
}
