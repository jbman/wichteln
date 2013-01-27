package com.github.jbman.wichteln.mailer;

public class DummyMailer implements Mailer {

	@Override
	public void sendMail(String sender, String recipient, String text) {
		System.out.println("Sending to: " + recipient);
		System.out.println(text);
	}

}
