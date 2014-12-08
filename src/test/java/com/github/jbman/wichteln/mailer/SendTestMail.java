package com.github.jbman.wichteln.mailer;

import java.io.IOException;

import com.github.jbman.wichteln.model.MailData;

public class SendTestMail {

	public static void main(String[] args) throws IOException {
		if (args.length >= 3) {
			String userName = args[0];
			String sender = args[1];
			String recipient = args[2];
			String subject = getOptionalArg(args, 3,
					"Test Mail from WebDeMailer.class");
			String text = getOptionalArg(args, 4,
					"This is just a test message.");

			MailData mailData = new MailData(sender, //
					recipient, //
					subject, //
					text);

			System.out.println(mailData);

			Mailer mailer = new WebDeMailer(userName);
			mailer.sendMail(mailData);
		} else {
			System.out
					.println("Run with arguments: <userName> <sender email> <recipient email> [subject] [text]");
		}
	}

	private static String getOptionalArg(String[] args, int pos,
			String defaultValue) {
		return args.length >= pos + 1 ? args[pos] : defaultValue;
	}
}
