package com.bridgelabz.fundookeep.service;

import java.util.List;

import com.bridgelabz.fundookeep.dao.Note;

public interface CollaboratorService {

	public void addCollaborator(String token, String emailAddress, Long nId);
	public List<Note> getCollaboratedNote(String token);
	public void removeCollaborator(String mail,Long cId,Long nId) ;
}
