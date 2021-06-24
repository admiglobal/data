package com.admi.data.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtility {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendSimpleEmail(String[] to, String from, String[] bcc, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(from);
		message.setBcc(bcc);
		message.setSubject(subject);
		message.setText(text);

		javaMailSender.send(message);
	}

	public void sendMimeMessage(String to, String from, String[] bcc, String subject, String text) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(from);
		helper.setBcc(bcc);
		helper.setText(text, true);

		javaMailSender.send(message);
	}

	public void sendMimeMessage(String to, String from, String cc, String[] bcc, String subject, String text) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(from);
//		helper.setReplyTo(from);
		helper.setCc(cc);
		helper.setBcc(bcc);
		helper.setText(text, true);

		javaMailSender.send(message);
	}

	public void sendMimeMessage(String to, String from, String[] cc, String[] bcc, String subject, String text) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(from);
//		helper.setReplyTo(from);
		helper.setCc(cc);
		helper.setBcc(bcc);
		helper.setText(text, true);

		javaMailSender.send(message);
	}

}
