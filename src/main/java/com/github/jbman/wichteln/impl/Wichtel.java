package com.github.jbman.wichteln.impl;

public class Wichtel implements Comparable<Wichtel> {

	private final String name;

	private final String email;

	private Wichtel beschenkter;

	private final String blockiert;

	public Wichtel(final String name, final String email, final String blockiert) {
		this.name = name;
		this.email = email;
		this.blockiert = blockiert;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getBeschenkterName() {
		return beschenkter.name;
	}

	public Wichtel getBeschenkter() {
		return beschenkter;
	}

	public void setBeschenkter(final Wichtel beschenkter) {
		this.beschenkter = beschenkter;
	}

	public String getBlockiert() {
		return blockiert;
	}

	@Override
	public String toString() {
		return "[" + name + ", " + email + "]";
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Wichtel)) {
			return false;
		}
		return this.name.equals(((Wichtel) other).name);
	}

	@Override
	public int compareTo(final Wichtel other) {
		return this.name.compareTo(other.name);
	}
}
