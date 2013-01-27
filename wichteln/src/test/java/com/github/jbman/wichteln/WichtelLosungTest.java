package com.github.jbman.wichteln;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.jbman.wichteln.impl.Wichtel;
import com.github.jbman.wichteln.impl.WichtelLosung;

public class WichtelLosungTest {

	@Test
	public void testAuslosung() {

		List<Wichtel> teilnehmer = new ArrayList<Wichtel>();

		for (int i = 1; i <= 7; i++) {
			Wichtel einWichtel = new Wichtel("wichtel_" + i, "wichtel_" + i
					+ "@test.de");
			teilnehmer.add(einWichtel);
		}

		WichtelLosung losung = new WichtelLosung(teilnehmer);
		losung.auslosungPermutation();

		for (Wichtel wichtel : teilnehmer) {
			System.out.println(wichtel.getName() + " beschenkt "
					+ wichtel.getBeschenkterName());
		}
	}
}
