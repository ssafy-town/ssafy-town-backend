package com.home.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.house.model.Property;

@Mapper
public interface PropertyMapper {
	public List<Property> 	getList();
	public Property 		getDetail(String idx);
	public void 			add(Property property);
	public void 			update(Property property);
	public void 			remove(String idx);
//	public int 				count(PageBean bean);
}
