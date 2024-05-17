package com.home.house.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	
//	// 글목록 가져오기, 페이징
//	@GetMapping
//	public ResponseEntity<List<Property>> getPropertyList(PageBean pagebean) {
//		pagebean.setInterval(10);
//		return new ResponseEntity<List<Property>>(propertyService.getList(pagebean), HttpStatus.OK);
//	}

   // 글 상세 보기  
	@GetMapping("{idx}")
	public ResponseEntity<Property> getPropertyDetail(@PathVariable String idx) {
		return new ResponseEntity<Property>(propertyService.getDetail(idx), HttpStatus.OK);
	}

	//글 등록
	@PostMapping
	public ResponseEntity<String> addProperty(@RequestBody Property property) {
		propertyService.add(property);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//글 업데이트
	@PutMapping
	public ResponseEntity<String> updateProperty(@RequestBody Property property) {
		
		propertyService.update(property);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//글 삭제
	@DeleteMapping("{idx}")
	public ResponseEntity<String> removeProperty(@PathVariable String idx) {
		propertyService.remove(idx);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
