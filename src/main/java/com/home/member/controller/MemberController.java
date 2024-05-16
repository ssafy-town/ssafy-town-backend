package com.home.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.member.model.Member;
import com.home.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
//	[POST] 회원가입
//	http://localhost:80/member/signUp
//	{
//	    "id" : "abcd",
//	    "pw" : "1234",
//	    "name" : "김철수",
//	    "addr" : "구미 진평동 사랑채",
//	    "tel" : "010-9323-1234"
//
//	}
	@PostMapping("/signUp")
	public ResponseEntity<?> signup(@RequestBody Member member) {
		try {
			memberService.signUp(member);
			return ResponseEntity.accepted().body("sign up succeed");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sign up failed");
		}
	}
	
//	[POST] 로그인
//	http://localhost:80/member/login
//	{
//	    "id" : "abcd",
//	    "pw" : "1234",
//	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member member, HttpSession session) {
		try {
			int id = memberService.login(member);
			session.setAttribute("member", id);
			
			if(id > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("login succeed");
			}
			else {
				return ResponseEntity.accepted().body("login failed");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("login failed");
		}
	}
	
//	[GET] 로그아웃
//	http://localhost:80/member/logout
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		try {
			session.removeAttribute("member");
			return ResponseEntity.accepted().body("logout succeed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("logout failed");
		}
	}
	
//	[GET] 회원 검색
//	http://localhost:80/member/search/abcd
	@GetMapping("/search/{id}")
	public ResponseEntity<?> searchMember(@PathVariable("id") String id) {
		try {
			Member member = memberService.searchMember(id);
			return ResponseEntity.accepted().body(member);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("search failed");
		}
	}
	
//	[DELETE] 회원 탈퇴
//	http://localhost:80/member/deleteMember/abcd
	@DeleteMapping("/deleteMember/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable("id") String id) {
		try {
			memberService.deleteMember(id);
			return ResponseEntity.accepted().body("delete succeed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("delete failed");
		}
	}
	
//	[PUT] 회원 정보 업데이트
//	http://localhost:80/member/update
//	{
//	    "id" : "abcd",
//	    "pw" : "1234",
//	    "name" : "박미선",
//	    "addr" : "대구시 대현동 경북대 기숙사",
//	    "tel" : "010-9345-1234"
//
//	}
	@PutMapping("/update")
	public ResponseEntity<?> updateMember(@RequestBody Member member) {
		try {
			memberService.updateMember(member);
			return ResponseEntity.accepted().body(memberService.searchMember(member.getId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update failed");
		}
	}
	
//	[GET] 회원 리스트 조회
//	http://localhost:80/member/list
	@GetMapping("/list")
	public ResponseEntity<?> getMemberList() throws Exception { 
		return new ResponseEntity<List<Member>>(memberService.getMemberList(), HttpStatus.OK);
	}
}
