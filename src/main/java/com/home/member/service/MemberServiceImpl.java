package com.home.member.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.home.member.mapper.MemberMapper;
import com.home.member.model.Member;

@Service
public class MemberServiceImpl implements MemberService {

	private MemberMapper memberMapper;
	
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public void signUp(Member member){
		memberMapper.signUp(member);
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
}
