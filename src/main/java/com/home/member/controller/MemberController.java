package com.home.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.member.model.Member;
import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;
import com.home.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	//	[POST] 회원가입
	//  ex)
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
	        // ID 중복 확인
	        if (memberService.isMemberExists(member.getId())) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 회원입니다.");
	        }
	        
	        memberService.signUp(member);
	        return ResponseEntity.accepted().body("회원 가입에 성공했습니다.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입에 실패했습니다.");
	    }
	}
	
	//	[POST] 로그인
	// 	ex)
	//	http://localhost:80/member/login
	//	{
	//	    "id" : "abcd",
	//	    "pw" : "1234",
	//	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member member, HttpSession session) {
	    try {
	        int id = memberService.login(member);
	        
	        // 존재하는 회원이라면
	        if(id > 0) {
	            session.setAttribute("member", member.getId());
	            return ResponseEntity.accepted().body("로그인 성공");
	        } else {	// 일치하는 회원이 없는 경우
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디 또는 비밀번호가 일치하지 않습니다.");
	        }
	    } catch (Exception e) {	// 로그인할 수 없는 경우
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
	    }
	}
	
	//	[GET] 로그아웃
	//	ex)
	//	http://localhost:80/member/logout
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
	    try {
	    	// session에 member가 저장되어 있다면
	        if(session.getAttribute("member") != null) {
	            session.removeAttribute("member");
	            return ResponseEntity.accepted().body("로그아웃되었습니다.");
	        } else {	// session에 member가 저장되어 있지 않다면
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 로그아웃된 상태입니다.");
	        }
	    } catch (Exception e) {	// "로그아웃할 수 없는 상태(예외)
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃에 실패했습니다.");
	    }
	}
	
	//  [POST] 비밀번호 찾기
	//  ex)
	//	id와 이름, 변경할 pw입력
	//	http://localhost/member/findPassword
	//	{
	//	    "id" : "abcd",
	//	    "name" : "최유리",
	//	    "pw" : "5678"
	//	}
	@PostMapping("/findPassword")
	public ResponseEntity<?> findPassword(@RequestBody Member member) {
	    try {
	        // ID와 이름을 통해 회원이 존재하는지 확인
	        Member foundMember = memberService.searchMember(member.getId());
	        if (foundMember != null && foundMember.getName().equals(member.getName())) {
	            // 찾은 회원이 존재하고 입력한 이름과 일치하면 입력한 비밀번호로 변경
	            foundMember.setPw(member.getPw());
	            memberService.updateMember(foundMember);
	            return ResponseEntity.accepted().body("비밀번호 변경 성공");
	        } else {	// ID와 이름이 일치하지 않는다면
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보가 일치하지 않습니다.");
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패");
	    }
	}

	
	
	
	//	[GET] 회원 검색
	// 	ex)
	//	http://localhost:80/member/search/abcd
	@GetMapping("/search/{id}")
	public ResponseEntity<?> searchMember(@PathVariable("id") String id) {
		try {
			Member member = memberService.searchMember(id);
			return ResponseEntity.accepted().body(member);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다.");
		}
	}
	
	//	[DELETE] 회원 탈퇴
	//	ex)
	//	http://localhost:80/member/deleteMember/abcd
	@DeleteMapping("/deleteMember/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable("id") String id) {
		try {
			int result = memberService.deleteMember(id);
			if(result != 0) {
				return ResponseEntity.accepted().body("회원 삭제에 성공했습니다.");				
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 삭제에 실패했습니다.");
		}
	}
	
	//	[PUT] 회원 정보 업데이트
	// 	ex)
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
			int result = memberService.updateMember(member);
			if(result !=0) {
				return ResponseEntity.accepted().body(memberService.searchMember(member.getId()));				
			}
			else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보가 일치하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보 업데이트에 실패했습니다.");
		}
	}
	
	//	[GET] 회원 리스트 조회
	//	ex)
	//	http://localhost:80/member/list
	@GetMapping("/list")
	public ResponseEntity<?> getMemberList() throws Exception { 
		return new ResponseEntity<List<Member>>(memberService.getMemberList(), HttpStatus.OK);
	}
	
	@PostMapping("/zzim")
	public ResponseEntity<?> addZzim(@RequestBody ZzimApt zzimApt){
		try {
			memberService.addLike(zzimApt);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e) {
			System.out.println("Error(" + this.getClass().getName() + ") "
					+ "("+Thread.currentThread().getStackTrace()[1].getMethodName() + "):" + e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("/zzimList/{userId}")
	public ResponseEntity<?> zzimList(@PathVariable("userId") String userId) {
		try {
			List<ZzimAptDetail> list = memberService.getZzimList(userId);
				return new ResponseEntity<List<ZzimAptDetail>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error(" + this.getClass().getName() + ") "
					+ "("+Thread.currentThread().getStackTrace()[1].getMethodName() + "):" + e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}

	}
	
}
