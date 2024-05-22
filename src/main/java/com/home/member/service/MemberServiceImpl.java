package com.home.member.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.member.mapper.MemberMapper;
import com.home.member.mapper.ZzimAptMapper;
import com.home.member.model.Member;
import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private ZzimAptMapper zzimAptMapper;

	@Override
	public void signUp(Member member){
		memberMapper.signUp(member);
	}
	
	@Override
	public boolean isMemberExists(String id) {
	    Member member = memberMapper.searchMember(id);
	    return member != null;
	}
	
	@Override
	public boolean checkIdName(Member member) {
		Member checkMember = memberMapper.checkIdName(member);
		return checkMember != null;
	}

	@Override
	public Member login(Member member){
		return memberMapper.login(member);
	}

	@Override
	public Member searchMember(String id){
		return memberMapper.searchMember(id);
	}

	@Override
	public int deleteMember(String id){
		return memberMapper.deleteMember(id);
	}

	@Override
	public int updateMember(Member member){
		return memberMapper.updateMember(member);
	}

	@Override
	public List<Member> getMemberList() {
		return memberMapper.getMemberList();
	}

	@Override
	public void addZzim(ZzimApt zzimApt) {
		zzimAptMapper.addZzim(zzimApt);
	}
	
	@Override
	public int isZzimExists(ZzimApt zzimApt) {
	    return zzimAptMapper.isZzimExists(zzimApt);
	}

	@Override
	public List<ZzimAptDetail> getZzimListAllWithCnt() {
		List<ZzimAptDetail> list = zzimAptMapper.getZzimListAllWithCnt();
		return list;
	}
	
	@Override
	public List<ZzimAptDetail> getZzimList(String userId) {
	    try {
	         	List<ZzimAptDetail> list = zzimAptMapper.getZzimList(userId);
	            Collections.sort(list, (a, b) -> b.getCnt() - a.getCnt());
	            return list;
	    } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("찜 리스트 조회 실패", e);
	       		}
	}

	@Override
	public void removeZzim(ZzimApt zzimApt) {
		zzimAptMapper.removeZzim(zzimApt);
	}
	
	@Override
	public void removeZzimAll() {
		zzimAptMapper.removeZzimAll();
	}

	@Override
	public ZzimAptDetail getZzimListDetail(ZzimApt zzimApt) {
		 return zzimAptMapper.getZzimListDetail(zzimApt);
	}

}
