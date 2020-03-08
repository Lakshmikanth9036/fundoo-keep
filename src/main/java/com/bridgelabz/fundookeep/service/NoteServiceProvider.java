package com.bridgelabz.fundookeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.Note;
import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.NoteDTO;
import com.bridgelabz.fundookeep.exception.UserException;
import com.bridgelabz.fundookeep.repository.UserRepository;
 
@Service
public class NoteServiceProvider {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private Environment env;
	
	public void createNote(NoteDTO noteDTO,Long id) {
		Note note = new Note(noteDTO);
		User user = repository.findById(id).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		user.getNotes().add(note);
		user = repository.save(user);
	}
	
	public int updateNote(NoteDTO)
	
}
