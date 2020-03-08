package com.bridgelabz.fundookeep.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.dto.UserResponse;
import com.bridgelabz.fundookeep.service.UserService;

@RestController
@RequestMapping("/user")
@PropertySource("classpath:message.properties")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private Environment env;
	
	@PostMapping("/register")
	public ResponseEntity<Response> userRegistration(@Valid @RequestBody RegistrationDTO register,BindingResult result){
		if(result.hasErrors())
			return new ResponseEntity<Response>(new Response(422, result.getAllErrors().get(0).getDefaultMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
		service.saveUser(register);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("200")));
	}

	@PutMapping("/registration/verify/{token}")
	private ResponseEntity<Response> userLoginVerification(@PathVariable String token) {
		if(service.updateVerificationStatus(token)>0)
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("201")));
		return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), env.getProperty("102")));
	}

	@GetMapping("/login")
	private ResponseEntity<Response> userLoginWithEmail(@RequestBody LoginDTO login) {
		UserResponse userResponse = service.loginByEmailOrMobile(login);
		if(userResponse != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("202"), userResponse));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), env.getProperty("404")));
	}

	@PostMapping("/login/forgotpassword")
	private ResponseEntity<Response> userLoginForgotpassword(@RequestParam String emailAddress) {
		service.sendTokentoMail(emailAddress);
		return ResponseEntity.status(HttpStatus.GONE)
				.body(new Response(HttpStatus.GONE.value(), env.getProperty("403")));
	}

	@PutMapping("/login/forgotpassword/{token}")
	private ResponseEntity<Response> userLoginForgotpasswordVerify(@PathVariable String token,
			@RequestParam String newPassword) {
		if(service.resetPassword(token, newPassword)>0)
			return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), env.getProperty("203")));
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.BAD_REQUEST.value(), env.getProperty("402")));
	}

}
