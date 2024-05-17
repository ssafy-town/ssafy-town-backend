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
	
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

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
	public int login(Member member){
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
	public void addLike(ZzimApt zzimApt) {
		Member findMember = memberMapper.searchMember(zzimApt.getUserId());
		if(findMember==null) {
			System.out.println("없는 회원. 예외처리 해야함.");
		}
		zzimAptMapper.addInterest(zzimApt);
		
	}

	 @Override
	    public List<ZzimAptDetail> getZzimList(String userId) {
	        try {
	            List<ZzimAptDetail> list = zzimAptMapper.getZzimList(userId);
	            Collections.sort(list, (a, b) -> b.getCnt() - a.getCnt());
	            return list;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to retrieve Zzim list", e);
	        }
	    }
}
