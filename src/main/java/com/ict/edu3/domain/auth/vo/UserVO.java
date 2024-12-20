package com.ict.edu3.domain.auth.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

// UserDetails : Spring Security를 거친 사용자 정보들
@Data
public class UserVO implements UserDetails{

  private String m_id="";
  private String m_pw="";
  private String email="";
  private String phone="";
  private String m_name="";

  private String sns_id = "";
  private String provider="";
  private String sns_email_kakao="";
  private String sns_email_naver="";
  private String sns_email_google="";

  private List<GrantedAuthority> authorities = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.m_pw;
  }

  @Override
  public String getUsername() {
    return this.m_name;
  }

}
