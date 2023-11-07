package com.hoozy.hoosshop.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hoozy.hoosshop.dto.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider { // 토큰을 만들어서 제공해주는 클래스
	
	// log4j2
	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

	private final Key key; // java.security의 key : JWT를 만들 때 사용하는 암호화 키값을 사용하기 위한 key

	public TokenProvider(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret); // BASE64 인코딩 한 시크릿 키를 다시 디코딩
		this.key = Keys.hmacShaKeyFor(keyBytes); // JWT를 생성할 때 쓰일 시크릿 키 인스턴스 생성
	}

	// Authentication 객체의 권한 정보를 이용해서 토큰 생성(문자열)
	public TokenDTO createToken(Authentication authentication) {

		// 권한들 가져오기
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime(); // 토큰 만료시간을 정하기 위해 현재 시간 가져오기

		// Access Token 생성
		Date accessTokenExpireDate = new Date(now + ACCESS_TOKEN_EXPIRE_TIME); // 토큰 만료시간

		String accessToken = Jwts.builder() // 토큰 생성
				.setSubject(authentication.getName()) // payload "sub" : "name"
				.claim(AUTHORITIES_KEY, authorities) // payload "auth" : "ROLE_USER
				.setExpiration(accessTokenExpireDate) // payload "exp" : ms초
				.signWith(key, SignatureAlgorithm.HS512) // header "alg" : "HS512"
				.compact();

		// Refresh Token 생성
		Date refreshTokenExpireDate = new Date(now + REFRESH_TOKEN_EXPIRE_TIME); // 토큰 만료시간

		String refreshToken = Jwts.builder() // 토큰 생성
				.setSubject(authentication.getName()) // payload "sub" : "name"
				.claim(AUTHORITIES_KEY, authorities) // payload "auth" : "ROLE_USER"
				.setExpiration(refreshTokenExpireDate) // payload "exp" : ms초
				.signWith(key, SignatureAlgorithm.HS512) // header "alg" : "HS512"
				.compact();
		
		return TokenDTO.builder()
				.grantType(BEARER_TYPE)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.tokenExpire(accessTokenExpireDate.getTime()) // 만료기간 액세스토큰 만료기간
				.build();
	}
	
	// 토큰을 사용하여 Authentication 객체 리턴
	public Authentication getAuthetication(String accessToken) {
		
		// 토큰 복호화
		Claims claims = parseClaims(accessToken);
		
		if(claims.get(AUTHORITIES_KEY) == null) { // 권한이 없으면
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		// claims 에서 권한 정보 가져오기
		// claims 형태의 토큰을 알맞게 정렬하고, GrantedAuthority를 상속받은 SimpleGrantedAuthority 형태의 새 List를 생성
		// SimpleGrantedAuthority 객체에는 인가가 들어있다.
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		// UserDetails 객체를 만들어서 Authentication 객체 리턴
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		// UsernamePasswordAuthenticationToken 인스턴스는 UserDetails를 생성하고 SecurityContext에 사용하기 위해 만든 절차이다.
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
	
	// 토큰을 검증하기 위한 메소드
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
	}
	
	// 토큰을 claims 객체로 만드는 메소드 -> 권한 정보가 있는지 체크 가능
	// 만료된 토큰이어도 정보를 꺼내기 위해 따로 분리했다.
	private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}