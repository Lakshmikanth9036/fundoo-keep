package com.bridgelabz.fundookeep.dao;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Label {

	@Id
	@GenericGenerator(name = "lId", strategy = "increment")
	@GeneratedValue(generator = "lId")
	@Column(name = "NoteId")
	private Long labelId;
	
	@Column(name = "Label Name")
	private String labelName;
	
	@Column(name = "UserId")
	private Long userId;
	
	@ManyToMany
	private List<Note> notes;
	
	public Label() {
	}

	public Label(Long labelId, String labelName, Long userId, List<Note> notes) {
		super();
		this.labelId = labelId;
		this.labelName = labelName;
		this.userId = userId;
		this.notes = notes;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
}
