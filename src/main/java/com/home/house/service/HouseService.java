package com.home.house.service;

import java.util.List;
import java.util.Map;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseDetailStats;
import com.home.house.model.HouseInfo;
import com.home.house.model.HouseStats;

public interface HouseService {
	HouseStats searchBySelectOptionWithStats(Map<String, String> params);
	List<HouseInfo> searchBySelectOptionExcludeDate(Map<String, String> params);
	List<HouseInfo> searchBySelectOption(FindDeal findDeal);
	
	HouseStats searchByKeywordWithStats(String keyword);
	List<HouseInfo> searchByKeyword(String keyword);
	String gptComment(Map<String, Object> params);
	
	HouseDetailStats searchByDetail(String aptCode);
	
	List<String> getSidoList();
	List<String> getGugunList(String sidoName);
	List<String> getDongList(String gugunName);
	List<String> getYearList();
	List<String> getMonthList();

	
	
	
}
