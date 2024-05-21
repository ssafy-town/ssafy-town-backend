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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	
	// 뉴스 API를 불러와 크롤링하기
	// [GET] Param()
	// ex)
	// localhost/news
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

        // API response 얻기
        String responseBody = get(apiURL, requestHeaders);

        // JSON response 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON response Parsing 실패", e);
        }

        // JSON response에서 필요한 정보 추출하기
        List<String> newsList = new ArrayList<>();
        JsonNode itemsNode = jsonNode.get("items");
        if (itemsNode != null && itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                String titleWithTags = itemNode.get("title").asText();
                String title = removeHtmlTags(titleWithTags); // title에서 HTML 태그 제거
                String link = itemNode.get("link").asText();
                String descriptionWithTags = itemNode.get("description").asText();
                String description = removeHtmlTags(removeQuot(descriptionWithTags)); // description에서 HTML 태그 제거, &quot 제거
                String pubDateStr = itemNode.get("pubDate").asText();

                // pubDateStr을 파싱하여 년, 월, 일, 시, 분, 초 파싱하기
                String[] pubDateComponents = parsePubDate(pubDateStr);
                int year = Integer.parseInt(pubDateComponents[0]);
                int month = Integer.parseInt(pubDateComponents[1]);
                int day = Integer.parseInt(pubDateComponents[2]);
                int hour = Integer.parseInt(pubDateComponents[3]);
                int minute = Integer.parseInt(pubDateComponents[4]);
                int second = Integer.parseInt(pubDateComponents[5]);

                // Construct news item string
                String newsItem = String.format("Title: %s\nLink: %s\nDescription: %s\nYear: %s\nMonth: %s\nDay: %s\nHour: %s\nMinute: %s\nSecond: %s",
                        title, link, description, year, month, day, hour, minute, second);
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

    private static String removeQuot(String input) {
        return input.replace("&quot;", "\"");
    }
    
    @SuppressWarnings("deprecation")
	private static String[] parsePubDate(String pubDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = sdf.parse(pubDateStr);

            // Extract individual components
            String[] pubDateComponents = new String[6];
            pubDateComponents[0] = String.valueOf(date.getYear() + 1900); // 1900을 제거하면 현재 년도가 나옴
            pubDateComponents[1] = String.valueOf(date.getMonth() + 1); // index가 0부터 시작하므로 +1
            pubDateComponents[2] = String.valueOf(date.getDate());
            pubDateComponents[3] = String.valueOf(date.getHours());
            pubDateComponents[4] = String.valueOf(date.getMinutes());
            pubDateComponents[5] = String.valueOf(date.getSeconds());

            return pubDateComponents;
        } catch (ParseException e) {
            throw new RuntimeException("parse pubDate 실패", e);
        }
    }

    private static String removeHtmlTags(String html) {
        return html.replaceAll("<[^>]*>", "");
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
	            con.setRequestMethod("GET");
	            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
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

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
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
}
