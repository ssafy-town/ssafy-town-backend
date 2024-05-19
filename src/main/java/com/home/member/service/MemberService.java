package com.home.member.service;

import java.util.List;

import com.home.member.model.Member;
import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;


public interface MemberService {
	
	public void signUp(Member member);
	public boolean isMemberExists(String id);
	public int login(Member member);
	public Member searchMember(String id);
	public int deleteMember(String id);
	public int updateMember(Member member);
	List<Member> getMemberList();
	public void addZzim(ZzimApt zzimApt);
	public List<ZzimAptDetail> getZzimList(String userId);
	public ZzimAptDetail getZzimListDetail(String aptCode);
	void removeZzim(ZzimApt zzimApt);
}
