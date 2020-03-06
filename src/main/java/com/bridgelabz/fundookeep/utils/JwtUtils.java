package com.bridgelabz.fundookeep.utils;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

	private static final String SECRET = "kanth@123";
	public static final long JWT_TOKEN_VALIDITY = 5 * 60;
	
	public String generateToken(Long str) {
		return Jwts.builder().setSubject(String.valueOf(str)).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	
	public Long decodeToken(String jwt) {
		Claims claim = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
		return Long.parseLong(claim.getSubject());
	}
}
