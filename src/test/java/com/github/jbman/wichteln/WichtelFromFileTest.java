package com.github.jbman.wichteln;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.github.jbman.wichteln.impl.Wichtel;
import com.github.jbman.wichteln.impl.WichtelFromFile;

public class WichtelFromFileTest {

	@Test
	public void testReadWichtelFile() {
		List<Wichtel> wichtel = new WichtelFromFile(
				"src/test/resources/wichtel-test.txt").readWichtel();
		assertEquals(new Wichtel("Amy", "amy@mailhost.com","Bert"), wichtel.get(0));
		assertEquals(new Wichtel("Bert", "bert@epost.de","Cecile"), wichtel.get(1));
		assertEquals(new Wichtel("Cecile", "cecile.albert@mail.com","Amy"),
				wichtel.get(2));
		assertEquals(3, wichtel.size());
	}
}
