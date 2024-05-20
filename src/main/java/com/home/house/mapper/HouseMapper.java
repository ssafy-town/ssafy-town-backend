package com.home.house.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;
import com.home.house.model.YearlyDealStats;
import com.home.house.model.AreaDistribution;

@Mapper
public interface HouseMapper {
    List<HouseInfo> searchByDong(String dongName);
    List<HouseInfo> searchByKeyword(String keyword);
    List<String> getSidoList();
    List<String> getGugunList(String sidoName);
    List<String> getDongList(String gugunName);
    List<String> getYearList();
    List<String> getMonthList();
    List<HouseInfo> searchBySelectOptionExcludeDate(Map<String, String> params);
    List<HouseInfo> searchBySelectOption(FindDeal findDeal);

    // New methods for statistics
    List<YearlyDealStats> getDealStatsByYear(Map<String, String> params);
    AreaDistribution getAreaDistribution(Map<String, String> params);
    List<YearlyDealStats> getDealStatsByYearByDong(String dongName);
    AreaDistribution getAreaDistributionByDong(String dongName);
    List<YearlyDealStats> getDealStatsByYearByKeyword(String keyword);
    AreaDistribution getAreaDistributionByKeyword(String keyword);
    
}
