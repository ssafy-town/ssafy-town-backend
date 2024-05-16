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
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		try {
			session.removeAttribute("member");
			return ResponseEntity.accepted().body("logout succeed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("logout failed");
		}
	}
	
	@GetMapping("/search/{id}")
	public ResponseEntity<?> searchMember(@PathVariable("id") String id) {
		try {
			Member member = memberService.searchMember(id);
			return ResponseEntity.accepted().body(member);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("search failed");
		}
	}
	
	@DeleteMapping("/deleteMember/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable("id") String id) {
		try {
			memberService.deleteMember(id);
			return ResponseEntity.accepted().body("delete succeed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("delete failed");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateMember(@RequestBody Member member) {
		try {
			memberService.updateMember(member);
			return ResponseEntity.accepted().body(memberService.searchMember(member.getId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update failed");
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getMemberList() throws Exception { 
		return new ResponseEntity<List<Member>>(memberService.getMemberList(), HttpStatus.OK);
	}
}
