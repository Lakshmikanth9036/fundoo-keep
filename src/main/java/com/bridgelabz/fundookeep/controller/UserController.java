package com.bridgelabz.fundookeep.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.LoginResponse;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
@PropertySource("classpath:message.properties")
public class UserController {

	@Autowired
	private UserService service;
	
//	@Autowired
//	private AmazonS3Service awsService;
	
	@Autowired
	private Environment env;
	
	@PostMapping("/register")
	public ResponseEntity<Response> userRegistration(@Valid @RequestBody RegistrationDTO register,BindingResult result){
		if(result.hasErrors())
			return new ResponseEntity<Response>(new Response(422, result.getAllErrors().get(0).getDefaultMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
		service.saveUser(register);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("200")));
	}
	
	@GetMapping("/hello/{text}")
	public ResponseEntity<String> welcomeUser(@PathVariable String text){
		return ResponseEntity.ok().body(text);
	}

	@PutMapping("/registration/verify/{token}")
	public ResponseEntity<Response> userLoginVerification(@PathVariable String token) {
		if(service.updateVerificationStatus(token)>0)
			return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("201")));
		return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), env.getProperty("102")));
	}

	@PutMapping("/login")
	public ResponseEntity<LoginResponse> userLoginWithEmail(@RequestBody LoginDTO login) {
		LoginResponse userResponse = service.loginByEmailOrMobile(login);
			return ResponseEntity.ok().body(userResponse);
		
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> userLoginForgotpassword(@RequestParam String emailAddress) {
		service.sendTokentoMail(emailAddress);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(HttpStatus.GONE.value(), env.getProperty("403")));
	}

	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetpassword(@PathVariable String token,
			@RequestParam String newPassword) {
		if(service.resetPassword(token, newPassword)>0)
			return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), env.getProperty("203")));
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.BAD_REQUEST.value(), env.getProperty("402")));
	}
	
//	@PostMapping("/uploadProfile")
//    public Map<String, String> uploadProfile(@RequestPart(value = "file") MultipartFile file,@RequestHeader(name = "header") String token)
//    {
//		awsService.uploadFileToS3Bucket(file, true,token);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");
//
//        return response;
//    }
//
//    @DeleteMapping("/deleteProfile")
//    public Map<String, String> deleteProfile(@RequestParam("file_name") String fileName,@RequestPart("token") String token)
//    {
//        awsService.deleteFileFromS3Bucket(fileName,token);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "file [" + fileName + "] removing request submitted successfully.");
//
//        return response;
//    }
//
//    @GetMapping("/getProfile")
//    public ResponseEntity<Response> getProfile(@RequestHeader(name = "header") String token) {
//    	String url = awsService.fetchObjectURL(token);
//    	return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("217"), url));
//    }
//    
//    @GetMapping("/getProfileDetails")
//    public ResponseEntity<Response> getProfileDetails(@RequestHeader(name = "header") String token) {
//    	return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("218"), service.getProfileDetails(token)));
//    }
}
