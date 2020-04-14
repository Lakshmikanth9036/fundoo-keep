package com.bridgelabz.fundookeep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundookeep.dto.Response;
import com.bridgelabz.fundookeep.service.CollaboratorService;

@RestController
@RequestMapping("/collaborator")
@CrossOrigin
@PropertySource("classpath:message.properties")
public class CollaboratorController {

	@Autowired
	private CollaboratorService cService;
	
	@PostMapping("/add")
	private ResponseEntity<Response> addCollaborator(@RequestHeader(name = "header") String token,@RequestParam String emailAddress,@RequestParam Long nId){
		cService.addCollaborator(token, emailAddress, nId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Successfully added.."));
	}
	
	@GetMapping("/getCollNotes")
	private ResponseEntity<Response> getCollaboratorNote(@RequestHeader(name = "header") String token){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Collaborated Notes..", cService.getCollaboratedNote(token)));
	}
	
	@DeleteMapping("/deleteCollaborator")
	private ResponseEntity<Response> deleteCollaborator(@RequestParam String mail, @RequestParam Long nId,@RequestParam Long cId ){
		cService.removeCollaborator(mail, cId, nId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Remove Collaborator!!!"));
		
	}
}
