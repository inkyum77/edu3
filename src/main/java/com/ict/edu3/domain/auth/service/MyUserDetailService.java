package com.ict.edu3.domain.auth.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.auth.mapper.AuthMapper;
import com.ict.edu3.domain.auth.vo.MembersVO;
import com.ict.edu3.domain.auth.vo.UserVO;
import com.ict.edu3.domain.members.mapper.MembersMapper;

@Service
public class MyUserDetailService implements UserDetailsService{

  @Autowired
  private AuthMapper authMapper;

  @Autowired
  private MembersMapper membersMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserVO member = authMapper.selectMember(username);
    if (member == null) {
      throw new UsernameNotFoundException("없는 아이디 입니다.");
    }
    return new User(member.getM_id(), member.getM_pw(), new ArrayList<>());
  }

  // DB에서 개인 정보 추출
  public UserVO getUserDetail(String m_id){
    return authMapper.selectMember(m_id);
  }

  // 정보를 받아서 DB에 저장 하기
  public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User, String provider){
    String email = oAuth2User.getAttribute("email");
    String name = oAuth2User.getAttribute("name");

    System.out.println("provider : " + provider);
    String id = "";

    MembersVO mvo = new MembersVO();
    if (provider.equals("kakao")) {
        // 카카오는 id가 Long 타입으로 반환되므로 이를 String으로 변환
        Long kakaoID = oAuth2User.getAttribute("id"); // Long 타입으로 가져옴
        id = kakaoID.toString();  // Long을 String으로 변환
        mvo.setSns_email_kakao(email);
        mvo.setM_name(name);
        mvo.setM_id(id);
        mvo.setSns_provider("kakao");
    } else if (provider.equals("naver")) {
        // 네이버 ID는 String 타입으로 반환됨
        id = oAuth2User.getAttribute("id");
        mvo.setSns_email_naver(email);
        mvo.setM_name(name);
        mvo.setM_id(id);
        mvo.setSns_provider("naver");
    } else if (provider.equals("google")) {
      // google
        id = oAuth2User.getAttribute("id");
        mvo.setSns_email_naver(email);
        mvo.setM_name(name);
        mvo.setM_id(id);
        mvo.setSns_provider("naver");
    }

    // 아이디가 존재하면 DB에 있는 것, 아니면 DB에 없는 것
    MembersVO mvo2 = membersMapper.findUserByProvider(mvo);
    System.out.println("id: " + mvo.getM_id());
    if(mvo2 == null){
      membersMapper.insertUser(mvo);
    }
    return new User(mvo.getM_id(), "", Collections.emptyList()); // 빈 리스트로 권한 설정
  }
}
