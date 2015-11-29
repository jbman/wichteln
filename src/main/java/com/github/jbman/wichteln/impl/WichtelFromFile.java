package com.github.jbman.wichteln.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WichtelFromFile {

	private static final String FILE_ENCODING = "UTF-8";
	private final String file;
	private FileInputStream fileInputStream;

	public WichtelFromFile(String file) {
		this.file = file;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Input file not found: " + file);
		}
	}

	public List<Wichtel> readWichtel() {

		Scanner scanner = new Scanner(fileInputStream, FILE_ENCODING);
		List<Wichtel> all = new ArrayList<Wichtel>();
		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				if (tokens.length != 3) {
					throw new IllegalStateException(
							"Wrong line in input file "
									+ file
									+ ": "
									+ line
									+ "\n"
									+ "Line must contain name, e-mail adress and excluded name separated with comma. \n"
									+ "Example: \n"
									+ "Bob Builder, bob.builder@example.com, Wendy");
				}
				final String name = tokens[0].trim();
				final String mailAddress = tokens[1].trim();
				final String blockiert = tokens[2].trim();
				Wichtel wichtel = new Wichtel(name, mailAddress,blockiert);
				System.out.println(wichtel);
				all.add(wichtel);
			}
		} finally {
			scanner.close();
		}
		return all;
	}
}
