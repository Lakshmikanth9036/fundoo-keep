package com.bridgelabz.fundookeep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PutMapping("/registration/verify/{token}")
	private ResponseEntity<Response> userLoginVerification(@PathVariable String token) {
		try {
		if (service.updateVerificationStatus(token) > 0) {
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Successfully Verified...!!!"));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "Something went Wrong...!!!"));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}

	@GetMapping("/login")
	private ResponseEntity<Response> userLoginWithEmail(@RequestBody LoginDTO login) {
		User user = service.loginByEmailOrMobile(login);
		if (user != null) {
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Successfully Logged-in...!!!"));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(HttpStatus.NOT_FOUND.value(), "Warning Unable to login...!!!"));
	}
	
	@PostMapping("/login/forgotpassword")
	private ResponseEntity<Response> userLoginForgotpassword(@RequestParam String emailAddress) {
		service.sendTokentoMail(emailAddress);
		return ResponseEntity.status(HttpStatus.GONE)
				.body(new Response(HttpStatus.GONE.value(), "reset password link sent"));
	}

	@PutMapping("/login/forgotpassword/{token}")
	private ResponseEntity<Response> userLoginForgotpasswordVerify(@PathVariable String token,@RequestParam String newPassword) {
		try {
		if(service.resetPassword(token, newPassword)>0) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(HttpStatus.OK.value(), "Successfully changed the password"));
		}
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.BAD_REQUEST.value(), "unable to changed the password"));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		}
	}

}
