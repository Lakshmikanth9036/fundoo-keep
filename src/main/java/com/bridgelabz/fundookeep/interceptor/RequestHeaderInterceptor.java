package com.bridgelabz.fundookeep.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.fundookeep.constants.Constants;
import com.bridgelabz.fundookeep.exception.InvalidHeaderException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter{
		
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		if(request.getRequestURI().contains("swagger"))
			return true;
		
		if(request.getHeader("header") == null || request.getHeader("header").isEmpty() ) 
			throw new InvalidHeaderException(500, Constants.INVALID_HEADER);
					
		log.info(request.getHeader("header"));
		return super.preHandle(request, response, handler);
		
	}
}
