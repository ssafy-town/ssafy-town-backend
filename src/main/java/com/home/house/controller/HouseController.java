package com.home.house.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.house.model.FindDeal;
import com.home.house.model.GptForm;
import com.home.house.model.HouseInfo;
import com.home.house.service.HouseService;

@RestController
@RequestMapping("/house")
@CrossOrigin("*")
public class HouseController {
	
	@Autowired
	private HouseService houseService;

	// 실거래가 검색 With 통계 - 선택한 지역
    // [GET] Param(sidoName, gugunName, dongName, year<Option>, month<Option>)
    // ex)
    // http://localhost/house/searchBySelectOptionWithStats?sidoName=경상북도&gugunName=구미시&dongName=황상동
    @GetMapping("/searchBySelectOptionWithStats")
    public ResponseEntity<?> searchBySelectOptionWithStats(
            @RequestParam("sidoName") String sidoName,
            @RequestParam("gugunName") String gugunName,
            @RequestParam("dongName") String dongName,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "month", required = false) String month)
            throws Exception {
		        Map<String, String> params = new HashMap<>();
		        params.put("sidoName", sidoName);
		        params.put("gugunName", gugunName);
		        params.put("dongName", dongName);
		        if (year != null) params.put("year", year);
		        if (month != null) params.put("month", month);

		        return new ResponseEntity<>(houseService.searchBySelectOptionWithStats(params), HttpStatus.OK);
    }
	
	// 실거래가 검색 Without 통계 - 선택한 지역
    // [GET] Param(sidoName, gugunName, dongName, year<Option>, month<Option>)
    // ex)
    // http://localhost/house/searchBySelectOption?sidoName=경상북도&gugunName=구미시&dongName=황상동
	@GetMapping("/searchBySelectOption")
	public ResponseEntity<?> searchBySelectOption(
	        @RequestParam("sidoName") String sidoName,
	        @RequestParam("gugunName") String gugunName,
	        @RequestParam("dongName") String dongName,
	        @RequestParam(value = "year", required = false) String year,
	        @RequestParam(value = "month", required = false) String month) 
	        throws Exception {
				try {
						Map<String, String> params = new HashMap<>();
					    params.put("sidoName", sidoName);
					    params.put("gugunName", gugunName);
					    params.put("dongName", dongName);
					    
					    if (year == null || month == null) {  // year 또는 month 값이 없는 경우 전체 조회
					    	return new ResponseEntity<>(houseService.searchBySelectOptionExcludeDate(params),HttpStatus.OK);
					    } else {
					    		FindDeal findDeal = new FindDeal();
					            findDeal.setSidoName(sidoName);
					            findDeal.setGugunName(gugunName);
					            findDeal.setDongName(dongName);
					            findDeal.setYear(year);
					            findDeal.setMonth(month);
					            return new ResponseEntity<>(houseService.searchBySelectOption(findDeal), HttpStatus.OK);
					   }
					} catch (Exception e) {
					        e.printStackTrace();
					        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("searchBySelectOption failed");
					  }
			}
	
    // 실거래가 검색 With 통계 - 키워드
	// [GET] Param(keyword)
	// ex)
	// http://localhost/house/searchByKeywordWithStats?keyword=진평
    @GetMapping("/searchByKeywordWithStats")
    public ResponseEntity<?> searchByKeywordWithStats(@RequestParam("keyword") String keyword) throws Exception {
        return new ResponseEntity<>(houseService.searchByKeywordWithStats(keyword), HttpStatus.OK);
    }
    
    // gpt 의견
	// [GET] Param(keyword, yearlyDeal)
	// ex)
	// http://localhost/house/gptComment?keyword=진평&yearlyDeal="DealYear:2015 min...."
    @GetMapping("/gptComment")
    public ResponseEntity<?> gptComment(@RequestBody GptForm gptForm) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", gptForm.getKeyword());
        params.put("yearlyDealStats", gptForm.getYearlyDealStats());
        return new ResponseEntity<>(houseService.gptComment(params), HttpStatus.OK);
    }
    
    // 실거래가 검색 Without 통계 - 키워드
    // [GET] Param(keyword)
 	// ex)
 	// http://localhost/house/searchByKeyword?keyword=진평
	@GetMapping("/searchByKeyword")
	public ResponseEntity<?> searchByKeyword(@RequestParam("keyword") String keyword) throws Exception { 
		return new ResponseEntity<List<HouseInfo>>(houseService.searchByKeyword(keyword), HttpStatus.OK);
	}
	
    // 실거래가 검색 With 통계 - 단일 아파트(aptCode)
    // [GET] Param(aptCode)
    // ex)
    // http://localhost/house/searchByDetail?aptCode=47190000000136
    @GetMapping("/searchByDetail")
    public ResponseEntity<?> searchByDetail(@RequestParam("aptCode") String aptCode) throws Exception{
    	  return new ResponseEntity<>(houseService.searchByDetail(aptCode), HttpStatus.OK);
    }
	
	// 전체 시도 이름 목록 가져오기 
	// [GET] Param()
	// ex)
	// http://localhost/house/getSidoList
	@GetMapping("/getSidoList")
	public ResponseEntity<?> getSidoList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getSidoList(), HttpStatus.OK);
	}
	
	// 전체 구군 이름 목록 가져오기 - Param sidoName에 따라
	// [GET] Param(sidoName)
	// ex)
	// http://localhost/house/getGugunList?sidoName=경상북도
	@GetMapping("/getGugunList")
	public ResponseEntity<?> getGugunList(@RequestParam("sidoName") String sidoName) throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getGugunList(sidoName), HttpStatus.OK);
	}
	
	// 전체 동 이름 목록 가져오기 - Param gugunName에 따라 
	// [GET] Param(gugunName)
	// ex)
	// http://localhost/house/getDongList?gugunName=동작구
	@GetMapping("/getDongList")
	public ResponseEntity<?> getDongList(@RequestParam("gugunName") String gugunName) throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getDongList(gugunName), HttpStatus.OK);
	}
	
	// 전체 년(2015-2022) 목록 가져오기 
	// [GET] Param()
	// ex)
	// http://localhost/house/getYearList
	@GetMapping("/getYearList")
	public ResponseEntity<?> getYearList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getYearList(), HttpStatus.OK);
	}
	
	// 전체 월(1-12) 목록 가져오기 
	// [GET] Param()
	// ex)
	// http://localhost/house/getMonthList
	@GetMapping("/getMonthList")
	public ResponseEntity<?> getMonthList() throws Exception { 
		return new ResponseEntity<List<String>>(houseService.getMonthList(), HttpStatus.OK);
	}
	
}
