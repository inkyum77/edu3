package com.ict.edu3.jwt;

public class JwtResponse {
  private final String token; // final 이라 수정 불가

  public JwtResponse(String token){
    this.token = token;
  }

  public String getToken(){
    return token;
  }
  
}
