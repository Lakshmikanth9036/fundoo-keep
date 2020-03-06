package com.bridgelabz.fundookeep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public ResponseEntity<Response> userRegistration(@RequestBody RegistrationDTO register) {
		service.saveUser(register);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Register success...!!!"));
	}

	@GetMapping("/login/email")
	private ResponseEntity<Response> userLoginWithEmail(@RequestBody LoginDTO login) {
		User user = service.loginByEmail(login);
		if(user != null) {
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Successfully Logged-in...!!!",user));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "Warning Unable to login...!!!",user));
	}
	
	@GetMapping("/login/mobile")
	private ResponseEntity<Response> userLoginWithMobile(@RequestBody LoginDTO login) {
		User user = service.loginByMobile(login);
		if(user != null) {
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Successfully Logged-in...!!!",user));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "Warning Unable to login...!!!",user));
	}

	@GetMapping("/registration/verify/{id}")
	private ResponseEntity<Response> userLoginVerification(@PathVariable Long id) {
		if(service.updateVerificationStatus(id)>0) {
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Successfully Verified...!!!"));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.OK.value(), "Something went Wrong...!!!"));
	}

	@PostMapping("/login/forgotpassword")
	private ResponseEntity<String> userLoginForgotpassword() {
		return new ResponseEntity<String>(HttpStatus.GONE);
	}

}
