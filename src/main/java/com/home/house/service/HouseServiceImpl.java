package com.home.house.service;

import java.util.List;

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
	public List<HouseInfo> searchByDong(String dong) {
		return houseMapper.searchByDong(dong);
	}

	@Override
	public List<HouseInfo> searchByApt(String aptName) {
		return houseMapper.searchByApt(aptName);
	}

	@Override
	public List<String> getSidoList() {
		return houseMapper.getSidoList();
	}

	@Override
	public List<String> getGugunList() {
		return houseMapper.getGugunList();
	}

	@Override
	public List<String> getDongList() {
		return houseMapper.getDongList();
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
	public List<HouseInfo> searchBySelectOption(FindDeal findDeal) {
		return houseMapper.searchBySelectOption(findDeal);
	}
}