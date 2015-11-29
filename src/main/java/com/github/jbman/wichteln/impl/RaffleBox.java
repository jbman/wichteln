package com.github.jbman.wichteln.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A raffle box holds a set of elements and allows to draw (=remove) a random element.
 */
public class RaffleBox<E> {

	private final List<E> content;
	private final RandomInt random;

	/**
	 * Constructor which adds the content to the raffle box.
	 * 
	 * @param content
	 *            Elements to be added.
	 */
	public RaffleBox(final Collection<E> content) {
		this(content, new RandomInt.Implementation());
	}

	/**
	 * Constructor with custom RandomInt implementation for testing.
	 */
	public RaffleBox(final Collection<E> content, final RandomInt r) {
		this.content = new LinkedList<E>(content);
		this.random = r;
	}

	/**
	 * Draw an element from the raffle box.
	 * 
	 * @return The element which was drawn form the box.
	 */
	public E draw() {
		final int pos = random.nextInt(size());
		return content.remove(pos);
	}

	/**
	 * Returns the number of elements in the box.
	 * 
	 * @return number of elements in the box.
	 */
	public int size() {
		return content.size();
	}
}
