package com.github.jbman.wichteln.impl;

import java.util.Collection;

public class WichtelLottery {

	/**
	 * Number of muximum lottery runs to dina solution. If no solution can be found within this number of
	 * runs, the lottery is aborted with an exceptuion.
	 */
	private static final int MAX_RUNS = 20;

	private final Collection<Wichtel> participants;
	private final RandomInt random;

	public WichtelLottery(final Collection<Wichtel> participants) {
		this(participants, new RandomInt.Implementation());
	}

	/*package private*/ WichtelLottery(final Collection<Wichtel> participants,
			final RandomInt random) {
		this.participants = participants;
		if (participants.size() < 2) {
			throw new IllegalArgumentException(
					"Two or more Wichtel are needed, but size is "
							+ participants.size());
		}
		this.random = random;
	}

	public void execute() {
		int runs = 0;
		boolean blocked;
		do {
			blocked = false;
			run(new RaffleBox<Wichtel>(participants, random));

			// Check that no blocked combination occured
			for (final Wichtel current : participants) {
				if (current.getBlockiert().equals(current.getBeschenkterName())) {
					System.out.println("Solution not valid: " + current
							+ " gives to " + current.getBeschenkterName());
					blocked = true;
					runs++;
				}
			}
			if(runs > MAX_RUNS) {
				throw new IllegalStateException("Couldn't find a solution with " + MAX_RUNS + " runs.");		
			}
		} while(blocked);
	}

	private void run(final RaffleBox<Wichtel> raffleBox) {
		final Wichtel first = raffleBox.draw();
		Wichtel current = first;
		while (raffleBox.size() > 0) {
			final Wichtel donee = raffleBox.draw();
			current.setBeschenkter(donee);
			current = donee;

		}
		// Finally let the last Wichtel give a present to first
		current.setBeschenkter(first);
	}

}
