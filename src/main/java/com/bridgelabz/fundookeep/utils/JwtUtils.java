package com.bridgelabz.fundookeep.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.bridgelabz.fundookeep.exception.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

	private static final String SECRET = "kanth@123";
	private static final long JWT_TOKEN_VALIDITY = 5 * 60;
	
	private  JwtUtils() {}
	
	public static String generateToken(Long id) {
		return Jwts.builder().setSubject(String.valueOf(id)).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	
	public static  String generateUserToken(Long id) {
		return Jwts.builder().setSubject(String.valueOf(id)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	
	public static Long decodeToken(String jwt) {
		try {
		Claims claim = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
		return Long.parseLong(claim.getSubject());
		}
		catch(TokenException e) {
			throw new TokenException(HttpStatus.REQUEST_TIMEOUT.value(), HttpStatus.REQUEST_TIMEOUT.toString());
		}
	}
}
