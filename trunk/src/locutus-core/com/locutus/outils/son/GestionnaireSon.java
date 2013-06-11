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
package com.locutus.outils.son;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.locutus.modeles.Concept;

/**
 * 
 * @author Beugnon
 * 
 */
public class GestionnaireSon {

	/**
	 * 
	 */
	private Map<Concept, File> listeFichier;
	/**
	 * 
	 */
	private Thread soncourant = null;

	/**
	 * 
	 */
	public GestionnaireSon() {
		this.listeFichier = new HashMap<Concept, File>();
	}

	/**
	 * @param cp
	 * @param chargerSon
	 * 
	 */
	public void ajouterSonConcept(Concept cp, File chargerSon) {
		// TODO Auto-generated method stub
		this.listeFichier.put(cp, chargerSon);
	}

	/**
	 * 
	 * @param cp
	 */
	public void jouerSonConcept(Concept cp) {
		if (this.soncourant == null || !this.soncourant.isAlive()) {
			this.soncourant = new Thread(new Son(this.getFichier(cp)));
			this.soncourant.start();
		}
	}

	/**
	 * 
	 * @param fl
	 */
	public void jouerElementSonore(File fl) {

		Thread th = new Thread(new Son(fl));
		th.start();

	}

	/**
	 * 
	 * @param i
	 * @return le fichier audio
	 */
	private File getFichier(Concept i) {
		return this.listeFichier.get(i);
	}

	/**
	 * 
	 * @return true si le son en cours est terminé.
	 */
	public boolean estTermine() {
		return !this.soncourant.isAlive();
	}

}
