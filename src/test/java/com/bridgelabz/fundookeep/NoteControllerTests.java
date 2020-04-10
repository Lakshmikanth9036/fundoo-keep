package com.bridgelabz.fundookeep;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.bridgelabz.fundookeep.controller.NoteController;
import com.bridgelabz.fundookeep.dto.NoteDTO;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	NoteService nService;
	
	@MockBean
	UserRepository repository;

	@Test
	public void getAllNotesTest() throws Exception {
		
		mockMvc.perform(get("/note/getallNotes").header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void getPinnedNotesTest() throws Exception {
		
		mockMvc.perform(get("/note/getallPinnedNotes").header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void getArchivedNotesTest() throws Exception {
		
		mockMvc.perform(get("/note/getArchivedNotes").header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void getTrashNotesTest() throws Exception {
		
		mockMvc.perform(get("/note/getTrashNotes").header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void pinNoteTest() throws Exception {
		
		mockMvc.perform(put("/note/pin/{nId}",11).header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void moveNoteToTrashTest() throws Exception {
		
		mockMvc.perform(put("/note/trash/{nId}",11).header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void moveNoteToArchiveTest() throws Exception {
		
		mockMvc.perform(put("/note/archive/{nId}",11).header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void updateNoteTest() throws Exception {
		
		mockMvc.perform(put("/note/update/{nId}",11).header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.content(asJsonString(new NoteDTO("hello", "welcome to java", "#fdcfe8")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void changeColorTest() throws Exception {
		
		mockMvc.perform(put("/note/color").header("header",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.1e509W4ZgPedrXBS8RTGEt1qcqIZhLmjPVZHaolDMc1tn108ypOxFTPDg2jAYa0hXR-W3O9rF3H3lLGRmGBwUA")
				.param("nId", "1")
				.param("color", "#fdcfe8")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
