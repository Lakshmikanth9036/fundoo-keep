package com.bridgelabz.fundookeep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundookeep.dao.Note;
import com.bridgelabz.fundookeep.dto.NoteDTO;
import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.service.NoteService;
import com.bridgelabz.fundookeep.utils.JwtUtils;

@RestController
@RequestMapping("/note")
@PropertySource("classpath:message.properties")
public class NoteController {
	
	@Autowired
	private NoteService nService;
	
	@Autowired
	private Environment env;

	@PostMapping(value = "/createNote")
	private ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDTO,@RequestHeader String token){
		
		nService.createNote(noteDTO, token);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("204")));
	}
	
	@PutMapping("/updateNote/{nId}")
	private ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDTO,@RequestHeader String token,@PathVariable("nId") Long nId){
		nService.updateNote(noteDTO, token, nId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("205")));
	}
	
	@DeleteMapping("/deleteNote/{nId}")
	private ResponseEntity<Response> deleteNote(@RequestHeader String token,@PathVariable("nId") Long nId){
		nService.deleteNote(token, nId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("206")));
	}
	
	@GetMapping("/getallNotes")
	private ResponseEntity<Response> getAllNotes(@RequestHeader String token){
		List<Note> notes = nService.getAllNotes(token);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), env.getProperty("302"), notes ));
	}
}
