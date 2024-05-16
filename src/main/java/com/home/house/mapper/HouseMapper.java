package com.home.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;

@Mapper
public interface HouseMapper {
	List<HouseInfo> searchByDong(String dong);
	List<HouseInfo> searchByApt(String aptName);
	List<String> getSidoList();
	List<String> getGugunList();
	List<String> getDongList();
	List<String> getYearList();
	List<String> getMonthList();
	List<HouseInfo> searchBySelectOption(FindDeal findDeal);
	
}
