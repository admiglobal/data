package com.admi.data.services;

import com.admi.data.dto.ImportIssue;
import com.admi.data.dto.MotorcraftOrder;
import com.admi.data.dto.MotorcraftOrderSet;
import com.admi.data.repositories.McOrdersContentRepository;
import com.admi.data.repositories.McOrdersRepository;
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
	McOrdersRepository mcOrdersRepo;

	@Autowired
	McOrdersContentRepository mcOrdersContentRepo;

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

		String issuesMessage = "**Note: " +
				"Be sure only to re-upload those orders that contain errors and require re-submission. " +
				"If you need further assistance, contact the support center at Motorcraft@admigobal.com.";

		Context context = new Context();

		context.setVariable("message", message);
		context.setVariable("issuesMessage", issuesMessage);
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


	public void sendMotorcraftCancellationEmail(String orderNumber) throws MessagingException {
//		String devEmail = "jbetzig@admiglobal.com";
		String devEmail = "kmowers@admiglobal.com";

		MotorcraftOrderSet order = new MotorcraftOrderSet(mcOrdersRepo.findByOrderNumber(orderNumber),
														  mcOrdersContentRepo.findAllByOrderNumber(orderNumber),
														  null); //issues always null because these are associated with upload isues, not cancellation issues

		if (order.getOrder().getEmail() == null || order.getOrder().getEmail().equals("")) {
			throw new MessagingException("Unable to deliver cancellation confirmation email for Motorcraft order: the email field for order number " + orderNumber + " is empty in database table MC_ORDERS.");
		}

		String to = order.getOrder().getEmail();
		String from = "Motorcraft@admiglobal.com";
		String[] bcc = {devEmail};
		String subject = "Motorcraft Order Cancellation - " + order.getOrder().getPaCode();

		String message = "The following Motorcraft order has been canceled by an ADMI employee. If you have any questions, please contact the support center at Motorcraft@admigobal.com.";

		Context context = new Context();

		context.setVariable("message", message);
		context.setVariable("order", order);

		String html = templateEngine.process("email/motorcraftCancellation", context);

		try {
			System.out.println("Sending email to: " + to);

			emailUtility.sendMimeMessage(to, from, bcc, subject, html);
		} catch (MailSendException e) {

			emailUtility.sendMimeMessage(devEmail, from, bcc,"Order Failed to Send - " + subject, html);
			throw e;
		}
	}


	public void sendAipUploadVerificationEmail(String userEmail, String paCode) throws MessagingException {
		String errorEmail = "errors@admiglobal.com";

		String to = userEmail;
		String from = "aisSupport@admiglobal.com";
//		String cc = "";
		String[] bcc = {};
		String subject = "File finished processing for P&A code " + paCode;

		String message = "Thank you for uploading your inventory file to the ADMI Insights Platform. " +
				"You are receiving this email to notify you that the file has finished processing for P&A code " + paCode + ", and " +
				"the ADMI Insights Platform site will now reflect your updated inventory information. " +
				"If you have any questions, please contact your ADMI representative. ";

		Context context = new Context();

		context.setVariable("message", message);

		String html = templateEngine.process("email/uploadVerificationMessage", context);

		try {
			emailUtility.sendMimeMessage(to, from, /*cc,*/ bcc, subject, html);
		} catch (MailSendException e) {

			emailUtility.sendMimeMessage(errorEmail, from, bcc,"AIP Upload Verification Failed to Send - " + subject, html);
			throw e;
		}
	}

}
