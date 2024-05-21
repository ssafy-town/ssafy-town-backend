package com.home.house.service;

import java.util.List;

import com.home.house.model.Property;
import com.home.member.model.ZzimApt;


public interface PropertyService {
	public List<Property> 	getList();
	public List<Property> 	getMyList(String userId);
	public Property 		getDetail(String idx);
	public void 			add(Property property);
	public void 			update(Property property);
	public int isPropertyExists(String idx);
	public void 			remove(String idx);
}
