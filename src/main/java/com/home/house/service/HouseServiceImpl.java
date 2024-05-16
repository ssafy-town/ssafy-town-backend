package com.home.house.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.home.house.mapper.HouseMapper;
import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;

@Service
public class HouseServiceImpl implements HouseService{
	
	private final HouseMapper houseMapper;
	
	public HouseServiceImpl(HouseMapper houseMapper) {
		this.houseMapper = houseMapper;
	}
	

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
	public List<HouseInfo> searchByDongGugunAndDong(Map<String, String> params){
		return houseMapper.searchByDongGugunAndDong(params);
	}
	
	@Override
	public List<HouseInfo> searchBySelectOption(FindDeal findDeal) {
		return houseMapper.searchBySelectOption(findDeal);
	}
}