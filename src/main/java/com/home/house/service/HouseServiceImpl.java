package com.home.house.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.house.mapper.HouseMapper;
import com.home.house.model.AreaDistribution;
import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;
import com.home.house.model.HouseStats;
import com.home.house.model.YearlyDealStats;

@Service
public class HouseServiceImpl implements HouseService{
	
	@Autowired
	private HouseMapper houseMapper;

	@Override
	public List<HouseInfo> searchByDong(String dongName) {
		return houseMapper.searchByDong(dongName);
	}

	@Override
	public List<HouseInfo> searchByKeyword(String keyword) {
		return houseMapper.searchByKeyword(keyword);
	}

	@Override
	public List<String> getSidoList() {
		return houseMapper.getSidoList();
	}

	@Override
	public List<String> getGugunList(String sidoName) {
		return houseMapper.getGugunList(sidoName);
	}

	@Override
	public List<String> getDongList(String gugunName) {
		return houseMapper.getDongList(gugunName);
	}

	@Override
	public List<String> getYearList() {
		return houseMapper.getYearList();
	}

	@Override
	public List<String> getMonthList() {
		return houseMapper.getMonthList();
	}

	@Override
	public List<HouseInfo> searchBySelectOptionExcludeDate(Map<String, String> params){
		return houseMapper.searchBySelectOptionExcludeDate(params);
	}
	
	@Override
	public List<HouseInfo> searchBySelectOption(FindDeal findDeal) {
		return houseMapper.searchBySelectOption(findDeal);
	}
	
	 @Override
	    public HouseStats searchBySelectOptionWithStats(Map<String, String> params) {
	        List<HouseInfo> houseInfos = houseMapper.searchBySelectOptionExcludeDate(params);
	        List<YearlyDealStats> yearlyDealStats = houseMapper.getDealStatsByYear(params);
	        AreaDistribution areaDistribution = houseMapper.getAreaDistribution(params);

	        HouseStats houseStats = new HouseStats();
	        houseStats.setHouseInfos(houseInfos);
	        houseStats.setYearlyDealStats(yearlyDealStats);
	        houseStats.setAreaDistribution(areaDistribution);

	        return houseStats;
	    }

	    @Override
	    public HouseStats searchByDongWithStats(String dongName) {
	        List<HouseInfo> houseInfos = houseMapper.searchByDong(dongName);
	        List<YearlyDealStats> yearlyDealStats = houseMapper.getDealStatsByYearByDong(dongName);
	        AreaDistribution areaDistribution = houseMapper.getAreaDistributionByDong(dongName);

	        HouseStats houseStats = new HouseStats();
	        houseStats.setHouseInfos(houseInfos);
	        houseStats.setYearlyDealStats(yearlyDealStats);
	        houseStats.setAreaDistribution(areaDistribution);

	        return houseStats;
	    }

	    @Override
	    public HouseStats searchByKeywordWithStats(String keyword) {
	        List<HouseInfo> houseInfos = houseMapper.searchByKeyword(keyword);

	        List<YearlyDealStats> yearlyDealStats = houseMapper.getDealStatsByYearByKeyword(keyword);
	        AreaDistribution areaDistribution = houseMapper.getAreaDistributionByKeyword(keyword);

	        HouseStats houseStats = new HouseStats();
	        houseStats.setHouseInfos(houseInfos);
	        houseStats.setYearlyDealStats(yearlyDealStats);
	        houseStats.setAreaDistribution(areaDistribution);

	        return houseStats;
	    }
	
}