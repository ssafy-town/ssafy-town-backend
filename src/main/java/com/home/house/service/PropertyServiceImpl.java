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
	public List<Property> getList() {
		return propertyMapper.getList();
	}
	
	@Override
	public List<Property> getMyList(String userId) {
		return propertyMapper.getMyList(userId);
	}

	@Override
	public Property getDetail(String idx) {
		return propertyMapper.getDetail(idx);
	}

	@Override
	public void add(Property property) {
		propertyMapper.add(property);
	}

	@Override
	public void update(Property property) {
		propertyMapper.update(property);
	}

	@Override
	public int isPropertyExists(String idx) {
		return propertyMapper.isPropertyExists(idx);
	}

	
	@Override
	@Transactional
	public void remove(String idx) {
		propertyMapper.remove(idx);
	}




}
