package com.contact.manager.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.manager.dao.ContactRepository;
import com.contact.manager.dao.UserRepository;
import com.contact.manager.entites.Contact;
import com.contact.manager.entites.User;
import com.contact.manager.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private BCryptPasswordEncoder bycrptPasswordEncoder;

	//Method to load Common Data
	@ModelAttribute
	public void loadCommonData(Model model, Principal principal) {
		
		String username = principal.getName();
		System.out.println("Username: "+username);
		
		User user = userRepository.getUserByUserName(username);
		System.out.println(user);
		
		//Pass User Details to the User Dashboard
		model.addAttribute("user", user);
		
	}
	
	@GetMapping("/index")
	public String dashboard(Model model, Principal principal) {
		
		model.addAttribute("title", "User-Dashboard");
		
		return "normal/user_dashboard";
		
	}
	
	@GetMapping("/add-contact")
	public String addContactForm(Model model) {
		
		model.addAttribute("title", "Add Contact");
		
		//Pass Contact Object To the Add Contact Form
		model.addAttribute("contact", new Contact());
		
		return "normal/add_contact";
		
	}
	
	/**
	 * @param contact
	 * @param principal
	 * @param bindingResult
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/process-contact")
	public String processAddContact(
			@Valid 
			@ModelAttribute("contact") Contact contact,
			BindingResult bindingResult, 
			@RequestParam("profileImg") MultipartFile file, 
			Principal principal, 
			Model model, 
			HttpSession session) {
		
		model.addAttribute("title", "Add Contact");
		
		try {
			if(bindingResult.hasErrors()) {
				System.out.println("Errors: "+bindingResult.toString());
				model.addAttribute("contact", contact);
				return "normal/add_contact";
			}
			
			String username = principal.getName();
			User user = userRepository.getUserByUserName(username);
			
			//Uploading Image File
			if(file.isEmpty()) {
				System.out.println("File field is empty.");
				contact.setImageUrl("contact.png");
			}
			else {
				
				File f = new ClassPathResource("static/img").getFile();
				
				Path path = Paths.get(f.getAbsolutePath()+File.separator+user.getId()+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				contact.setImageUrl(file.getOriginalFilename());
				
				System.out.println("Image is uploaded.");
				
			}
			
			contact.setUser(user);
			user.getContacts().add(contact);
			
			userRepository.save(user);
			
			System.out.println("Data: "+contact);
			
			System.out.println("Data added to the database");
			
			model.addAttribute("contact", new Contact());
			
			session.setAttribute("message", new Message("Contact successfully added.", "alert-primary"));
			
			return "normal/add_contact";
			
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("contact", contact);
			
			//Error Message
			session.setAttribute("message", new Message("Something went wrong! "+e.getMessage(), "alert-danger"));
			return "normal/add_contact";
		}
		
		
		
	}
	
	//Handler for Show Contact
	//Per Page = 5
	//Current Page = 0
	@GetMapping("/show-contact/{page}")
	public String showContact(@PathVariable("page") Integer page, Model model, Principal principal) {
		
		model.addAttribute("title", "View Contacts");
		
		String username = principal.getName();
		
		User user = userRepository.getUserByUserName(username);
		
		//For Pagination
		Pageable pageable = PageRequest.of(page, 5);
		
		Page<Contact> contacts = contactRepository.getContactByUser(user.getId(), pageable);
		
		model.addAttribute("contacts", contacts);
		
		model.addAttribute("currentPage", page);
		
		model.addAttribute("totalPages", contacts.getTotalPages());
		
		
		return "normal/show_contact";
		
	}
	
	//Handler for showing single contact
	@GetMapping("/{cid}/contact")
	public String viewContact(@PathVariable("cid") Integer cid, Model model){
		
		Optional<Contact> findByIdOptional = contactRepository.findById(cid);
		
		Contact contact = findByIdOptional.get();
		
		model.addAttribute("contact", contact);
		
		model.addAttribute("title", "Contact Profile");
		
		return "normal/contact_profile";
	}
	
	//Handler for deleting contact
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid, HttpSession session, Principal principal) {
		
		Optional<Contact> contactOptional = contactRepository.findById(cid);
		
		Contact contact = contactOptional.get();
		
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		
		user.getContacts().remove(contact);
		
		userRepository.save(user);
		
		session.setAttribute("message", new Message("Contact is successfully deleted.", "alert-primary"));
	
		return "redirect:/user/show-contact/0";
		
	}
	
	//Handler for opening Update Contact Form
	@GetMapping("/update-contact/{cid}")
	public String updateContact(@PathVariable("cid") Integer cid,  Model model){
		
		Optional<Contact> contactOptional = contactRepository.findById(cid);
		
		Contact contact = contactOptional.get();
		
		model.addAttribute("contact", contact);
		
		model.addAttribute("title", "Update Contact");
		
		return "normal/update_contact_form";
		
	}
	
	//Handler for process update
	@PostMapping("/process-update")
	public String processUpdate(
			@Valid 
			@ModelAttribute("contact") Contact contact,
			BindingResult bindingResult, 
			@RequestParam("profileImg") MultipartFile file, 
			Principal principal, 
			Model model, 
			HttpSession session) {
		
		try{
			
			if(bindingResult.hasErrors()) {
				System.out.println("Errors: "+bindingResult.toString());
				model.addAttribute("contact", contact);
				return "normal/update_contact_form";
			}
			
			//Old Contact Details
		    Optional<Contact> oldContactOptional = contactRepository.findById(contact.getCid());
			Contact oldContact = oldContactOptional.get();
			
			String username = principal.getName();
			User user = userRepository.getUserByUserName(username);
		    
			if(!file.isEmpty()) {
				
				
				//Update new Photo
				File f = new ClassPathResource("static/img").getFile();
				
				Path path = Paths.get(f.getAbsolutePath()+File.separator+user.getId()+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				contact.setImageUrl(file.getOriginalFilename());
				
				System.out.println("Image is uploaded.");
				
			}
			else {
				contact.setImageUrl(oldContact.getImageUrl());
			}
			

			System.out.println(contact.getName());
			System.out.println(contact.getCid());
			
			contact.setUser(user);
			contactRepository.save(contact);
			
			//session.setAttribute("message", new Message("Your contact is updated.", "alert-success"));
			
			return "redirect:/user/"+contact.getCid()+"/contact";
			
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("contact", contact);
			
			session.setAttribute("message", new Message("Something went wrong! "+e.getMessage(), "alert-danger"));
			return "normal/update_contact_form";
		}
		
		
		
	}
	
	//Handler For User Profile
	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal) {
		
		String username = principal.getName();
		
		User user = userRepository.getUserByUserName(username);
		
		model.addAttribute("user", user);
		
		model.addAttribute("title", "User Profile");
		
		return "normal/user_profile";
		
	}
	
	//Open Setting Page Handler
	@GetMapping("/settings")
	public String openSetting(Model model) {
		
		model.addAttribute("title", "Settings");
		return "normal/settings";
	}
	
	
	//Handler For Changing Password
	@PostMapping("/change-password")
	public String changePassword(
			@RequestParam("oldPassword") String oldPassword, 
			@RequestParam("newPassword") String newPassword,
			Principal principal,
			HttpSession session
			) {
		
		//Print on the console
		System.out.println("Old Password: "+oldPassword);		
		System.out.println("New Password: "+newPassword);
		
		//New Password Field Validation
		if(newPassword.length() < 4 || newPassword.length() > 64) {
			session.setAttribute("message", new Message("Error! New password must be between 4 to 64 characters.", "alert-danger"));
			return "redirect:/user/settings";
		}
		
		//Get Current User
		String username = principal.getName();		
		User currentUser = userRepository.getUserByUserName(username);
		
		//If Old Password Matches
		if(this.bycrptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			currentUser.setPassword(bycrptPasswordEncoder.encode(newPassword));
			userRepository.save(currentUser);
			
			//Show Message
			session.setAttribute("message", new Message("Your Password is successfully changed.", "alert-primary"));
			return "redirect:/user/index";
		}
		else {
			//Error Message
			session.setAttribute("message", new Message("Your old passsowrd is wrong. Please try again lager.", "alert-danger"));
			return "redirect:/user/settings";
		}
		
	}
	

}
