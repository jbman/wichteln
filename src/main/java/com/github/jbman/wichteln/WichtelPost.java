package com.github.jbman.wichteln;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import com.github.jbman.wichteln.impl.Wichtel;
import com.github.jbman.wichteln.impl.WichtelFromFile;
import com.github.jbman.wichteln.impl.WichtelLottery;
import com.github.jbman.wichteln.mailer.DummyMailer;
import com.github.jbman.wichteln.mailer.Mailer;
import com.github.jbman.wichteln.mailer.WebDeMailer;
import com.github.jbman.wichteln.model.MailData;
import com.github.jbman.wichteln.text.FromTemplateMailTextProvider;
import com.github.jbman.wichteln.text.MailContent;
import com.github.jbman.wichteln.text.MailTextProvider;

/**
 * Main class to send a mail to every wichtel.
 * <p/>
 * Wichtel names and theri e-mail addresses are read from a file wichtel.txt
 * which contains <i>name, e-mail</i> in every line. The first wichtel is used
 * as sender for the mails.
 * 
 * @author Johannes Bergmann
 */
public class WichtelPost {

	private final Mailer mailer;
	private final List<Wichtel> allWichtel;
	private final MailTextProvider mailTextProvider = new FromTemplateMailTextProvider();

	public static void main(final String[] args) throws MessagingException,
	FileNotFoundException {
		final Mailer mailer;
		final String userName;
		if (args.length > 0 && args[0].equals("mail")) {
			userName = args[1];
			mailer = new WebDeMailer(userName);
		} else {
			userName = "test";
			System.out
			.println("This is a dry run. Run with arguments 'mail <username@web.de>' to really send mails with a web.de account");
			mailer = new DummyMailer();
		}
		final WichtelFromFile wff = new WichtelFromFile("wichtel.txt");
		// Username is used as email sender:  
		new WichtelPost(mailer, wff.readWichtel()).run(userName);
	}

	private WichtelPost(final Mailer mailer, final List<Wichtel> wichtelList) {
		this.mailer = mailer;
		this.allWichtel = wichtelList;
	}

	public void run(final String sender) {
		final WichtelLottery lottery = new WichtelLottery(allWichtel);
		lottery.execute();

		for (final Wichtel wichtel : allWichtel) {
			final MailContent content = mailTextProvider.getMail(wichtel);
			mailer.sendMail(new MailData(sender, //
					wichtel.getEmail(), //
					content.getSubject(), //
					content.getText()));
		}
	}
}
