package com.home.house.model;

import java.io.Serializable;

public class Property implements Serializable{
	private int idx;
	private String id;
	private String title;
	private String date;
	private String expireDate;
	private String price;
	private String managementPrice;
	private String managementDesc;
	private String roomType;
	private int floorNow;
	private int floorTotal;
	private double roomSize;
	private String content;
	private String img;
	private String keyword;
	private String addr;
	private String dongType;
	
	public Property() {
		super();
	}

	public Property(int idx, String id, String title, String date, String expireDate, String price,
			String managementPrice, String managementDesc, int floorNow, int floorTotal, double roomSize,
			String content, String img, String keyword, String addr, String dongType) {
		super();
		this.idx = idx;
		this.id = id;
		this.title = title;
		this.date = date;
		this.expireDate = expireDate;
		this.price = price;
		this.managementPrice = managementPrice;
		this.managementDesc = managementDesc;
		this.floorNow = floorNow;
		this.floorTotal = floorTotal;
		this.roomSize = roomSize;
		this.content = content;
		this.img = img;
		this.keyword = keyword;
		this.addr = addr;
		this.dongType = dongType;
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

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
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

	public String getRoomType() {
		return roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public int getFloorNow() {
		return floorNow;
	}

	public void setFloorNow(int floorNow) {
		this.floorNow = floorNow;
	}

	public int getFloorTotal() {
		return floorTotal;
	}

	public void setFloorTotal(int floorTotal) {
		this.floorTotal = floorTotal;
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

	public String getDongType() {
		return dongType;
	}

	public void setDongType(String dongType) {
		this.dongType = dongType;
	}
	
}