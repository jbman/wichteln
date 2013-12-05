package com.github.jbman.wichteln.model;

/**
 * Holds the data of a Mail which should be sent by the mailer.
 * 
 * @author Johannes Bergmann
 */
public class MailData {

	private final String sender;
	private final String recipient;
	private final String subject;
	private final String text;

	public MailData(String sender, String recipient, String subject, String text) {
		super();
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.text = text;
	}

	public String getSender() {
		return sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

}
