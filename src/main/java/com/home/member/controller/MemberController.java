package com.home.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.member.model.Member;
import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;
import com.home.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//	[POST] 회원가입
		//  ex)
		//	http://localhost/member/signUp
		//  body - raw (json)
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
	        if (memberService.isMemberExists(member.getId())) {	// 회원 중복 확인
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 회원");
	        }
	        
	        memberService.signUp(member);
	        return ResponseEntity.accepted().body("회원 가입 성공");
	    } catch (Exception e) {	
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 실패.");
	    }
	}
	
	//	[POST] 로그인
		// 	ex)
		//	http://localhost/member/login
		//  body - raw (json)
		//	{
		//	    "id" : "abcd",
		//	    "pw" : "1234",
		//	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member member, HttpSession session) {
	    try {
	        int id = memberService.login(member);
	        
	        
	        if(id > 0) {	// 존재하는 회원이라면
	            session.setAttribute("member", member.getId());
	            return ResponseEntity.accepted().body("로그인 성공");
	        } else {	// 일치하는 회원이 없는 경우
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
	        }
	    } catch (Exception e) {	
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
	    }
	}
	
	//	[GET] 로그아웃
		//	ex)
		//  현재 저장되어 있는 session을 자동으로 불러와 속성 조회 후 있다면 로그아웃
		//	http://localhost/member/logout
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
	    try {
	        if(session.getAttribute("member") != null) {	// session에 member가 저장되어 있다면
	            session.removeAttribute("member");
	            return ResponseEntity.accepted().body("로그아웃 성공");
	        } else {	// session에 member가 저장되어 있지 않다면
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 로그아웃된 상태");
	        }
	    } catch (Exception e) {	
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 실패");
	    }
	}
	
	//  [POST] 비밀번호 찾기
		//  ex)
		//	id와 이름, 변경할 pw입력
		//  body - raw (json)
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
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID 또는 이름 불일치");
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패");
	    }
	}
	
	//	[GET] 회원 검색
		// 	RequestParam("id")
		// 	ex)
		//	http://localhost/member/searchMember?id=abcd
	@GetMapping("/searchMember")
	public ResponseEntity<?> searchMember(@RequestParam("id") String id) {
		try {
			Member member = memberService.searchMember(id);
			if(member != null) {
				return ResponseEntity.accepted().body(member);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 검색 실패");
		}
	}
	
	//	[DELETE] 회원 탈퇴
		// 	RequestParam("id")
		//	ex)
		//	http://localhost/member/deleteMember?id=abcd
	@DeleteMapping("/deleteMember")
	public ResponseEntity<?> deleteMember(@RequestParam("id") String id) {
		try {
			int result = memberService.deleteMember(id);
			if(result != 0) {
				return ResponseEntity.accepted().body("회원 삭제 성공");				
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 삭제 실패");
		}
	}
	
	//	[PUT] 회원 정보 업데이트
		//  body - raw (json)
		// 	ex)
		//	http://localhost/member/updateMember
		//	{
		//	    "id" : "abcd",
		//	    "pw" : "1234",
		//	    "name" : "박미선",
		//	    "addr" : "대구시 대현동 경북대 기숙사",
		//	    "tel" : "010-9345-1234"
		//	}
	@PutMapping("/updateMember")
	public ResponseEntity<?> updateMember(@RequestBody Member member) {
		try {
			int result = memberService.updateMember(member);
			if(result !=0) {
				return ResponseEntity.accepted().body(memberService.searchMember(member.getId()));				
			}
			else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보 불일치");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보 업데이트 실패");
		}
	}
	
	//	[GET] 회원 리스트 조회
		//	ex)
		//	http://localhost/member/memberList
	@GetMapping("/memberList")
	public ResponseEntity<?> getMemberList() throws Exception { 
		return new ResponseEntity<List<Member>>(memberService.getMemberList(), HttpStatus.OK);
	}
	
	//	[POST] 찜하기
		//  body - raw (json)
		//	ex)
		//	http://localhost/member/addZzim
		//	{
		//	    "userId" : "qwer",
		//	    "aptCode" : "47111000000006"
		//	}
	@PostMapping("/addZzim")
	public ResponseEntity<?> addZzim(HttpSession session, @RequestParam("aptCode") String aptCode) {
        try {
            ZzimApt zzimApt = new ZzimApt();
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
            }
            zzimApt.setUserId(userId);
            zzimApt.setAptCode(Long.parseLong(aptCode));
            if (memberService.isZzimExists(zzimApt) > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 찜 추가한 아파트입니다");
            }
            memberService.addZzim(zzimApt);
            return ResponseEntity.accepted().body("찜추가 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 추가 실패");
        }
    }
	
	//	[GET] 찜 리스트 전체 조회(session)
		//	ex)
		//	http://localhost/member/zzimList
	@GetMapping("/zzimList")
	 public ResponseEntity<?> zzimList(HttpSession session) {
        try {
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            List<ZzimAptDetail> list = memberService.getZzimList(userId);
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 아파트가 없습니다");
            }
            return new ResponseEntity<List<ZzimAptDetail>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 리스트 전체 조회 실패");
        }
    }

	
	//	[GET] 찜 리스트 상세 조회(aptCode로)
		//  RequestParam("aptCode")
		//	ex)
		//	http://localhost/member/zzimList?aptCode=47111000000002
	@GetMapping("/zzimListDetail")
	public ResponseEntity<?> zzimListDetail(@RequestParam("aptCode") String aptCode, HttpSession session) {
        try {
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            ZzimApt zzimApt = new ZzimApt(userId, Long.parseLong(aptCode));
            ZzimAptDetail zzimAptDetail = memberService.getZzimListDetail(zzimApt);
            if (zzimAptDetail == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 데이터가 아닙니다");
            }
            return new ResponseEntity<ZzimAptDetail>(zzimAptDetail, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 리스트 상세 조회 실패");
        }
    }
	
	
	
	//	[DELETE] 찜 삭제(aptCode로)
		//	RequestParam("aptCode")
		//	ex)
		//	http://localhost/member/removeZzim?aptCode=47111000000002
	@DeleteMapping("/removeZzim")
	 public ResponseEntity<?> removeZzim(@RequestParam("aptCode") String aptCode, HttpSession session) {
		try {
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            ZzimApt zzimApt = new ZzimApt(userId, Long.parseLong(aptCode));
            int zzimExists = memberService.isZzimExists(zzimApt);
            if (zzimExists == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 데이터가 아닙니다");
            }
            memberService.removeZzim(zzimApt);
            return ResponseEntity.accepted().body("찜삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜삭제 실패");
        }
    }
	
}
