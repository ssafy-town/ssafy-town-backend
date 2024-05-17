package com.home.house.model;

import java.io.Serializable;

public class Property implements Serializable{
	private int idx;
	private String id;
	private String title;
	private String date;
	private String type;
	private String price;
	private String managementPrice;
	private String managementDesc;
	private String houseType;
	private int currentFloor;
	private int totalFloor;
	private double roomSize;
	private String content;
	private String img;
	private String keyword;
	private String addr;
	private String dong;
	private String lat;
	private String lng;
	
	public Property() {
		super();
	}

	public Property(int idx, String id, String title, String date, String type, String price, String managementPrice,
			String managementDesc, String houseType, int currentFloor, int totalFloor, double roomSize, String content,
			String img, String keyword, String addr, String dong, String lat, String lng) {
		super();
		this.idx = idx;
		this.id = id;
		this.title = title;
		this.date = date;
		this.type = type;
		this.price = price;
		this.managementPrice = managementPrice;
		this.managementDesc = managementDesc;
		this.houseType = houseType;
		this.currentFloor = currentFloor;
		this.totalFloor = totalFloor;
		this.roomSize = roomSize;
		this.content = content;
		this.img = img;
		this.keyword = keyword;
		this.addr = addr;
		this.dong = dong;
		this.lat = lat;
		this.lng = lng;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getManagementPrice() {
		return managementPrice;
	}

	public void setManagementPrice(String managementPrice) {
		this.managementPrice = managementPrice;
	}

	public String getManagementDesc() {
		return managementDesc;
	}

	public void setManagementDesc(String managementDesc) {
		this.managementDesc = managementDesc;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
	}

	public double getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(double roomSize) {
		this.roomSize = roomSize;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
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

	


	
}