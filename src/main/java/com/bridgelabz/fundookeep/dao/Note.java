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
	@GenericGenerator(name = "nId", strategy = "increment")
	@GeneratedValue(generator = "nId")
	@Column(name = "NoteId", nullable = false)
	private Long noteId;

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

	@Column(name = "isPin", columnDefinition = "bit(1) default 0")
	private boolean isPin;

	@Column(name = "isTrash", columnDefinition = "bit(1) default 0")
	private boolean isTrash;

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

	public String getTakeANote() {
		return takeANote;
	}

	public void setTakeANote(String takeANote) {
		this.takeANote = takeANote;
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
