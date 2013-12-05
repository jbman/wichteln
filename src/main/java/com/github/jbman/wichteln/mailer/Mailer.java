package com.github.jbman.wichteln.mailer;

public interface Mailer {

	void sendMail(String sender, String recipient, String text);

}
