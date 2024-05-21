package com.home.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.member.model.FindMember;
import com.home.member.model.Member;
import com.home.member.model.ZzimApt;
import com.home.member.model.ZzimAptDetail;
import com.home.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// 회원가입
	// [POST] Body(id, pw, name, addr, tel, regions, img)
	// ex)
	// http://localhost/member/signUp
	//	{
	//	    "id" : "abcd",
	//	    "pw" : "1234",
	//	    "name" : "김철수",
	//	    "addr" : "구미 진평동 사랑채",
	//	    "tel" : "010-9323-1234",
	//		"regions" : "경상북도 구미시 진평동",
	//		"img" : ""
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
	
	// 로그인
	// [POST] Session(id)
	//		  Body(id, pw)
	// 	ex)
	//	http://localhost/member/login
	//	{
	//	    "id" : "abcd",
	//	    "pw" : "1234",
	//	}
	@PostMapping("/login")
	public ResponseEntity<?> login(HttpSession session, @RequestBody Member member) {
	    try {
	        Member validMember = memberService.searchMember(member.getId());
	        
	        if(validMember != null) {	// 존재하는 회원이라면
	            session.setAttribute("member", member.getId());
	            
	            FindMember findMember = new FindMember();
	            findMember.setId(validMember.getId());
	            findMember.setName(validMember.getName());
	            findMember.setAddr(validMember.getAddr());
	            findMember.setTel(validMember.getTel());
	            findMember.setRegions(validMember.getRegions());
	            
	            return new ResponseEntity<>(findMember, HttpStatus.OK);
	        } else {	// 일치하는 회원이 없는 경우
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
	        }
	    } catch (Exception e) {	
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
	    }
	}
	
	// 로그아웃
	// [GET] Session(id)
	// ex)
	// http://localhost/member/logout
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
	    try {
	        if(session.getAttribute("member") != null) {	// session에 member가 저장되어 있다면
	        	String responseMessage = session.getAttribute("member") + "님 로그아웃";
	        	session.removeAttribute("member");
	        	
	            return ResponseEntity.accepted().body(responseMessage);
	        } else {	// session에 member가 저장되어 있지 않다면
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 필요");
	        }
	    } catch (Exception e) {	
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 실패");
	    }
	}
	
	// 현재 로그인되어 있는 멤버 확인
	// [GET] Session(id)
	// ex)
	// http://localhost/member/checkLogin)
	@GetMapping("/checkLogin")
	public ResponseEntity<?> checkLogin(HttpSession session) {
	    try {
	        if(session.getAttribute("member") != null) {	// session에 member가 저장되어 있다면
	        	String responseMessage = session.getAttribute("member") + "님 로그인중입니다";
	            return ResponseEntity.accepted().body(responseMessage);
	        } else {	// session에 member가 저장되어 있지 않다면
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 상태 ");
	        }
	    } catch (Exception e) {	
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 확인 실패");
	    }
	}

	// 전체 회원 조회
	// [GET] Param()
	// ex)
	// http://localhost/member/memberList
	@GetMapping("/memberList")
	public ResponseEntity<?> getMemberList() throws Exception { 
		return new ResponseEntity<List<Member>>(memberService.getMemberList(), HttpStatus.OK);
	}
	
	// 회원 검색
	// [GET] Param(id)
	// ex)
	// http://localhost/member/searchMember?id=abcd
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
	
	// 비밀번호 찾기
	// [POST] Body(id, name)
	// ex)
	// http://localhost/member/findPassword
	// {
	//	    "id" : "abcd",
	//	    "name" : "최유리",
	// }
	@PostMapping("/findPassword")
	public ResponseEntity<?> findPassword(@RequestBody Member member) {
	    try {
	        Member foundMember = memberService.searchMember(member.getId());
	        
	        if (foundMember != null && foundMember.getName().equals(member.getName())) {
	        	 return new ResponseEntity<>(foundMember.getPw(), HttpStatus.OK);
	        } else {	// ID와 이름이 일치하지 않는다면
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID 또는 이름 불일치");
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 찾기 실패");
	    }
	}
	
	// 회원 정보 업데이트
	// [PUT] Session(id)
	//		 Body(id, pw, name, addr, tel, img)
	//  body - raw (json)
	// 	ex)
	//	http://localhost/member/updateMember
	//	{
	//	    "pw" : "1234",
	//	    "name" : "박미선",
	//	    "addr" : "대구시 대현동 경북대 기숙사",
	//	    "tel" : "010-9345-1234",
	//		"regions" : "경상남도 창원시 용호동",
	//		"img" :	""
	//	}
	@PutMapping("/updateMember")
	public ResponseEntity<?> updateMember(HttpSession session, @RequestBody Member member) {
	    try {
	        String userId = (String) session.getAttribute("member");
	        if (userId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
	        }
	        
	        Member findMember = memberService.searchMember(userId);
	        if (findMember == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원이 존재하지 않음");
	        }

	        findMember.setId(userId);
	        
	        if (member.getAddr() != null) {
	            findMember.setAddr(member.getAddr());
	        }
	        if (member.getTel() != null) {
	            findMember.setTel(member.getTel());
	        }
	        if (member.getRegions() != null) {
	            findMember.setRegions(member.getRegions());
	        }
	        if (member.getImg() != null) {
	            findMember.setImg(member.getImg());
	        }
	        
	        int result = memberService.updateMember(findMember);
	        if (result != 0) {
	            return ResponseEntity.accepted().body(memberService.searchMember(findMember.getId()));
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보 업데이트 실패");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 업데이트 중 오류 발생");
	    }
	}
	
	// 회원 탈퇴
	// [DELETE] Session(id)
	// ex)
	// http://localhost/member/deleteMember
	@DeleteMapping("/deleteMember")
	public ResponseEntity<?> deleteMember(HttpSession session) {
		try {
			String userId = (String) session.getAttribute("member");
	        if (userId == null) {
	        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
	        }
			
			int result = memberService.deleteMember(userId);
			if(result != 0) {
				session.removeAttribute("member");
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
	
	// 찜하기
	// [POST] Session(userId)
	//		  Body(userId, aptCode)
	// ex)
	// http://localhost/member/addZzim
	// {
	//	    "userId" : "qwer",
	//	    "aptCode" : "47111000000006"
	// }
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 찜 추가한 아파트");
            }
            
            memberService.addZzim(zzimApt);
            return ResponseEntity.accepted().body("찜추가 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 추가 실패");
        }
    }
	
	// 찜 횟수 많은 5개 찜 리스트 보기 (전체에서)
	// [GET] Param()
	// ex)
	// http://localhost/member/zzimListAllWithCnt
	@GetMapping("/zzimListAllWithCnt")
 public ResponseEntity<?> zzimListAllWithCnt() {
    try {
        List<ZzimAptDetail> list = memberService.getZzimListAllWithCnt();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 아파트 없음");
        }
        return new ResponseEntity<List<ZzimAptDetail>>(list, HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 리스트 전체 조회 실패");
    }
}
	
	// 나의 찜 리스트 전체 조회
	// [GET] Session(userId)
	// ex)
	// http://localhost/member/zzimList
	@GetMapping("/myZzimList")
	 public ResponseEntity<?> zzimList(HttpSession session) {
        try {
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
            }
            
            List<ZzimAptDetail> list = memberService.getZzimList(userId);
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 아파트 없음");
            }
            return new ResponseEntity<List<ZzimAptDetail>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 리스트 전체 조회 실패");
        }
    }

	
	// 찜한 매물 상세 조회(aptCode)
	// [GET] Session(userId)
	//		 Param("aptCode")
	// ex)
	// http://localhost/member/zzimList?aptCode=47111000000002
	@GetMapping("/myZzimListDetail")
	public ResponseEntity<?> zzimListDetail(HttpSession session, @RequestParam("aptCode") String aptCode) {
        try {
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
            }
            
            ZzimApt zzimApt = new ZzimApt(userId, Long.parseLong(aptCode));
            ZzimAptDetail zzimAptDetail = memberService.getZzimListDetail(zzimApt);
            if (zzimAptDetail == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 데이터가 아님");
            }
            return new ResponseEntity<ZzimAptDetail>(zzimAptDetail, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜 리스트 상세 조회 실패");
        }
    }
	
	// 찜 삭제
	// [DELETE] Session(userId)
	// Param(aptCode)
	// ex)
	// http://localhost/member/removeZzim?aptCode=47111000000002
	@DeleteMapping("/removeZzim")
	 public ResponseEntity<?> removeZzim(@RequestParam("aptCode") String aptCode, HttpSession session) {
		try {
            String userId = (String) session.getAttribute("member");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
            }
            
            ZzimApt zzimApt = new ZzimApt(userId, Long.parseLong(aptCode));
            int zzimExists = memberService.isZzimExists(zzimApt);
            if (zzimExists == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜한 데이터 아님");
            }
            
            memberService.removeZzim(zzimApt);
            return ResponseEntity.accepted().body("찜삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("찜삭제 실패");
        }
    }
	
	
	// 모든 찜 리스트 삭제 (개발용)
	// [DELETE] Param()
	// ex)
	// http://localhost/member/removeZzimAll
	@DeleteMapping("/removeZzimAll")
	 public ResponseEntity<?> removeZzimAll() {
		try {
			memberService.removeZzimAll();		
			return ResponseEntity.accepted().body("전체 찜삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전체 찜 삭제 실패");
		}
       } 

}