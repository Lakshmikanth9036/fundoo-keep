package com.bridgelabz.fundookeep.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Collaborator {
	@Id
	@GenericGenerator(name = "cId", strategy = "increment")
	@GeneratedValue(generator = "cId")
	@Column(name = "cId")
	private Long cId;
	
	@Column(name="fromMail")
	private String from;
	
	@Column(name="toMail")
	private String to;
	

	public Collaborator(String from, String to) {
		this.from = from;
		this.to = to;
	}
}
