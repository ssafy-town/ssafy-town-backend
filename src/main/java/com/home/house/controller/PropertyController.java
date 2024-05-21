package com.home.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.house.model.Property;
import com.home.house.service.PropertyService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/property")
@CrossOrigin("*")
public class PropertyController {

	@Autowired
	private PropertyService propertyService;

	// Create - 이어살기 매물 글 등록하기
	// [POST] Session(userId)
	//		  Body(title, expireDate, price, managementPrice, managementDesc, roomType, 
	//			   floorNow, floorTotal, roomSize, content, img, keyword, addr, dongType)
	// ex)
	// http://localhost/property/add
	//	{
	//	    "title" : "첫글1",
	//	    "expireDate" : "20240511",
	//	    "price" : "40",
	//	    "managementPrice" : "5",
	//	    "managementDesc" : "수도, 전기 포함",
	//	    "roomType" : "원룸",
	//	    "floorNow" : "5",
	//	    "floorTotal" : "10",
	//	    "roomSize" : "15",
	//	    "content" : "이어살기 구합니다",
	//	    "img" : "null",
	//	    "keyword" : "#깨끗#교통좋음",
	//	    "addr" : "구미 진평동",
	//	    "dongType" : "진평동"
	//	}
	@PostMapping("/addProperty")
	public ResponseEntity<String> addProperty(HttpSession session, @RequestBody Property property) {
		try {
			String userId = (String) session.getAttribute("member");
			if (userId == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
	        }
				
	        property.setId(userId);
	        propertyService.addProperty(property);
	         
	        return new ResponseEntity<String>("글 등록 성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("글 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Read - 글 목록 전체 가져오기 (모든 회원)
	// [GET] Param()
	// ex)
	// localhost/property/getListAll
	@GetMapping("/getListAll")
	public ResponseEntity<?> getListAll() {
		try {
			List<Property> properties = propertyService.getListAll();
			return new ResponseEntity<List<Property>>(properties, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("아무 글도 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Read - 내가 작성한 글 목록 전체 가져오기
	// [GET] Session(userId)
	// ex)
	// localhost/property/getListMy
	@GetMapping("/getListMy")
	public ResponseEntity<?> getMListMy(HttpSession session) {
		try {
			 String userId = (String) session.getAttribute("member");
	         if (userId == null) {
	        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
	         }
	         
	         List<Property> properties = propertyService.getListMy(userId);
	         return new ResponseEntity<List<Property>>(properties, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("아무 글도 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Read - 글 상세 보기
	// [GET] Param(idx)
	// ex)
	// localhost/property/getDetail?idx=5
	@GetMapping("/getDetailProperty")
	public ResponseEntity<?> getDetailProperty(@RequestParam("idx") String idx) {
		try {
			Property property = propertyService.getDetailProperty(idx);
			return new ResponseEntity<Property>(property, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("해당 글을 가져오지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Update - 내가 작성한 글 수정하기
	// [Put] Session(userId)
	//		 Param(idx)
	//	 	 Body(title, expireDate, price, managementPrice, managementDesc, roomType, 
	//			  floorNow, floorTotal, roomSize, content, img, keyword, addr, dongType)
	// ex)
	// localhost/property/update
	
	@PutMapping("/updateProperty")
	public ResponseEntity<String> updateProperty(HttpSession session, @RequestParam("idx") String idx, @RequestBody Property property) {
		try {
	        String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
            }
            
            Property findProperty = propertyService.getDetailProperty(idx);
            findProperty.setTitle(property.getTitle());
            findProperty.setExpireDate(property.getExpireDate());
            findProperty.setPrice(property.getPrice());
            findProperty.setManagementPrice(property.getManagementPrice());
            findProperty.setManagementDesc(property.getManagementDesc());
            findProperty.setRoomType(property.getRoomType());
            findProperty.setFloorNow(property.getFloorNow());
            findProperty.setFloorTotal(property.getFloorTotal());
            findProperty.setRoomSize(property.getRoomSize());
            findProperty.setContent(property.getContent());
            findProperty.setImg(property.getImg());
            findProperty.setKeyword(property.getKeyword());
            findProperty.setAddr(property.getAddr());
            findProperty.setDongType(property.getDongType());
            
			propertyService.updateProperty(property);
			return new ResponseEntity<String>("글 업데이트 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("글 업데이트 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Delete - 글 삭제
	// [DELETE] Session(userId)
	//			Param(idx)
	// ex)
	// localhost/property/remove?idx=1
	@DeleteMapping("/removeProperty")
	public ResponseEntity<String> removeProperty(HttpSession session, @RequestParam("idx") String idx) {
		try {
			 String userId = (String) session.getAttribute("member");
	            if (userId == null) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
	         }
			
	        int propertyExists = propertyService.isPropertyExists(idx);    
	        if (propertyExists == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 글");
            }    
	        
			propertyService.removeProperty(idx);
			return new ResponseEntity<String>("글 제거 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("글 제거 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}