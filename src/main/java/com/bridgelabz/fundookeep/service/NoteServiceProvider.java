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
import com.bridgelabz.fundookeep.repository.LabelRepository;
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
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404,env.getProperty("105")));
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
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404,env.getProperty("105")));
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
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404,env.getProperty("105")));
		filteredNote.setTrash(!filteredNote.isTrash()); 
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
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404,env.getProperty("105")));
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
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404,env.getProperty("105")));
		filteredNote.setPin(!filteredNote.isPin()); 
		filteredNote.setNoteUpdated(LocalDateTime.now());
		repository.save(user);
	}
	
	@Transactional
	public void changeColorOfNote(String token,Long noteId,String color) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404,env.getProperty("105")));
		filteredNote.setColor(color);
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
		notes.forEach(note -> {if(!note.isArchived() && !note.isTrash() && !note.isPin()) filteredNotes.add(note);});
		return filteredNotes;
	}
	
	public List<Note> getPinnedNotes(String token){
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		List<Note> filteredNotes = new LinkedList<>();
		notes.forEach(note -> {if(note.isPin()) filteredNotes.add(note);});
		return filteredNotes;
	}
	
	public List<Note> getArchivedNotes(String token){
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		List<Note> filteredNotes = new LinkedList<>();
		notes.forEach(note -> {if(note.isArchived()) filteredNotes.add(note);});
		return filteredNotes;
	}
	
	public List<Note> getTrashNotes(String token){
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		List<Note> notes = user.getNotes();
		List<Note> filteredNotes = new LinkedList<>();
		notes.forEach(note -> {if(note.isTrash()) filteredNotes.add(note);});
		return filteredNotes;
	}
	
	/**
	 * Get all notes sorted by title
	 */
	public List<Note> sortByTitle(String token){
		
		List<Note> notes = getAllNotes(token);
		
		Collections.sort(notes, (n1,n2) -> 
		{
			return n1.getTitle().compareTo(n2.getTitle());
		});
		return notes;
	}
	
	/**
	 * Get all notes sorted by created time
	 */
	public List<Note> sortByDateAndTime(String token){
		List<Note> notes = getAllNotes(token);
		Collections.sort(notes, (n1,n2) -> 
		{
			return n1.getNoteCreated().compareTo(n2.getNoteCreated());
		});
		return notes;
	}
	
	/**
	 * Add or create a label for the note
	 */
	public Response addOrCreateLable(String token,Long noteId,LabelDTO labelDTO) {
		Long uId = JwtUtils.decodeToken(token);
		Label lb = new Label(labelDTO);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		Note filteredNote = user.getNotes().stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404, "105"));
		boolean exist = filteredNote.getLabels().stream().noneMatch(lbl -> lbl.getLabelName().equalsIgnoreCase(labelDTO.getLabelName()));
		if(exist) {
			boolean exst = user.getLabels().stream().noneMatch(lbl -> lbl.getLabelName().equalsIgnoreCase(labelDTO.getLabelName()));
			if(exst) {
				user.getLabels().add(lb);
				filteredNote.getLabels().add(lb);
				filteredNote.setNoteUpdated(LocalDateTime.now());
				lb.getNotes().add(filteredNote);
				repository.save(user);
				return new Response(HttpStatus.OK.value(), env.getProperty("209"),labelDTO);
			}
			else {
				Label l = user.getLabels().stream().filter(lbl -> lbl.getLabelName().equalsIgnoreCase(labelDTO.getLabelName())).findFirst().orElseThrow(() -> new NoteException(404, "Label Doesnt exists"));
				filteredNote.getLabels().add(l);
				filteredNote.setNoteUpdated(LocalDateTime.now());
				l.getNotes().add(filteredNote);
				repository.save(user);
				return new Response(HttpStatus.OK.value(), env.getProperty("210"),labelDTO);
			}
		}
		return new Response(HttpStatus.ALREADY_REPORTED.value(), env.getProperty("106"),labelDTO);
	}
	
	/**
	 * Remove the perticular label from the note
	 */
	@Transactional
	public Response removeLabel(String token,Long noteId,Long labelId) {
		Long uId = JwtUtils.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404,env.getProperty("104")));
		Note filteredNote = user.getNotes().stream().filter(note -> note.getNoteId().equals(noteId)).findFirst().orElseThrow(() -> new NoteException(404, "105"));
		List<Label> labels = filteredNote.getLabels();
		Label label = filteredNote.getLabels().stream().filter(lbl -> lbl.getLabelId().equals(labelId)).findFirst().orElse(null);
		if(label != null) {
			labels.remove(label);
			filteredNote.setNoteUpdated(LocalDateTime.now());
			noteRepository.save(filteredNote);
			repository.save(user);
			return new Response(HttpStatus.OK.value(), env.getProperty("211"));
		}
		return new Response(HttpStatus.NOT_FOUND.value(), env.getProperty("107"));
	}
}
