package com.home.house.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.home.house.mapper.HouseMapper;
import com.home.house.model.AreaDistribution;
import com.home.house.model.FindDeal;
import com.home.house.model.FloorDealStats;
import com.home.house.model.HouseDetailStats;
import com.home.house.model.HouseInfo;
import com.home.house.model.HouseStats;
import com.home.house.model.YearlyDealStats;

@Service
public class HouseServiceImpl implements HouseService{
	
	@Autowired
	private HouseMapper houseMapper;

	@Override
	public HouseStats searchBySelectOptionWithStats(Map<String, String> params) {
		List<HouseInfo> houseInfos = houseMapper.searchBySelectOptionExcludeDate(params);
		List<YearlyDealStats> yearlyDealStats = houseMapper.getDealStatsByYear(params);
		AreaDistribution areaDistribution = houseMapper.getAreaDistribution(params);
	
		HouseStats houseStats = new HouseStats();
		houseStats.setHouseInfos(houseInfos);
		houseStats.setYearlyDealStats(yearlyDealStats);
		houseStats.setAreaDistribution(areaDistribution);
		
		return houseStats;
	}

	@Override
	public List<HouseInfo> searchBySelectOptionExcludeDate(Map<String, String> params){
		return houseMapper.searchBySelectOptionExcludeDate(params);
	}
	
	@Override
	public List<HouseInfo> searchBySelectOption(FindDeal findDeal) {
		return houseMapper.searchBySelectOption(findDeal);
	}

	@Override
	public HouseStats searchByKeywordWithStats(String keyword) {
		List<HouseInfo> houseInfos = houseMapper.searchByKeyword(keyword);

	    List<YearlyDealStats> yearlyDealStats = houseMapper.getDealStatsByYearByKeyword(keyword);
	    AreaDistribution areaDistribution = houseMapper.getAreaDistributionByKeyword(keyword);

	    HouseStats houseStats = new HouseStats();
	    houseStats.setHouseInfos(houseInfos);
	    houseStats.setYearlyDealStats(yearlyDealStats);
	    houseStats.setAreaDistribution(areaDistribution);

	    return houseStats;
	}
	
	@Override
	public List<HouseInfo> searchByKeyword(String keyword) {
		return houseMapper.searchByKeyword(keyword);
	}

	@Override
	public HouseDetailStats searchByDetail(String aptCode) {
		List<HouseInfo> houseInfos = houseMapper.searchByAptCode(aptCode);
		
		List<YearlyDealStats> yearlyDealStats = houseMapper.getDealStatsByYearByAptCode(aptCode);
		List<FloorDealStats> floorDealStats = houseMapper.getDealStatsByFloorByAptCode(aptCode);
		
		HouseDetailStats houseDetailStats = new HouseDetailStats();
		houseDetailStats.setHouseInfos(houseInfos);
		houseDetailStats.setYearlyDealStats(yearlyDealStats);
		houseDetailStats.setFloorDealStats(floorDealStats);
		return houseDetailStats;
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
	public String gptComment(Map<String, Object> params) {
	    String keyword = (String) params.get("keyword");
	    List<YearlyDealStats> yearlyDealStats = (List<YearlyDealStats>) params.get("yearlyDealStats");

	    StringBuilder yearlyDealStringBuilder = new StringBuilder();
	    for (YearlyDealStats stat : yearlyDealStats) {
	        yearlyDealStringBuilder.append("Year: ")
	            .append(stat.getDealYear())
	            .append(", Max Deal Amount: ")
	            .append(stat.getMaxDealAmount())
	            .append(", Min Deal Amount: ")
	            .append(stat.getMinDealAmount())
	            .append(", Avg Deal Amount: ")
	            .append(stat.getAvgDealAmount())
	            .append("\n");
	    }

	    String prompt = "너는 부동산 전문 애널리스트야.\n\n\"" + keyword +
	        "\"이라는 키워드에 대한 집계데이터를 줄게. 너의 분석결과, 전망 등을 정확히 5줄로 제시해줘."
	        + " 유기적인 줄글로 작성해줘. 문항 나눠서 대답하지말고."
	        + " 만약 문장이 부자연스럽게 끝난다면, 그 문장은 작성하지 않아도 돼. 온점(.)으로 끝나는게 자연스러운 문장이야\n\n" +
	        yearlyDealStringBuilder.toString();

	    final String ENDPOINT = "https://api.openai.com/v1/chat/completions";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Bearer " + "sk-proj-ejU2R6Oftvm8trkyClcaT3BlbkFJbvBzQ6z0hxCgYm91vJEQ");

	    Map<String, Object> requestBody = new HashMap<>();
	    requestBody.put("model", "gpt-4");

	    List<Map<String, String>> messages = List.of(
	        Map.of("role", "system", "content", "You are a real estate analyst."),
	        Map.of("role", "user", "content", prompt)
	    );

	    requestBody.put("messages", messages);
	    requestBody.put("temperature", 0.7);
	    requestBody.put("max_tokens", 150);

	    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);

	    if (response.getStatusCode() == HttpStatus.OK) {
	        Map<String, Object> responseBody = response.getBody();
	        if (responseBody != null && responseBody.containsKey("choices")) {
	            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
	            if (choices != null && !choices.isEmpty()) {
	                Map<String, Object> choice = choices.get(0);
	                if (choice.containsKey("message")) {
	                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
	                    if (message.containsKey("content")) {
	                        String generatedContent = message.get("content").toString().trim();
	                        // Trim the content until a period is encountered at the end
	                        while (!generatedContent.endsWith(".")) {
	                            generatedContent = generatedContent.substring(0, generatedContent.length() - 1).trim();
	                        }
	                        return generatedContent;
	                    }
	                }
	            }
	        }
	    }

	    return "Error: Unable to generate response";
	}


	
	
}