package com.home.house.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.home.house.model.FindDeal;
import com.home.house.model.FloorDealStats;
import com.home.house.model.HouseInfo;
import com.home.house.model.YearlyDealStats;
import com.home.house.model.AreaDistribution;

@Mapper
public interface HouseMapper {
    List<HouseInfo> searchBySelectOptionExcludeDate(Map<String, String> params);
    List<HouseInfo> searchBySelectOption(FindDeal findDeal);
    List<YearlyDealStats> getDealStatsByYear(Map<String, String> params);
    AreaDistribution getAreaDistribution(Map<String, String> params);
    
	List<HouseInfo> searchByKeyword(String keyword);
    List<YearlyDealStats> getDealStatsByYearByKeyword(String keyword);
    AreaDistribution getAreaDistributionByKeyword(String keyword);
    
	List<HouseInfo> searchByAptCode(String aptCode);
	List<YearlyDealStats> getDealStatsByYearByAptCode(String aptCode);
	List<FloorDealStats> getDealStatsByFloorByAptCode(String aptCode);
	
	List<String> getSidoList();
    List<String> getGugunList(String sidoName);
    List<String> getDongList(String gugunName);
    List<String> getYearList();
    List<String> getMonthList();
    
}
