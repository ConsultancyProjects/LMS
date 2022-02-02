package com.lms.tutor.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.lms.tutor.model.User;

@Service
public class AmazonSesClient {

	@Value("${change_pwd_url}")
	private String changePwdURL;
	
	@Value("${admin_email}")
	private String adminEmail;
	
	@Value("${contact_us_email}")
	private String contactUsEmail;

	@Bean
	public AmazonSimpleEmailService getAmazonSimpleEmailService() {
		return AmazonSimpleEmailServiceClientBuilder.standard().withRegion("us-east-2").build();

	}

 
	public void sendEmailForTempPassword(String tempPwd, String userName, 
			String name, String receiverEmail) throws Exception {
	
	  String emailContent =
	      "<!DOCTYPE html>\n"
	          + "<html>\n"
	          + "<head>\n"
	          + "    <meta charset=\"utf-8\">\n"
	          + "    <title>Temp Password Email</title>\n"
	          + "</head>\n"
	          + "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n"
	          + "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Hello "+ name+",</h5>\n"
	          + "<br></br>"
	          + "<p style=\"font-size: 16px; font-weight: 500\">Please login to application using <a target=\"_blank\" href="+ changePwdURL +">Tutor App</a> with</p>\n"
	          + "<br></br>"
	          + "<p><b>UserName: </b>"+userName +"</p>"
	          + "<br></br>"
	          + "<p><b>Current Password: </b>"+tempPwd +"</p>"
	          + "</body>\n"
	          + "</html>";

	  String senderEmail = adminEmail;
	  String emailSubject = "Reset Password";

	  try {
	    SendEmailRequest sendEmailRequest =
	        new SendEmailRequest()
	            .withDestination(new Destination().withToAddresses(receiverEmail))
	            .withMessage(
	                new Message()
	                    .withBody(
	                        new Body()
	                            .withHtml(new Content().withCharset("UTF-8").withData(emailContent)))
	                    .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
	            .withSource(senderEmail);
	    SendEmailResult result = getAmazonSimpleEmailService().sendEmail(sendEmailRequest);
	    System.out.println(result.getMessageId());
	  } catch (Exception e) {
	   throw new Exception(e);
	  }
	}
	
	public void sendContactUsMail(String userName, String body, String senderEmail) throws Exception {
	
	  String emailContent =
	      "<!DOCTYPE html>\n"
	          + "<html>\n"
	          + "<head>\n"
	          + "    <meta charset=\"utf-8\">\n"
	          + "    <title>Contact US Email</title>\n"
	          + "</head>\n"
	          + "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n"
	          + "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Question From "+ userName +",</h5>\n"
	          + "<br></br>"
	          + "<p style=\"font-size: 16px; font-weight: 500\">"+ body +"</p>\n"
	          + "<br></br>"
	          + "</body>\n"
	          + "</html>";

	  String emailSubject = "Question from user: " + userName;

	  try {
	    SendEmailRequest sendEmailRequest =
	        new SendEmailRequest()
	            .withDestination(new Destination().withToAddresses(contactUsEmail))
	            .withMessage(
	                new Message()
	                    .withBody(
	                        new Body()
	                            .withHtml(new Content().withCharset("UTF-8").withData(emailContent)))
	                    .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
	            .withSource(senderEmail);
	    SendEmailResult result = getAmazonSimpleEmailService().sendEmail(sendEmailRequest);
	    System.out.println(result.getMessageId());
	  } catch (Exception e) {
	   throw new Exception(e);
	  }
	}


	public void sendPasswordExpiryMail(User user) throws Exception {
		String emailContent =
			      "<!DOCTYPE html>\n"
			          + "<html>\n"
			          + "<head>\n"
			          + "    <meta charset=\"utf-8\">\n"
			          + "    <title>Contact US Email</title>\n"
			          + "</head>\n"
			          + "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n"
			          + "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Question From "+ "Password Reset using link" +",</h5>\n"
			          + "<br></br>"
			          + "<p style=\"font-size: 16px; font-weight: 500\">Please login to application using <a target=\"_blank\" href="+ changePwdURL +">Tutor App</a> with</p>\n"
			          + "<br></br>"
			          + "<p style=\"font-size: 16px; font-weight: 500\">"+ "For Security reasons change your password as its already 30 days!" +"</p>\n"
			          + "<br></br>"
			          + "</body>\n"
			          + "</html>";

			  String emailSubject = "Change your password: ";

			  try {
			    SendEmailRequest sendEmailRequest =
			        new SendEmailRequest()
			            .withDestination(new Destination().withToAddresses(user.getEmail()))
			            .withMessage(
			                new Message()
			                    .withBody(
			                        new Body()
			                            .withHtml(new Content().withCharset("UTF-8").withData(emailContent)))
			                    .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
			            .withSource(adminEmail);
			    SendEmailResult result = getAmazonSimpleEmailService().sendEmail(sendEmailRequest);
			    System.out.println(result.getMessageId());
			  } catch (Exception e) {
			   throw new Exception(e);
			  }
		
	}
}
