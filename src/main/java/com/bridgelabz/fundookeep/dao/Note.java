package com.bridgelabz.fundookeep.dao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Note {
	
	@Id
	@GenericGenerator(name="nId",strategy = "increment")
	@GeneratedValue(generator = "nId")
	@Column(name = "NoteId", nullable = false)
	private Long id;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "TakeANote")
	private String takeANote;
	
	@Column(name = "NoteCreatedTime")
	private LocalDateTime noteCreated;
	
	@Column(name = "NoteUpdatedTime")
	private LocalDateTime noteUpdated;
	
	@Column(name = "isArchived", columnDefinition = "bit(1) default 0")
	private boolean isArchived;
	
	

}
