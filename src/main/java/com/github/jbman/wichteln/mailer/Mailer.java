package com.github.jbman.wichteln.mailer;

import com.github.jbman.wichteln.model.MailData;

public interface Mailer {

	void sendMail(MailData mailData);

}
