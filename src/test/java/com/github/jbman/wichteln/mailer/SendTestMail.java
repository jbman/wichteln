package com.github.jbman.wichteln.mailer;

import com.github.jbman.wichteln.model.MailData;

public class SendTestMail {

	public static void main(String[] args) {
		if (args.length >= 3) {
			String userName = args[0];
			String sender = args[1];
			String recipient = args[2];

			Mailer mailer = new WebDeMailer(userName);
			mailer.sendMail(new MailData(sender, //
					recipient, //
					"Test Mail from WebDeMailer.class", //
					"This is just a test message."));
		} else {
			System.out
					.println("Run with arguments: <userName> <sender email> <recipient email>");
		}
	}
}
