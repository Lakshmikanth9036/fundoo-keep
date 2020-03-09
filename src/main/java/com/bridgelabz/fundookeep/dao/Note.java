package com.bridgelabz.fundookeep.dao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.fundookeep.dto.NoteDTO;

@Entity
public class Note {

	@Id
	@GenericGenerator(name = "nId", strategy = "increment")
	@GeneratedValue(generator = "nId")
	@Column(name = "NoteId", nullable = false)
	private Long noteId;

	@Column(name = "Title")
	private String title;

	@Column(name = "Description")
	private String description;

	@Column(name = "NoteCreatedTime")
	private LocalDateTime noteCreated;

	@Column(name = "NoteUpdatedTime")
	private LocalDateTime noteUpdated;

	@Column(name = "isArchived", columnDefinition = "bit(1) default 0")
	private boolean isArchived;

	@Column(name = "isPin", columnDefinition = "bit(1) default 0")
	private boolean isPin;

	@Column(name = "isTrash", columnDefinition = "bit(1) default 0")
	private boolean isTrash;

	@Column(name = "Color", columnDefinition = "varchar(10) default 'White'")
	private String color;

	public Note() {
		// TODO Auto-generated constructor stub
	}
	
	public Note(NoteDTO noteDTO) {
		this.title = noteDTO.getTitle();
		this.description = noteDTO.getDescription();
		this.noteCreated = LocalDateTime.now();
		this.noteUpdated = LocalDateTime.now();
		this.isArchived = false;
		this.isPin = false;
		this.isTrash = false;
		this.color = noteDTO.getColor();
	}



	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getNoteCreated() {
		return noteCreated;
	}

	public void setNoteCreated(LocalDateTime noteCreated) {
		this.noteCreated = noteCreated;
	}

	public LocalDateTime getNoteUpdated() {
		return noteUpdated;
	}

	public void setNoteUpdated(LocalDateTime noteUpdated) {
		this.noteUpdated = noteUpdated;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

}
