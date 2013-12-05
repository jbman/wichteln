package com.github.jbman.wichteln.impl;

public class Wichtel implements Comparable<Wichtel> {

	private final String name;

	private final String email;

	private Wichtel beschenkter;

	public Wichtel(String name, String email) {
		this.name = name;
		this.email = email;
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

	public void setBeschenkter(Wichtel beschenkter) {
		this.beschenkter = beschenkter;
	}

	@Override
	public String toString() {
		return "[" + name + ", " + email + "]";
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Wichtel)) {
			return false;
		}
		return this.name.equals(((Wichtel) other).name);
	}

	@Override
	public int compareTo(Wichtel other) {
		return this.name.compareTo(other.name);
	}
}
