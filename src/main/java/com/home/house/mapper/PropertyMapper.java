package com.home.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.house.model.Property;

@Mapper
public interface PropertyMapper {
	public void 			addProperty(Property property);
	
	public List<Property> 	getListAll();
	public List<Property> 	getListMy(String userId);
	public Property 		getDetailProperty(String idx);
	
	public void 			updateProperty(Property property);
	public int 				isPropertyExists(String idx);
	
	public void 			removeProperty(String idx);
}
