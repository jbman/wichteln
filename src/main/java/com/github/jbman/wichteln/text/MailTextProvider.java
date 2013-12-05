package com.github.jbman.wichteln.text;

import com.github.jbman.wichteln.impl.Wichtel;

/**
 * Provides subject and text for the mail.
 * 
 * @author Johannes Bergmann
 */
public interface MailTextProvider {

	public String getSubject();

	public String getText(Wichtel wichtel);
}
