package com.contact.manager.controllers;

import java.util.Random;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.manager.helper.Message;
import com.contact.manager.services.EmailServices;

@Controller
public class ForgotController {
	
	@Autowired
	private EmailServices emailServices;

	//Handler for opening forgot password page
	@GetMapping("/forgot_password")
	public String openForgotPassword(Model model) {
		
		model.addAttribute("title", "Forgot Password");
		return "forgot_password";
		
	}
	
	//Handler for verifying OTP
	@PostMapping("/send-otp")
	public String openForgotPassword(@RequestParam("email") String email, Model model, HttpSession session) {
		
		System.out.println("email: "+email);
		
		//Generating OTP of 4 Digit
		Random random = new Random(1000 );
		int otp = random.nextInt(9999);
		System.out.println("otp: "+otp);
		
		//Code for sending OTP to Email
		String subject = "OTP From Smart Contact Manager";
		String message = "<h2> OTP = " +otp+ "</h2>";
		String receiver = email;
		boolean flag = emailServices.sendEmail(subject, message, receiver);
		
		if(flag) {
			model.addAttribute("title", "Verify Email with OTP");
			session.setAttribute("message", new Message("We have sent a 4 digit to your email.", "alert-primary"));
			return "verify_otp";
		}
		else {
			model.addAttribute("title", "Forgot Password");
			session.setAttribute("message", new Message("Something went wrong!", "alert-danger"));
			return "forgot_password";
		}
		
	
		
		
	}
	
}
