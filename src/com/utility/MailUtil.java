package com.utility;

import java.util.Properties;
import java.util.Random;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.base64EndoderDecoderUtility.Base64;
import com.propertyReader.PropertyReader;


/**
 * The Class MailUtil.
 */
public class MailUtil {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Send mail.
	 *
	 * @param to the to
	 * @param subject the subject
	 * @param content the content
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public boolean sendMail(String to,String subject,String content){
		logger.info("sendMail action invoked.");
		boolean sendMailFlag=false;
		try{
			Message message=mailProperties();
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart("alternative");
			BodyPart htmlMessageBodyPart = new MimeBodyPart();
			htmlMessageBodyPart.setContent(content,"text/html");
			multipart.addBodyPart(htmlMessageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			logger.debug("Email Message Sent..");
			sendMailFlag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sendMailFlag;
	}

	/**
	 * Mail properties.
	 *
	 * @return the message
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public Message mailProperties() throws AddressException, MessagingException{
		logger.info(" 'mailProperties' action invoked.. ");		
		Properties props = new Properties();
		String host=PropertyReader.getProperty("mailHostName");
		String port=PropertyReader.getProperty("mailPort");
		String fromAddress=PropertyReader.getProperty("fromAddress");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port",port  );
		props.put("mail.smtp.socketFactory.port",PropertyReader.getProperty("mailPort"));
		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{ 
				return new PasswordAuthentication(Base64.decode(PropertyReader.getProperty("mailSendAuthParam1"))
						,
						Base64.decode(PropertyReader.getProperty("mailSendAuthParam2"))
				);
			}
		});		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress( fromAddress));
		return message;
	}


	/**
	 * Mail authenticate.
	 *
	 * @return the message
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	public Message mailAuthenticate()throws AddressException, MessagingException{
		String fromAddress=PropertyReader.getProperty("fromAddress");
		Properties props = new Properties();
		props.put("mail.smtp.host", PropertyReader.getProperty("mailHostNameGmail"));
		props.put("mail.smtp.socketFactory.port",PropertyReader.getProperty("mailPortGmail"));
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", PropertyReader.getProperty("mailPortGmail"));
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Base64.decode(PropertyReader.getProperty("mailSendAuthParam1"))
						,
						Base64.decode(PropertyReader.getProperty("mailSendAuthParam2"))
				);
			}
		});
		Message message=null;
		message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress)); 
		return message;
	}


	/**
	 * Send mail via gmail.
	 *
	 * @param to the to
	 * @param subject the subject
	 * @param content the content
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	public boolean sendMailViaGmail(String to,String subject,String content){
		logger.info("sendMailViaGmail invoked..");
		boolean sendMailFlag=false;
		try{
			Message message=mailAuthenticate();
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart("alternative");
			BodyPart htmlMessageBodyPart = new MimeBodyPart();
			htmlMessageBodyPart.setContent(content,"text/html");
			multipart.addBodyPart(htmlMessageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			logger.info("Email Message Sent..");
			sendMailFlag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sendMailFlag;
	}

	/**
	 * Gets the random string.
	 *
	 * @param length the length
	 * @return the random string
	 */
	public static String getRandomString(int length) {
		String charset = "!@&#123456789abcdefghjkmnpqrstuvwxyz";
		Random random = new Random(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int pos = random.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}

		return sb.toString().toLowerCase();
	}

}
