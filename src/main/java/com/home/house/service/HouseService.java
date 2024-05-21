package com.home.house.service;

import java.util.List;
import java.util.Map;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseDetailStats;
import com.home.house.model.HouseInfo;
import com.home.house.model.HouseStats;

public interface HouseService {
	List<HouseInfo> searchByDong(String dongName);
	List<HouseInfo> searchByKeyword(String keyword);
	List<String> getSidoList();
	List<String> getGugunList(String sidoName);
	List<String> getDongList(String gugunName);
	List<String> getYearList();
	List<String> getMonthList();
	List<HouseInfo> searchBySelectOptionExcludeDate(Map<String, String> params);
	List<HouseInfo> searchBySelectOption(FindDeal findDeal);
	List<HouseInfo> searchByAptCode(String aptCode);
	
	HouseStats searchBySelectOptionWithStats(Map<String, String> params);
	HouseStats searchByDongWithStats(String dongName);
	HouseStats searchByKeywordWithStats(String keyword);
	HouseDetailStats searchByDetail(String aptCode);
	
}
