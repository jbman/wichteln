package com.github.jbman.wichteln.text;

import com.github.jbman.wichteln.impl.Wichtel;

public class StaticMailTextProvider implements MailTextProvider {

	@Override
	public MailContent getMail(Wichtel wichtel) {
		return new MailContent(
				"Weihnachtswichteln 2013", //
				"Lieber Weihnachtswichtel "
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
						+ "Frohes Wichteln wünscht Dir,\n"
						+ "dein Wichtel-Mailer\n"
						+ "\n"
						+ "PS: Die Zuteilung wurde automatisiert und zufällig durch den Wichtel-Mailer erstellt.\n"
						+ "    Sie ist bis zur Beschenkung streng vertraulich zu behandeln!\n");
	}

}
