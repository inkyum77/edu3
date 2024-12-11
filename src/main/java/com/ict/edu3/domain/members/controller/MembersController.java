package com.ict.edu3.domain.members.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.auth.vo.DataVO;
import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.members.service.MembersServiceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@RestController
@RequestMapping("/api/members")
public class MembersController {

  @Autowired
  private MembersServiceImpl service;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/join")
  public DataVO membersJoin(@RequestBody MembersVO mvo){
    DataVO dataVO = new DataVO();

    // String rawPassword = mvo.getM_pw();
    // String endcodedPassword = passwordEncoder.encode(rawPassword);
    // mvo.setM_pw("mvo");

    
    // 아이디 중복 확인
    // MembersVO mvo1 = service.membersIdCheck(mvo.getM_id());

    // System.out.println(mvo1);
    // if(mvo1 != null) {
    //   dataVO.setSuccess(false);
    //   dataVO.setMessage("이미 있는 아이디 입니다.");
    //   return dataVO;
    // }


    // 비밀번호 암호화
    mvo.setM_pw(passwordEncoder.encode(mvo.getM_pw()));

    int result = service.joinMember(mvo);

    if (result > 0) {
      dataVO.setSuccess(true);
      dataVO.setMessage("회원가입 성공");
    } else {
      dataVO.setSuccess(false);
      dataVO.setMessage("회원가입 실패");
    }

    log.info("encoded password : " + mvo.getM_pw());
    log.info(mvo + "\n");
    return dataVO;
  }

  
  @PostMapping("/login")
  public DataVO memberLogin(@RequestBody MembersVO mvo) {
      DataVO dataVO = new DataVO();
      System.out.println("id : " + mvo.getM_id());
      System.out.println("password : " + mvo.getM_pw());
      try {
        // 사용자 정보 조회
        MembersVO membersVO = service.getMembersById(mvo.getM_id());
        System.out.println(membersVO);

        if(membersVO == null){
          dataVO.setSuccess(false);
          dataVO.setMessage("존재하지 않는 아이디입니다.");
          return dataVO;
        }
        
        System.err.println(mvo.getM_pw());
        System.err.println(membersVO.getM_pw());
        // 비밀번호 검증 받기
        if(!passwordEncoder.matches(mvo.getM_pw(), membersVO.getM_pw())){
          dataVO.setSuccess(false);
          dataVO.setMessage("비밀번호가 일치하지 않습니다.");
          return dataVO;
        }

        // JWT 토큰 생성 및 전송
        System.out.println(mvo.getM_id());
        String token = jwtUtil.generateToken(mvo.getM_id());

        // SecurityContext에 인증 객체 설정
        // 다른 컨트롤러, 서비스, 또는 보안 필터에서 인증 정보를 쉽게 가져올 수 있습니다.
        // 인증된 사용자 이름이나 권한을 사용해 요청 처리.
        // 인증 객체가 설정되면, Spring Security의 유틸리티 메서드를 사용해 현재 사용자 정보를 쉽게 가져올 수 있습니다.
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // String username = auth.getName(); // 인증된 사용자 이름

        // UsernamePasswordAuthenticationToken authenticationToken = new
        // UsernamePasswordAuthenticationToken(
        // membersVO.getM_id(), null, null); // 권한 정보 추가 가능
        // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // log.info("로그인 성공, SecurityContext에 인증 객체 설정 완료");

        dataVO.setData(membersVO);
        dataVO.setSuccess(true);
        dataVO.setMessage("로그인 성공");
        dataVO.setToken(token);
        return dataVO;

      } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("네트워크 오류");
            return dataVO;
      }
    }

    @GetMapping(value = "/idCheck", produces = "application/json")
  public DataVO getIdCheck(@RequestParam("m_id") String m_id) {
    System.out.println(m_id);
    DataVO dataVO = new DataVO();
    String member_id = service.membersIdCheck(m_id);
    if (member_id == null || member_id.isEmpty()) {
      dataVO.setSuccess(true);
      dataVO.setMessage("사용 가능한 아이디 입니다.");
    } else {
        dataVO.setSuccess(false);  // 실패 상태로 설정
        dataVO.setMessage("사용중인 아이디 입니다.");
    }
    System.out.println(dataVO);

    return dataVO;
  }

}
