package com.home.house.model;

import java.util.List;

public class GptForm {
	private String keyword;
	 private List<YearlyDealStats> yearlyDealStats;
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<YearlyDealStats> getYearlyDealStats() {
		return yearlyDealStats;
	}

	public void setYearlyDealStats(List<YearlyDealStats> yearlyDealStats) {
		this.yearlyDealStats = yearlyDealStats;
	}
	

	
	
}
