package com.home.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.house.mapper.PropertyMapper;
import com.home.house.model.Property;

@Service
public class PropertyServiceImpl implements PropertyService{
	
	@Autowired
	private PropertyMapper propertyMapper;

	@Override
	public List<Property> getListAll() {
		return propertyMapper.getListAll();
	}
	
	@Override
	public List<Property> getListMy(String userId) {
		return propertyMapper.getListMy(userId);
	}

	@Override
	public Property getDetailProperty(String idx) {
		return propertyMapper.getDetailProperty(idx);
	}

	@Override
	public void addProperty(Property property) {
		propertyMapper.addProperty(property);
	}

	@Override
	public void updateProperty(Property property) {
		propertyMapper.updateProperty(property);
	}

	@Override
	public int isPropertyExists(String idx) {
		return propertyMapper.isPropertyExists(idx);
	}

	
	@Override
	@Transactional
	public void removeProperty(String idx) {
		propertyMapper.removeProperty(idx);
	}



}
