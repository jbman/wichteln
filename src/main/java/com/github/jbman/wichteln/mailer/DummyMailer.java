package com.github.jbman.wichteln.mailer;

import com.github.jbman.wichteln.model.MailData;

public class DummyMailer implements Mailer {

	@Override
	public void sendMail(MailData mailData) {
		System.out.println("Sending to: " + mailData.getRecipient());
		System.out.println("Subject: " + mailData.getSubject());
		System.out.println(mailData.getText());

	}
}
