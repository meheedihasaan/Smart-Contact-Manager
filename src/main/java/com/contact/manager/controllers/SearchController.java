package com.contact.manager.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contact.manager.dao.ContactRepository;
import com.contact.manager.dao.UserRepository;
import com.contact.manager.entites.Contact;
import com.contact.manager.entites.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	//Search Handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
		
		System.out.println(query);
		
		String username = principal.getName();		
		User user = userRepository.getUserByUserName(username);
		
		List<Contact> contacts = contactRepository.findByNameContainingAndUser(query, user);
		return ResponseEntity.ok(contacts);
		
	}
	
}
