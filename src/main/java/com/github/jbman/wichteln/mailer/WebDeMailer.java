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

	public WebDeMailer(final String userName) {
		this.userName = userName;
		init();
	}

	public String getUserName() {
		return userName;
	}

	private void init() {

		final Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.web.de");
		props.put("mail.pop3.host", "pop3.web.de");
		// Enable SSL
		props.put("mail.pop3.ssl.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");

		//enable SMTP Authentication
		props.put("mail.smtp.auth","true");

		final Authenticator auth = new Authenticator() {
			private PasswordAuthentication passwordAuthentication;

			private PasswordAuthentication requestPasswordWithDialog() {
				final String password = JOptionPane.showInputDialog(null,
						"Password for user '" + userName + "'",
						"Please enter pop3 password",
						JOptionPane.QUESTION_MESSAGE);
				return new PasswordAuthentication(userName, password);
			}
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				if (passwordAuthentication == null) {
					passwordAuthentication = requestPasswordWithDialog();
				}
				return passwordAuthentication;
			}
		};
		session = Session.getDefaultInstance(props, auth);

		// Get the store for authentication
		Store store;
		try {
			store = session.getStore("pop3");
		} catch (final NoSuchProviderException e) {
			throw new IllegalStateException(e);
		}

		try {
			store.connect();
		} catch (final AuthenticationFailedException e) {
			System.out.println(e.getMessage());
			if (!e.getMessage().startsWith(
					"Zeitabstand zwischen zwei Logins unterschritten")) {
				throw new IllegalStateException(e);
			}
		} catch (final MessagingException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void sendMail(final MailData mailData) {
		try {
			doSendMail(mailData);
			System.out.println("Mail sent to: " + mailData.getRecipient());
		} catch (final AddressException e) {
			System.out.println("Sending Mail to " + mailData.getRecipient()
					+ " failed.");
			throw new IllegalStateException(e);
		} catch (final MessagingException e) {
			System.out.println("Sending Mail to " + mailData.getRecipient()
					+ " failed.");
			throw new IllegalStateException(e);
		}
	}

	private void doSendMail(final MailData mailData) throws MessagingException {
		final InternetAddress from = new InternetAddress(mailData.getSender());
		final InternetAddress to = new InternetAddress(mailData.getRecipient());

		final MimeMessage message = new MimeMessage(session);
		message.setFrom(from);
		message.addRecipient(Message.RecipientType.TO, to);

		message.setSubject(mailData.getSubject());
		message.setText(mailData.getText());
		Transport.send(message);
	}
}
