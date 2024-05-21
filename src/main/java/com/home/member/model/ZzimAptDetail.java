package com.home.member.model;

public class ZzimAptDetail {
	long aptCode;
	String aptName;
	String dongCode;
	String dongName;
	int buildYear;
	String jibun;
	String lat;
	String lng;
	String img;
	int cnt;

	public ZzimAptDetail(long aptCode, String aptName, String dongCode, String dongName, int buildYear, String jibun,
			String lat, String lng, String img, int cnt) {
		super();
		this.aptCode = aptCode;
		this.aptName = aptName;
		this.dongCode = dongCode;
		this.dongName = dongName;
		this.buildYear = buildYear;
		this.jibun = jibun;
		this.lat = lat;
		this.lng = lng;
		this.img = img;
		this.cnt = cnt;
	}
	
	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	public long getAptCode() {
		return aptCode;
	}
	
	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}
	
	public String getAptName() {
		return aptName;
	}
	
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	
	public String getDongCode() {
		return dongCode;
	}
	
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	
	public int getBuildYear() {
		return buildYear;
	}
	
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	
	public String getJibun() {
		return jibun;
	}
	
	public void setJibun(String jibun) {
		this.jibun = jibun;
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
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public int getCnt() {
		return cnt;
	}
	
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

}
