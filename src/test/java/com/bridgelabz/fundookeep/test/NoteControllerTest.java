package com.bridgelabz.fundookeep.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bridgelabz.fundookeep.controller.NoteController;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

	@Autowired
	private MockMvc mvc;
	
	
	@Test
	public void getAllNotesTest() throws Exception{
		mvc.perform( MockMvcRequestBuilders
			      .get("/note/getallNotes").header("header", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA"))
				  .andExpect(MockMvcResultMatchers.status().isOk());
	}
}
