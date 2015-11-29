package com.github.jbman.wichteln.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.jbman.wichteln.impl.RandomInt;
import com.github.jbman.wichteln.impl.Wichtel;
import com.github.jbman.wichteln.impl.WichtelLottery;

public class WichtelLotteryTest {

	private List<Wichtel> sevenWichtel;

	@Before
	public void setup() {
		sevenWichtel = new ArrayList<Wichtel>();
		for (int i = 1; i <= 7; i++) {
			final Wichtel wichtel = new Wichtel("wichtel_" + i, "wichtel_" + i
					+ "@test.de", "wichtel_" + (i - 1));
			sevenWichtel.add(wichtel);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProvidingOneWichtel() {
		final Set<Wichtel> singleWichtel = Collections.singleton(sevenWichtel
				.get(0));
		final WichtelLottery lottery = new WichtelLottery(singleWichtel);
		lottery.execute();
	}

	@Test
	public void testAlwaysDrawFirstElement() {

		// Given a lottery which draws always the first element (with
		// AlwaysZeroProvider)
		final List<Wichtel> participants = sevenWichtel;
		final WichtelLottery lottery = new WichtelLottery(participants,
				new AlwaysZeroProvider());
		// When lottery is executed
		lottery.execute();

		// Then each Wichtel gives a present to the next one in the list
		for (int i = 0; i < participants.size(); i++) {
			final Wichtel wichtel = participants.get(i);
			final Wichtel expectedDonee = participants.get((i + 1)
					% participants.size());
			Assert.assertEquals(expectedDonee, wichtel.getBeschenkter());
		}
	}

	@Test
	public void testRandomLottery() {

		// Given a lottery which draws randomly
		final List<Wichtel> participants = sevenWichtel;
		final WichtelLottery lottery = new WichtelLottery(participants);
		// When lottery is executed
		lottery.execute();

		// Then every Wichtel should occur as donee.
		final Collection<Wichtel> donees = new HashSet<Wichtel>();
		for (final Wichtel wichtel : participants) {
			donees.add(wichtel.getBeschenkter());
			System.out.println(wichtel.getName() + " gives to donee "
					+ wichtel.getBeschenkterName());
		}
		Assert.assertEquals(
				"The set of donees should be equal to the set of participants",
				new HashSet<Wichtel>(participants), donees);
	}

	@Test
	public void testBlockingWithSingleSolution() {
		// Given a blocking spec, that can't be solved
		final Wichtel a = new Wichtel("a", "a@test.de", "c");
		final Wichtel b = new Wichtel("b", "a@test.de", "a");
		final Wichtel c = new Wichtel("c", "a@test.de", "b");

		final List<Wichtel> participants = new ArrayList<Wichtel>();
		participants.add(a);
		participants.add(b);
		participants.add(c);

		final WichtelLottery lottery = new WichtelLottery(participants);

		// When lottery is executed
		lottery.execute();

		// Then this soltion is the only one:
		Assert.assertEquals(b, a.getBeschenkter());
		Assert.assertEquals(c, b.getBeschenkter());
		Assert.assertEquals(a, c.getBeschenkter());
	}

	@Test(expected = IllegalStateException.class)
	public void testBlockingWhenNoSolutionExists() {
		// Given a blocking spec, that can't be solved
		final List<Wichtel> participants = new ArrayList<Wichtel>();
		participants.add(new Wichtel("a", "a@test.de", "b"));
		participants.add(new Wichtel("b", "b@test.de", "a"));

		final WichtelLottery lottery = new WichtelLottery(participants);

		// When lottery is executed
		lottery.execute();

		// Then lottery should stop with exception and not run endlessly
	}

	private static class AlwaysZeroProvider implements RandomInt {
		@Override
		public int nextInt(final int bound) {
			return 0;
		}
	}
}
