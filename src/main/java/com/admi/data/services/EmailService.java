package com.admi.data.services;

import com.admi.data.dto.ImportIssue;
import com.admi.data.dto.MotorcraftOrderSet;
import com.admi.data.utilities.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	TemplateEngine templateEngine = new TemplateEngine();

	public void sendMotorcraftOrderEmail(String userEmail, List<MotorcraftOrderSet> orders, String paCode) throws MessagingException {
		boolean test = false;
		String devEmail = (test)? "jbetzig@admiglobal.com":"kmowers@admiglobal.com";

		List<ImportIssue> issues = new ArrayList<>();
		orders.forEach((MotorcraftOrderSet order) -> issues.addAll(order.getIssues()));

		if (userEmail == null) {
			userEmail = devEmail;
		}

		String recipientEmail = userEmail;

		String from = "Motorcraft@admiglobal.com";
		String cc = (test)? devEmail: "Motorcraft@admiglobal.com";
		String[] bcc = {devEmail};
		String subject = "Motorcraft Order Status - " + paCode;

		String message = "Your order has been received and will be uploaded to DOW within 72 hours of your desired order upload date. " +
				"You can check its status on the Motorcraft Order Site under the 'Orders' tab. " +
				"Please note, orders will not be uploaded the same day they are submitted. " +
				"If there were any issues with any of your order forms, they will be listed below.";

		Context context = new Context();

		context.setVariable("message", message);
		context.setVariable("issues", issues);
		context.setVariable("orders", orders);

		String html = templateEngine.process("email/importMessage", context);

		try {
			emailUtility.sendMimeMessage(recipientEmail, from, cc, bcc, subject, html);
		} catch (MailSendException e) {

			emailUtility.sendMimeMessage(devEmail, from, bcc,"Order Failed to Send - " + subject, html);
			throw e;
		}
	}

}
