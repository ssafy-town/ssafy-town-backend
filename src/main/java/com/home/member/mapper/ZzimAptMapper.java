package com.home.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;


@Mapper
public interface ZzimAptMapper {
	public void addInterest(ZzimApt zzimApt);
	public List<ZzimAptDetail> getZzimList(String userId);
}
