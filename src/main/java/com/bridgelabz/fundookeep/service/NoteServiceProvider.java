package com.bridgelabz.fundookeep.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.Note;
import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.NoteDTO;
import com.bridgelabz.fundookeep.exception.UserException;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;
 
@Service
@PropertySource("classpath:message.properties")
public class NoteServiceProvider implements NoteService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private Environment env;
	
	public void createNote(NoteDTO noteDTO,String token) {
		Long uId = JwtUtils.decodeToken(token);
		Note note = new Note(noteDTO);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		user.getNotes().add(note);
		user = repository.save(user);
	}
	
	@Transactional
	public void updateNote(NoteDTO noteDTO, String token, Long noteId) {
		
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(404,env.getProperty("104")));
		filteredNote.setTitle(noteDTO.getTitle());
		filteredNote.setDescription(noteDTO.getDescription());
		filteredNote.setColor(noteDTO.getColor());
		filteredNote.setNoteUpdated(LocalDateTime.now());
		repository.save(user);
	}
	
	@Transactional
	public void deleteNote(String token, Long noteId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(404,env.getProperty("104")));
		notes.remove(filteredNote); 
		filteredNote.setNoteUpdated(LocalDateTime.now());
		repository.save(user);
	}
	
	@Transactional
	public void moveNoteToTrash(String token, Long noteId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(404,env.getProperty("104")));
		filteredNote.setTrash(!filteredNote.isPin()); 
		filteredNote.setNoteUpdated(LocalDateTime.now());
		repository.save(user);
	}
	
	@Transactional
	public void moveNoteToArchive(String token, Long noteId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(404,env.getProperty("104")));
		filteredNote.setArchived(!filteredNote.isArchived());
		filteredNote.setNoteUpdated(LocalDateTime.now());
		repository.save(user);
	}
	
	@Transactional
	public void pinNote(String token, Long noteId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(404,env.getProperty("104")));
		filteredNote.setPin(!filteredNote.isPin()); 
		filteredNote.setNoteUpdated(LocalDateTime.now());
		repository.save(user);
	}
	
	public List<Note> getAllNotes(String token){
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		List<Note> filteredNotes = new LinkedList<>();
		notes.forEach(note -> {if(!note.isArchived() && !note.isTrash()) filteredNotes.add(note);});
		return filteredNotes;
	}
	
	public List<Note> sortByTitle(String token){
		
		List<Note> notes = getAllNotes(token);
		
//		Arrays.parallelSort(notes, (n1,n2) -> {
//			return n1.getTitle().compareTo(n2.getTitle());
//		});
		Collections.sort(notes, (n1,n2) -> 
		{
			return n1.getTitle().compareTo(n2.getTitle());
		});
		return notes;
	}
	
	public List<Note> sortByDateAndTime(String token){
		List<Note> notes = getAllNotes(token);
		Collections.sort(notes, (n1,n2) -> 
		{
			return n1.getNoteCreated().compareTo(n2.getNoteCreated());
		});
		return notes;
	}
	
	public void createLable(String token,Long nId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		
	}
	
	
}
