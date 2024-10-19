package br.com.joscavalcantesn.service.impl;

import java.util.Objects;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.joscavalcantesn.model.Email;
import br.com.joscavalcantesn.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	@Override
	@Async
	public void sendSimpleEmail(Email email) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getBody());

		mailSender.send(message);
	}

	@Override
	@Async
	public void sendHTMLEmail(Email email) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress("joscavalcantesn.test@gmail.com"));

		for (String recipient : email.getTo()) {
			message.addRecipients(MimeMessage.RecipientType.TO, recipient);
		}

		message.setSubject(email.getSubject());
		message.setContent(email.getBody(), "text/html; charset=utf-8");

		mailSender.send(message);
	}

	@Override
	@Async
	public void sendEmailWithThymeLeaf(Email email) throws MessagingException {
		for (String recipient : email.getTo()) {
			Context context = new Context();
			context.setVariable("username", recipient);

			String process = templateEngine.process("thymeleaf-mail", context);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setSubject(email.getSubject());
			helper.setFrom("joscavalcantesn.test@gmail.com");
			helper.setText(process, true);
			helper.setTo(recipient);

			mailSender.send(message);
		}
	}

	@Override
	@Async
	public void sendEmailWithAttachment(Email email) throws MessagingException {
		for (String recipient : email.getTo()) {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("joscavalcantesn.test@gmail.com");
			helper.setTo(recipient);
			helper.setSubject("Testando API de e-mail com anexo");
			helper.setText("Veja o documento em anexo abaixo:");

			ClassPathResource classPathResource = new ClassPathResource("static/iluvboobies.jpg");
			helper.addAttachment(Objects.requireNonNull(classPathResource.getFilename()), classPathResource);

			mailSender.send(message);
		}
	}
}
