package com.home.house.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;

@Mapper
public interface HouseMapper {
	List<HouseInfo> searchByDong(String dongName);
	List<HouseInfo> searchByApt(String aptName);
	List<String> getSidoList();
	List<String> getGugunList(String sidoName);
	List<String> getDongList(String gugunName);
	List<String> getYearList();
	List<String> getMonthList();
	List<HouseInfo> searchByDongGugunAndDong(Map<String, String> params);
	List<HouseInfo> searchBySelectOption(FindDeal findDeal);
	
}
