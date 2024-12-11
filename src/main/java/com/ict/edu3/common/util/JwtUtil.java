package com.ict.edu3.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;  // 비밀 키
  @Value("${jwt.expiration}")
  private long expiration;  // 만료시간

  private SecretKey getKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

    // 토큰 생성
    public String generateToken(String id){
      Map<String, Object> claims = new HashMap<>();
      claims.put("phone", "010-7777-9999");
      return generateToken(id, claims);
    }

  // 토큰 생성
  public String generateToken(String username, Map<String, Object> claims ){
    //내용을 더 추가하고 싶어도 보안 때문에 중요한 정보 넣으면 안됨.
    return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  //토큰을 받아서 추출한다.
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // 토큰 검사
  // UserDetails 유저 정보를 로드하며, 관리하는 역할을 한다. 
  public Boolean validateToken(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  //완료 시간 점검
  public Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  // 클레임 이름 추출
  // 클레임에서 특정 데이터 추출
  // 모든 클레임 추출
  // 만료 여부 확인
  // 만료 시간 추출


}
