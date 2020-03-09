package com.bridgelabz.fundookeep.service;

import java.util.List;

import com.bridgelabz.fundookeep.dao.Note;
import com.bridgelabz.fundookeep.dto.NoteDTO;

public interface NoteService {

	public void createNote(NoteDTO noteDTO,String token);
	public void updateNote(NoteDTO noteDTO,String token, Long noteId) ;
	public void deleteNote(String token, Long noteId);
	public List<Note> getAllNotes(String token);
	
}
