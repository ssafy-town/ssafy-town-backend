package com.home.house.service;

import java.util.List;

import com.home.house.model.Property;

public interface PropertyService {
	public void 			addProperty(Property property);
	
	public List<Property> 	getListAll();
	public List<Property> 	getListMy(String userId);
	public Property 		getDetailProperty(String idx);
	
	public void 			updateProperty(Property property);
	
	public int 				isPropertyExists(String idx);
	public void 			removeProperty(String idx);
}
