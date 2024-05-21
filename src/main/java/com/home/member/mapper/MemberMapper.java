package com.home.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.member.model.Member;

@Mapper
public interface MemberMapper {
	public void signUp(Member member);
	public boolean isMemberExists(String id);
	public Member login(Member member);
	public Member searchMember(String id);
	public Member checkIdName(Member member);
	public int deleteMember(String id);
	public int updateMember(Member member);
	List<Member> getMemberList();
}
