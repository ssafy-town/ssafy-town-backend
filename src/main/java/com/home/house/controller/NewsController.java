package com.home.house.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/news")
@CrossOrigin("*")
public class NewsController {
	@GetMapping("")
	public ResponseEntity<?> news() {
	    String clientId = "WpsYcPTYp47T6ZURym7F"; // 애플리케이션 클라이언트 아이디
	    String clientSecret = "DrUaEdLwmq"; // 애플리케이션 클라이언트 시크릿

	    String text;
	    try {
	        text = URLEncoder.encode("부동산", "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("검색어 인코딩 실패", e);
	    }

	    String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text;

	    Map<String, String> requestHeaders = new HashMap<>();
	    requestHeaders.put("X-Naver-Client-Id", clientId);
	    requestHeaders.put("X-Naver-Client-Secret", clientSecret);

	    // Get API response
	    String responseBody = get(apiURL, requestHeaders);

	    // Parse JSON response
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode jsonNode;
	    try {
	        jsonNode = objectMapper.readTree(responseBody);
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException("Failed to parse JSON response", e);
	    }

	    // Extract relevant information from JSON response
	    List<String> newsList = new ArrayList<>();
	    JsonNode itemsNode = jsonNode.get("items");
	    if (itemsNode != null && itemsNode.isArray()) {
	        for (JsonNode itemNode : itemsNode) {
	            String title = itemNode.get("title").asText();
	            String link = itemNode.get("link").asText();
	            String descriptionWithTags = itemNode.get("description").asText();
	            String date = itemNode.get("pubDate").asText();
	            
	            // HTML 태그 제거
	            String description = removeHtmlTags(descriptionWithTags);
	            
	            String newsItem = String.format("Title: %s\nLink: %s\nDescription: %s\nPubDate: %s", title, link, description, date);
	            newsList.add(newsItem);
	        }
	    }

	    // Construct response
	    StringBuilder responseBuilder = new StringBuilder();
	    for (String newsItem : newsList) {
	        responseBuilder.append(newsItem).append("\n\n");
	    }

	    return new ResponseEntity<>(responseBuilder.toString(), HttpStatus.OK);
	}


	private static String removeHtmlTags(String html) {
	    Pattern pattern = Pattern.compile("<[^>]*>");
	    Matcher matcher = pattern.matcher(html);
	    return matcher.replaceAll("");
	    
	}
	
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
		
	}

	
	    

	
	
	
	
//	@GetMapping("")
//	public ResponseEntity<?> news() throws IOException {
//		StringBuilder sbUrl = new StringBuilder(
//				"https://serpapi.com/search.json?engine=naver&query=%08%EB%B6%80%EB%8F%99%EC%82%B0&where=news"); /* URL */
//		sbUrl.append("&" + URLEncoder.encode("api_key", "UTF-8")
//				+ "=d783a2fb69f65d8930131d49e6cfdbdad3d3bb75da7523feec22f3f53650c60a"); 
//		
//		URL url = new URL(sbUrl.toString());
//
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Content-type", "application/json");
//
//		BufferedReader rd;
//		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		} else {
//			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//		}
//		
//		StringBuilder sb = new StringBuilder();
//		String line;
//		while ((line = rd.readLine()) != null) {
//			sb.append(line).append('\n');
//		}
//		rd.close();
//		conn.disconnect();
//		
//		return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
//
//	}
	
}
