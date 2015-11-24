package com.github.jbman.wichteln.impl;

import java.util.*;

public class WichtelLosung {

	private final List<Wichtel> teilnehmer;

	private boolean blockiertNicht;

	public WichtelLosung(List<Wichtel> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}

	public void auslosungPermutation() {
		blockiertNicht = false;

		while(blockiertNicht == false){
			blockiertNicht = true;
			List<Wichtel> nochNichtGezogene = new LinkedList<Wichtel>(teilnehmer);
			ArrayList<Wichtel> permutation = new ArrayList<Wichtel>();

			final Random random = new Random();
			for (int i = 0; i < teilnehmer.size(); i++) {
				int position = random.nextInt(nochNichtGezogene.size());
				Wichtel gezogener = nochNichtGezogene.get(position);
				permutation.add(gezogener);
				nochNichtGezogene.remove(gezogener);
			}

			// Jeder beschenkt seinen Nachfolger in der Permutation:
			// Bsp: 2 0 1, daraus folgt 2 beschenkt 0, 0 beschenkt 1, 1 beschenkt 2
			Wichtel letzter = null;
			for (Wichtel wichtel : permutation) {
				if (letzter != null) {
					letzter.setBeschenkter(wichtel);
				}
				letzter = wichtel;
			}
			letzter.setBeschenkter(permutation.get(0));
			Iterator itr = permutation.iterator();
			while(itr.hasNext()){
				Wichtel currentWichtel = (Wichtel) itr.next();
				if (currentWichtel.getBeschenkterName().equals(currentWichtel.getBlockiert())){
					System.out.println("Invalid combination, rolling again...");
					blockiertNicht = false;
				}
			}
		}
	}

}
