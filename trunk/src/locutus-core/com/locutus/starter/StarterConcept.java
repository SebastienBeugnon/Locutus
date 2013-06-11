/**
 * Copyright (C) 2013
	Sébastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
	E.E.A.P. Coste Rousse <http://www.adages.net/costerousse/>


    This file is part of Locutus.

    Locutus is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Locutus is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Locutus.  If not, see <http://www.gnu.org/licenses/>.

 *
 */
package com.locutus.starter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;

/**
 * @author Sebastien Beugnon
 * 
 */
public class StarterConcept {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static void starter() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Concept> coll = new LinkedList<Concept>();

		Concept d = new Concept("maman", "Maman");
		d.setPhotoPrivee(true);
		coll.add(d);

		d = new Concept("papa", "Papa");
		d.setPhotoPrivee(true);
		coll.add(d);

		d = new Concept("bonjour", "Bonjour");
		coll.add(d);

		d = new Concept("lune", "Lune");
		coll.add(d);

		d = new Concept("soleil", "Soleil");
		coll.add(d);

		d = new Concept("froid", "Froid");
		coll.add(d);

		d = new Concept("chaud", "Chaud");
		coll.add(d);

		d = new Concept("fauteuilroulant", "Fauteuil roulant");
		coll.add(d);

		d = new Concept("merci", "Merci");
		coll.add(d);

		d = new Concept("probleme", "Problème");
		coll.add(d);

		d = new Concept("origine", "origine (graphe)");
		coll.add(d);

		d = new Concept("retour", "retour (graphe)");
		coll.add(d);

		Collections.sort(coll);

		OptionsProgramme.initialisation();

		File fl = new File(OptionsProgramme.getOptionsCourantes()
				.getCheminCourant() + File.separator + "concepts.bin");
		fl.getParentFile().mkdirs();
		fl.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(fl));
		oos.writeObject(coll);
		oos.close();

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fl));
		coll = (List<Concept>) ois.readObject();
		ois.close();
	}

}
