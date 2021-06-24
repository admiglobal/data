package com.admi.data.processes;

import com.admi.data.dto.ImportIssue;
import com.admi.data.utilities.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.util.List;

@Service
public class EmailService {

	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	TemplateEngine templateEngine = new TemplateEngine();

	public void sendMotorcraftOrderEmail(String userEmail, List<ImportIssue> issues, String paCode) throws MessagingException {

		if (userEmail == null) {
			userEmail = "kmowers@admiglobal.com";
		}

		String recipientEmail = userEmail;

		String from = "Motorcraft@admiglobal.com";
		String cc = "Motorcraft@admiglobal.com";
//		String cc = "kmowers@admiglobal.com";
		String[] bcc = {"kmowers@admiglobal.com"};
		String subject = "Motorcraft Order Status - " + paCode;

		String message = "Your order has been received and will be uploaded to DOW on your desired order upload date. " +
				"You can check it's status on the Motorcraft Order Site under the 'Orders' tab. " +
				"If there was any issues with any of your order forms, they will be listed below.";

		Context context = new Context();

		context.setVariable("message", message);
		context.setVariable("issues", issues);

		String html = templateEngine.process("email/importMessage", context);

		try {
			emailUtility.sendMimeMessage(recipientEmail, from, cc, bcc, subject, html);
		} catch (MailSendException e) {

			emailUtility.sendMimeMessage("kmowers@admiglobal.com", from, bcc,"Order Failed to Send - " + subject, html);
			throw e;
		}
	}

}
