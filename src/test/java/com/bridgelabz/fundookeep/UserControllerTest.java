package com.bridgelabz.fundookeep;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bridgelabz.fundookeep.controller.UserController;
import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.service.AmazonS3Service;
import com.bridgelabz.fundookeep.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService service;
	
	@MockBean
	AmazonS3Service awsService;
	
	
	@Test
	public void loginTest() throws Exception{
		
		mockMvc.perform(put("/user/login")
		.content(asJsonString(new LoginDTO("kanth1997.9036@gmail.com", "kanth@123")))
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void registerTest() throws Exception{
		
		mockMvc.perform(post("/user/register")
		.content(asJsonString(new RegistrationDTO("Rama", "Arjuna", "rama@gmail.com", 9876543210l, "Rama@123")))
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void userLoginVerification() throws Exception{
		
		mockMvc.perform(put("/user/registration/verify/{token}","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
		.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void forgotPasswordTest() throws Exception{
		
		mockMvc.perform(post("/user/forgotpassword")
		.param("emailAddress", "kanth1997.9036@gmail.com")
		.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void restPasswordTest() throws Exception{
		
		mockMvc.perform(put("/user/resetpassword/{token}","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.param("newPassword", "Kanth@123")
		.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
