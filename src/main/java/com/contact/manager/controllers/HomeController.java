package com.contact.manager.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.manager.dao.UserRepository;
import com.contact.manager.entites.User;
import com.contact.manager.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("title", "Home-Smart Contact Manager");
		
		return "home";
		
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		
		model.addAttribute("title", "About-Smart Contact Manager");
		
		return "about";
		
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title", "Signup-Smart Contact Manager");
		
		model.addAttribute("user", new User());
		
		return "signup";
		
	}
	
	@GetMapping("/signin")
	public String signin(Model model) {
		
		model.addAttribute("title", "Sign in here");
		
		return "login";
		
	}
	
	//Handler for registering user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model model, HttpSession session) {
		
		try {
			
			if(!agreement) {
				System.out.println("Your have not agreed the terms and condition.");
				
				//Go to the exception block
				throw new Exception("Your have not agreed the terms and condition.");
			}
			
			if(bindingResult.hasErrors()) {
				System.out.println("Errors: "+bindingResult.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			
			//Set User Role and Enabled
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("Agreement: "+agreement);
			System.out.println("User: "+user);
			
			//Save User
			User result = userRepository.save(user);
			
			//If not checked the agreement, then the others field data will be unchanged
			model.addAttribute("user", new User());
			
			//Success Message
			session.setAttribute("message", new Message("You are successfully registered.", "alert-primary"));
			return "signup";
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("user", user);
			
			//Error Message
			session.setAttribute("message", new Message("Something went wrong! "+e.getMessage(), "alert-danger"));
			return "signup";
			
		}
		
	}
	
}
