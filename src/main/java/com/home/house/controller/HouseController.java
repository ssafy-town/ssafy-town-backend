package com.home.house.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.house.model.FindDeal;
import com.home.house.model.HouseInfo;
import com.home.house.service.HouseService;

@RestController
@RequestMapping("/house")
public class HouseController {

	private final HouseService houseService;
	
	public HouseController(HouseService houseService) {
		this.houseService = houseService;
	}
	
	// HouseController -> HouseServiceImpl -> HouseMapper -> house.xml(mapper folder) -> List 반환 (ResponseEntity)
	
	// 동이름으로 실거래가 검색하기 (searchBySelectOption 대신 임시로 사용, 나중에 select 옵션으로 선택한 데이터를 파라미터로 사용)
	// http://localhost/house/searchByDong/동홍동
	@GetMapping("/searchByDong/{dong}")
	public ResponseEntity<?> searchByDong(@PathVariable("dong") String dong) throws Exception {
		System.out.println(dong);
		return new ResponseEntity<List<HouseInfo>>(houseService.searchByDong(dong), HttpStatus.OK);
	}
	
	// 키워드로 실거래가 검색하기
	// http://localhost/house/searchByApt/산격대우
	@GetMapping("/searchByApt/{name}")
	public ResponseEntity<?> searchByApt(@PathVariable("name") String name) throws Exception { 
		return new ResponseEntity<List<HouseInfo>>(houseService.searchByApt(name), HttpStatus.OK);
	}
	
	
	// 전체 시도 목록 가져오기 (view 만들기 위함)
	// http://localhost/house/getSidoList
	@GetMapping("/getSidoList")
	public ResponseEntity<?> getSidoList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getSidoList(), HttpStatus.OK);
	}
	
	
	// 전체 구군 목록 가져오기 
	// http://localhost/house/getGugunList
	@GetMapping("/getGugunList")
	public ResponseEntity<?> getGugunList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getGugunList(), HttpStatus.OK);
	}
	
	// 전체 동 목록 가져오기 
	// http://localhost/house/getDongList
	@GetMapping("/getDongList")
	public ResponseEntity<?> getDongList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getDongList(), HttpStatus.OK);
	}
	
	// 전체 년(2015-2022) 목록 가져오기 
	// http://localhost/house/getYearList
	@GetMapping("/getYearList")
	public ResponseEntity<?> getYearList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getYearList(), HttpStatus.OK);
	}
	
	// 전체 월(1-12) 목록 가져오기 
	// http://localhost/house/getMonthList
	@GetMapping("/getMonthList")
	public ResponseEntity<?> getMonthList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getMonthList(), HttpStatus.OK);
	}
	
//	client가 선택한 시도, 구군, 동, 년, 월 데이터를 받아서, 일치하는 실거래가를 가져오기. 미완
	@GetMapping("/searchBySelectOption/{sido}/{gugun}/{dong}/{year}/{month}")
	public ResponseEntity<?> searchBySelectOption(@PathVariable("sido") String sido,
	                                               @PathVariable("gugun") String gugun,
	                                               @PathVariable("dong") String dong,
	                                               @PathVariable("year") String year,
	                                               @PathVariable("month") String month) throws Exception {
	    
		try {
			FindDeal findDeal = new FindDeal();
			findDeal.setSido(sido);
			findDeal.setGugun(gugun);
			findDeal.setDong(dong);
			findDeal.setYear(year);
			findDeal.setMonth(month);
			
			System.out.println(findDeal.getDong() + " " + findDeal.getYear());
			
			return new ResponseEntity<>(houseService.searchBySelectOption(findDeal), HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
		}
	}
	
	
	
//	@GetMapping("/searchByDong/{dong}")
//	public ResponseEntity<?> searchByDong(@PathVariable("dong") String dong) throws Exception {
//		System.out.println(dong);
//		return new ResponseEntity<List<HouseInfo>>(houseService.searchByDong(dong), HttpStatus.OK);
//	}

}
