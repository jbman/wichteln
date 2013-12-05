package com.github.jbman.wichteln.mailer;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import com.github.jbman.wichteln.model.MailData;

public class WebDeMailer implements Mailer {

	private final String userName;

	private Session session;

	public WebDeMailer(String userName) {
		this.userName = userName;
		init();
	}

	private void init() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.web.de");
		props.put("mail.pop3.host", "pop3.web.de");

		Authenticator auth = new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String password = JOptionPane.showInputDialog(null,
						"Password for user '" + userName + "'",
						"Please enter pop3 password",
						JOptionPane.QUESTION_MESSAGE);
				return new PasswordAuthentication(userName, password);
			}
		};
		session = Session.getDefaultInstance(props, auth);

		// Get the store for authentication
		Store store;
		try {
			store = session.getStore("pop3");
		} catch (NoSuchProviderException e) {
			throw new IllegalStateException(e);
		}

		try {
			store.connect();
		} catch (AuthenticationFailedException e) {
			System.out.println(e.getMessage());
			if (!e.getMessage().startsWith(
					"Zeitabstand zwischen zwei Logins unterschritten")) {
				throw new IllegalStateException(e);
			}
		} catch (MessagingException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void sendMail(MailData mailData) {
		try {
			doSendMail(mailData);
			System.out.println("Mail sent to: " + mailData.getRecipient());
		} catch (AddressException e) {
			System.out.println("Sending Mail to " + mailData.getRecipient()
					+ " failed.");
			throw new IllegalStateException(e);
		} catch (MessagingException e) {
			System.out.println("Sending Mail to " + mailData.getRecipient()
					+ " failed.");
			throw new IllegalStateException(e);
		}
	}

	private void doSendMail(MailData mailData) throws MessagingException {
		InternetAddress from = new InternetAddress(mailData.getSender());
		InternetAddress to = new InternetAddress(mailData.getRecipient());

		MimeMessage message = new MimeMessage(session);
		message.setFrom(from);
		message.addRecipient(Message.RecipientType.TO, to);

		message.setSubject(mailData.getSubject());
		message.setText(mailData.getText());
		Transport.send(message);
	}
}
