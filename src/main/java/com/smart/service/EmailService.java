package com.smart.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Session;

@Service
public class EmailService {
	
//	@Value("${mail.username}")
//	private String username;
//	
//	@Value("${mail.password}")
//	private String password;
	
	public boolean sendEmail(String subject, String message, String to) {
		//rest of the code
		
		boolean flag=false;
		
		String from="helicop49@gmail.com";
		
		//variable for gmail
		String host = "smtp.gmail.com";
		
		//get the system properties
		Properties properties = new Properties();
		System.out.println("PROPERTIES "+properties);
		
		//setting important information to properties object
		
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", 465);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		//Step 1: to get the session object..
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
				return new PasswordAuthentication("helicop49@gmail.com", "cxsnjrxylhwcjtua");
			}
		});
		
		session.setDebug(true);
		
		//Step 2: compose the message [text, multi media]
		MimeMessage m = new MimeMessage(session);
		try {
			//from email
			m.setFrom(from);
			
			//adding recipient to message
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//adding subject to message
			m.setSubject(subject);
			
			//adding text to subject
//			m.setText(message);
			m.setContent(message, "text/html");
			
			//send
			
			//Step 3: send the message using Transport class
			Transport.send(m);
			
			System.out.println("Sent success..............");
			flag =true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
