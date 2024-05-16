package com.home.house.service;

import java.util.List;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;

public interface HouseService {
	List<HouseInfo> searchByDong(String dong);
	List<HouseInfo> searchByApt(String aptName);
	List<String> getSidoList();
	List<String> getGugunList();
	List<String> getDongList();
	List<String> getYearList();
	List<String> getMonthList();
	List<HouseInfo> searchBySelectOption(FindDeal findDeal);
	
}
