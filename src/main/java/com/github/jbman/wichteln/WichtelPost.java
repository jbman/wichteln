package com.github.jbman.wichteln;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import com.github.jbman.wichteln.impl.Wichtel;
import com.github.jbman.wichteln.impl.WichtelFromFile;
import com.github.jbman.wichteln.impl.WichtelLosung;
import com.github.jbman.wichteln.mailer.DummyMailer;
import com.github.jbman.wichteln.mailer.Mailer;

/**
 * Main class to send a mail to every wichtel.
 * 
 * @author Johannes Bergmann
 */
public class WichtelPost {

	private final Mailer mailer;
	private final List<Wichtel> allWichtel;

	public static void main(String[] args) throws MessagingException,
			FileNotFoundException {
		// final Mailer mailer = new WebDeMailer();
		final Mailer mailer = new DummyMailer();
		WichtelFromFile wff = new WichtelFromFile("wichtel.txt");
		new WichtelPost(mailer, wff.readWichtel()).run();
	}

	private WichtelPost(final Mailer mailer, final List<Wichtel> teilnehmer) {
		this.mailer = mailer;
		this.allWichtel = teilnehmer;
	}

	private String createText(Wichtel wichtel) {
		return "Lieber Weihnachtswichtel "
				+ wichtel.getName()
				+ ",\n"
				+ "\n"
				+ "damit Du dieses Jahr zu Weihnachten nicht zu viel zu schleppen hast, wurde Dir\n"
				+ "ein besonders braver Mensch zugeteilt, den es zu beschenken gilt.\n"
				+ "\n"
				+ "Bitte suche etwas Nettes für: "
				+ wichtel.getBeschenkterName()
				+ "\n"
				+ "\n"
				+ "Es muss kein Haus, Auto oder Diammantring sein.\n"
				+ wichtel.getBeschenkterName()
				+ " soll sich über eine Kleinigkeit im Wert von 10 - 15 EUR freuen können.\n"
				+ "\n"
				+ "Frohes Wichteln wünschen Dir,\n"
				+ "Wichtel Petra und Wichtel Johannes\n"
				+ "\n"
				+ "PS: Die Zuteilung wurde automatisiert und zufällig durch den Wichtel-Mailer erstellt.\n"
				+ "    Sie ist bis zur Beschenkung streng vertraulich zu behandeln!\n";
	}

	public void run() {
		WichtelLosung losung = new WichtelLosung(allWichtel);
		losung.auslosungPermutation();

		for (Wichtel wichtel : allWichtel) {
			mailer.sendMail("johbergmann@web.de", wichtel.getEmail(),
					createText(wichtel));
		}
	}
}
