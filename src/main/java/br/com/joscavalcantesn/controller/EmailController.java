package br.com.joscavalcantesn.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.joscavalcantesn.model.Email;
import br.com.joscavalcantesn.service.EmailService;
import jakarta.mail.MessagingException;

@RestController
public class EmailController {

	private final EmailService emailService;

	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("/simple")
	public void sendSimpleEmail(@RequestBody Email mail) {
		emailService.sendSimpleEmail(mail);
	}

	@PostMapping("/html")
	public void sendHTMLEmail(@RequestBody Email mail) throws MessagingException {
		emailService.sendHTMLEmail(mail);
	}

	@PostMapping("/template")
	public void sendEmailWithThymeLeaf(@RequestBody Email mail) throws MessagingException {
		emailService.sendEmailWithThymeLeaf(mail);
	}

	@PostMapping("/attachment")
	public void sendEmailWithAttachment(@RequestBody Email mail) throws MessagingException {
		emailService.sendEmailWithAttachment(mail);
	}
}
