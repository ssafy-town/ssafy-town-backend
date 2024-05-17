package com.home.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.home.house.mapper.PropertyMapper;
import com.home.house.model.Property;

public class PropertyServiceImpl implements PropertyService{
	
	@Autowired
	private PropertyMapper propertyMapper;

//	@Override
//	public List<Property> getList(PageBean bean) {
//	
//	}

	@Override
	public Property getDetail(String idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Property property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Property property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String idx) {
		// TODO Auto-generated method stub
		
	}

}
