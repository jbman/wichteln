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
	public MailContent getMail(Wichtel wichtel) {
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
		final String rendered = stringWriter.toString();

		int firstLineBreak = rendered.indexOf("\n");
		if (firstLineBreak <= 0) {
			throw new IllegalStateException(
					"Mail template file "
							+ MAIL_TEMPLATE_FILE
							+ "must have at least 2 lines (First line is for the subject)");
		}
		final String subject = rendered.substring(0, firstLineBreak);
		final String text = rendered.substring(firstLineBreak + 1);
		return new MailContent(subject, text);
	}
}
