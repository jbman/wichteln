package com.github.jbman.wichteln.text;

import java.io.IOException;
import java.io.StringWriter;

import com.github.jbman.wichteln.impl.Wichtel;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class FromTemplateMailTextProvider implements MailTextProvider {

	private static final String MAIL_TEMPLATE_FILE = "mail-template.txt";

	@Override
	public String getSubject() {
		return "Weihnachtswichteln 2013";
	}

	@Override
	public String getText(Wichtel wichtel) {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(MAIL_TEMPLATE_FILE);
		StringWriter stringWriter = new StringWriter();
		try {
			mustache.execute(stringWriter, wichtel).flush();
		} catch (IOException e) {
			throw new IllegalStateException(
					"Writing mail text from template file '"
							+ MAIL_TEMPLATE_FILE + "' failed", e);
		}
		return stringWriter.toString();
	}
}
