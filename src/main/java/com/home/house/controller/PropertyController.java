package com.home.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.house.model.Property;
import com.home.house.service.PropertyService;

@RestController
@RequestMapping("/property")
@CrossOrigin("*")
public class PropertyController {

	@Autowired
	private PropertyService propertyService;

	// 글목록 가져오기, 페이징
	@GetMapping
	public ResponseEntity<?> getPropertyList() {
		try {
			List<Property> properties = propertyService.getList();
			return new ResponseEntity<List<Property>>(properties, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("아무 글도 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 글 상세 보기  
	@GetMapping("{idx}")
	public ResponseEntity<?> getPropertyDetail(@PathVariable("idx") String idx) {
		try {
			Property property = propertyService.getDetail(idx);
			return new ResponseEntity<Property>(property, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("해당 글을 가져오지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 글 등록
	@PostMapping
	public ResponseEntity<String> addProperty(@RequestBody Property property) {
		try {
			propertyService.add(property);
			return new ResponseEntity<String>("글 등록 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("글 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 글 업데이트
	@PutMapping
	public ResponseEntity<String> updateProperty(@RequestBody Property property) {
		try {
			propertyService.update(property);
			return new ResponseEntity<String>("글 업데이트 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("글 업데이트 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 글 삭제
	@DeleteMapping("{idx}")
	public ResponseEntity<String> removeProperty(@PathVariable("idx") String idx) {
		try {
			propertyService.remove(idx);
			return new ResponseEntity<String>("글 제거 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("글 제거 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}