package com.bridgelabz.fundookeep.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.bridgelabz.fundookeep.exception.TokenException;
import com.bridgelabz.fundookeep.repository.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

	@Autowired
	private static RedisService redisService;
	
	private static final String SECRET = "kanth@123";
	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	
	private JwtUtils() {
	}

	public static String generateToken(Long id) {
		return Jwts.builder().setSubject(String.valueOf(id))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public static String generateUserToken(Long id) {
		return Jwts.builder().setSubject(String.valueOf(id)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public static Long decodeToken(String jwt) {
		try {
			if (redisService.getToken(jwt) != null) {
				System.out.println(redisService.getToken(jwt));
				return redisService.getToken(jwt);
			} else {
				Claims claim = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
				Long id = Long.parseLong(claim.getSubject());
				redisService.putToken(jwt, id);
				return id;
			}
		} catch (TokenException e) {
			throw new TokenException(HttpStatus.REQUEST_TIMEOUT.value(), HttpStatus.REQUEST_TIMEOUT.toString());
		}
	}
}
