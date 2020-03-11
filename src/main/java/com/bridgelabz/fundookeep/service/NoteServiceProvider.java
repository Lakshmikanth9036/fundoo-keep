package com.bridgelabz.fundookeep.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.Label;
import com.bridgelabz.fundookeep.dao.Note;
import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LabelDTO;
import com.bridgelabz.fundookeep.dto.NoteDTO;
import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.exception.NoteException;
import com.bridgelabz.fundookeep.exception.UserException;
import com.bridgelabz.fundookeep.repository.NoteRepository;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;
 
@Service
@PropertySource("classpath:message.properties")
public class NoteServiceProvider implements NoteService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private Environment env;
	
	/**
	 * Creates a new note
	 */
	public void createNote(NoteDTO noteDTO,String token) {
		Long uId = JwtUtils.decodeToken(token);
		Note note = new Note(noteDTO);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		user.getNotes().add(note);
		repository.save(user);
	}
	
	/**
	 * Update the existing note
	 */
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
	
	/**
	 * Permenentaly delete the existing note  
	 */
	@Transactional
	public void deleteNote(String token, Long noteId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new UserException(404,env.getProperty("104")));
		notes.remove(filteredNote);
		noteRepository.delete(filteredNote);
		repository.save(user);
	}
	
	/**
	 * Moves the existing note to bin
	 */
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
	
	/**
	 * Moves the existing note to archive
	 */	
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
	
	/**
	 * Pin the existing note
	 */
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
	
	/**
	 * Get all notes user have
	 */
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
	
	public Response addOrCreateLable(String token,Long noteId,LabelDTO labelDTO) {
		Long uId = JwtUtils.decodeToken(token);
		Label lb = new Label(labelDTO);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		Note filteredNote = user.getNotes().stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404, "Not doesnt exist"));
		boolean exist = filteredNote.getLabels().stream().noneMatch(lbl -> lbl.getLabelName().equalsIgnoreCase(labelDTO.getLabelName()));
		if(exist) {
			boolean exst = user.getLabels().stream().noneMatch(lbl -> lbl.getLabelName().equalsIgnoreCase(labelDTO.getLabelName()));
			if(exst) {
				user.getLabels().add(lb);
				filteredNote.getLabels().add(lb);
				lb.getNotes().add(filteredNote);
				repository.save(user);
				return new Response(HttpStatus.OK.value(), "label created successfully",labelDTO);
			}
			else {
				Label l = user.getLabels().stream().filter(lbl -> lbl.getLabelName().equalsIgnoreCase(labelDTO.getLabelName())).findFirst().orElseThrow(() -> new NoteException(404, "Label Doesnt exists"));
				filteredNote.getLabels().add(l);
				l.getNotes().add(filteredNote);
				repository.save(user);
				return new Response(HttpStatus.OK.value(), "label added successfully",labelDTO);
			}
		}
		return new Response(HttpStatus.ALREADY_REPORTED.value(), "this label already exist",labelDTO);
	}
	
	public void removeLabel(String token,Long noteId,Long labelId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		Note filteredNote = user.getNotes().stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404, "Not doesnt exist"));
		boolean exist = filteredNote.getLabels().stream().noneMatch(lbl -> lbl.getLabelId().equals(labelId));
	}
}
