package com.home.house.model;

import java.util.List;

public class HouseStats {
    private List<HouseInfo> houseInfos;
    private List<YearlyDealStats> yearlyDealStats;
    private AreaDistribution areaDistribution;
    private String gptComment;

    public List<HouseInfo> getHouseInfos() {
        return houseInfos;
    }

    public void setHouseInfos(List<HouseInfo> houseInfos) {
        this.houseInfos = houseInfos;
    }

    public List<YearlyDealStats> getYearlyDealStats() {
        return yearlyDealStats;
    }

    public void setYearlyDealStats(List<YearlyDealStats> yearlyDealStats) {
        this.yearlyDealStats = yearlyDealStats;
    }

    public AreaDistribution getAreaDistribution() {
        return areaDistribution;
    }

    public void setAreaDistribution(AreaDistribution areaDistribution) {
        this.areaDistribution = areaDistribution;
    }

	public String getGptComment() {
		return gptComment;
	}

	public void setGptComment(String gptComment) {
		this.gptComment = gptComment;
	}
    
    
    
    
}
