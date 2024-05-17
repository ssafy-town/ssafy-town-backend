package com.home.house.service;

import java.util.List;

import com.home.house.model.Property;


public interface PropertyService {
//	public List<Property> 	getList(PageBean bean);
	public Property 		getDetail(String idx);
	public void 			add(Property property);
	public void 			update(Property property);
	public void 			remove(String idx);
}
