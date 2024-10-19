package br.com.joscavalcantesn.service;

import br.com.joscavalcantesn.model.Email;
import jakarta.mail.MessagingException;

public interface EmailService {

	void sendSimpleEmail(Email mail);

	void sendHTMLEmail(Email mail) throws MessagingException;

	void sendEmailWithThymeLeaf(Email mail) throws MessagingException;

	void sendEmailWithAttachment(Email mail) throws MessagingException;
}
