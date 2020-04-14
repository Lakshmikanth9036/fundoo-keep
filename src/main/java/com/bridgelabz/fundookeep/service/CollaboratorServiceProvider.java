package com.bridgelabz.fundookeep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.repository.CollaboratorRepository;
import com.bridgelabz.fundookeep.repository.NoteRepository;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;
import com.bridgelabz.fundookeep.dao.Note;
import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.Collaborator;
import com.bridgelabz.fundookeep.exception.NoteException;
import com.bridgelabz.fundookeep.exception.UserException;

@Service
@PropertySource("classpath:message.properties")
public class CollaboratorServiceProvider implements CollaboratorService{

	@Autowired
	private UserRepository repository;

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private CollaboratorRepository crepository;
	
	@Autowired
	private Environment env;

	@Autowired
	private JwtUtils jwt;

	
	public void addCollaborator(String token, String emailAddress, Long nId) {
		
		Long uId = jwt.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404, env.getProperty("104")));
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(nId)).findFirst()
				.orElseThrow(() -> new NoteException(404, env.getProperty("105")));
		
		Optional<User> usr= repository.findByEmailAddress(emailAddress);
		
		if(usr.isPresent()) {
			User ur = usr.get();
			boolean exist = filteredNote.getCollaborators().stream().noneMatch(col -> col.getTo().equalsIgnoreCase(emailAddress));
			if(!exist)
				throw new NoteException(500, "Email address already exist");
			else {
			Collaborator c = new Collaborator(user.getEmailAddress(),emailAddress);
			crepository.save(c);
			filteredNote.getCollaborators().add(c);
			noteRepository.save(filteredNote);
			ur.getCollaborators().add(c);
			repository.save(user);
			repository.save(ur);
			}
		}
	}
	
	public List<Note> getCollaboratedNote(String token) {
		Long uId = jwt.decodeToken(token);
		User user = repository.findById(uId).orElseThrow(() -> new UserException(404, env.getProperty("104")));
		List<Collaborator> collaborators = user.getCollaborators();
		List<Note> collNotes = new ArrayList<>();
		if(collaborators.size() > 0) {
			collaborators.forEach(col -> {
				Optional<User> usr= repository.findByEmailAddress(col.getFrom());
				if(usr.isPresent()) {
					User u = usr.get();
					List<Note> notes = u.getNotes();
					notes.forEach(nts -> {
						nts.getCollaborators().forEach(collab -> {
							if(collab.getCId().equals(col.getCId())) {
								collNotes.add(nts);
							}
						});
					});
				}
			});
		}
		return collNotes;
	}
	
	@Transactional
	public void removeCollaborator(String mail,Long cId,Long nId) {		
		Optional<User> u = repository.findByEmailAddress(mail);
		if(u.isPresent()) {
		User user = u.get();
		List<Note> notes = user.getNotes();
		Note filteredNote = notes.stream().filter(note -> note.getNoteId().equals(nId)).findFirst()
				.orElseThrow(() -> new NoteException(404, env.getProperty("105")));
		List<Collaborator> collaborators = filteredNote.getCollaborators();
		Collaborator col = collaborators.stream().filter(coll -> coll.getCId().equals(cId)).findFirst().orElseThrow(() -> new NoteException(500, "Collaborator Does note exist....."));
		
		
		Optional<User> usr= repository.findByEmailAddress(col.getTo());
		
		if(usr.isPresent()) {
			User ur = usr.get();
			ur.getCollaborators().remove(col);
			repository.save(ur);
		}
		
		collaborators.remove(col);
		crepository.deleteById(col.getCId());
		repository.save(user);
		}
		else {
			throw new NoteException(500, "Email address dosnt exist");
		}
	}
	
}
