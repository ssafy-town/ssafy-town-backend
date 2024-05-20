package com.home.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;


@Mapper
public interface ZzimAptMapper {
	   void addZzim(ZzimApt zzimApt);
	    List<ZzimAptDetail> getZzimList(String userId);
	    ZzimAptDetail getZzimListDetail(ZzimApt zzimApt);
	    void removeZzim(ZzimApt zzimApt);
	    int isZzimExists(ZzimApt zzimApt);
}
