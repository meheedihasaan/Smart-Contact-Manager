package com.contact.manager.services;

import javax.mail.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServices {

	public boolean sendEmail(String subject, String message, String receiver) {
		
		boolean flag = false;
		
		//Variable for yahoo
		String host="smtp.gmail.com";
		
		//Sender
		String sender = "mehedihasan111941@gmail.com";
		
		//get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);
		
		//setting important information to properties object
		
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		//Step 1: to get the session object..
		Session session=Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {				
				return new PasswordAuthentication("mehedihasan111941@gmail.com", "let me down slowly");
			}
			
			
			
		});
		
		session.setDebug(true);
		
		//Step 2 : compose the message [text,multi media]
		MimeMessage m = new MimeMessage(session);
		
		try {
		
			//from email
			m.setFrom(sender);
			
			//adding recipient to message
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			//adding subject to message
			m.setSubject(subject);
		
			
			//adding text to message
			m.setText(message);
			
			//send 
			
			//Step 3 : send the message using Transport class
			Transport.send(m);
			
			System.out.println("Sent success...................");
			
			flag = true;
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
					
		return flag;
		
	}
	
}
