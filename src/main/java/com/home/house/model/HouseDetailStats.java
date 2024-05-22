package com.home.house.model;

import java.util.List;

public class HouseDetailStats {
    private List<HouseInfo> houseInfos;
    private List<YearlyDealStats> yearlyDealStats;
    private List<FloorDealStats> floorDealStats;


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

	public List<FloorDealStats> getFloorDealStats() {
		return floorDealStats;
	}

	public void setFloorDealStats(List<FloorDealStats> floorDealStats) {
		this.floorDealStats = floorDealStats;
	}

}
