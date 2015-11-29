package com.github.jbman.wichteln.impl;

import java.util.Random;

/**
 * Interface for getting a random int value. Javas {@link Random} isn't an
 * interface, so its hard o test a class which uses Random directly.
 */
public interface RandomInt {

	/**
	 * See {@link Random#nextInt()}.
	 * 
	 * @param bound Upper bound, non-inclusive.
	 * @return Value between 0 and (bound - 1). 
	 */
	int nextInt(final int bound);

	public static class Implementation implements RandomInt {

		private final Random random = new Random();

		@Override
		public int nextInt(final int bound) {
			return random.nextInt(bound);
		}
	}
}
