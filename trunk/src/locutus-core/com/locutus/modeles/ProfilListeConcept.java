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
package com.locutus.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.locutus.exceptions.modeles.ProfilListeConceptException;

/**
 * 
 * @author Beugnon
 * 
 */
public class ProfilListeConcept implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<Concept> favourites;
	/**
	 * 
	 */
	private List<Concept> lastUse;

	/**
	 * 
	 */
	public ProfilListeConcept() {
		favourites = new ArrayList<Concept>();
		lastUse = new ArrayList<Concept>();
	}

	/**
	 * 
	 * @param favoris
	 */
	public void setFavoris(List<Concept> favoris) {
		this.favourites = favoris;
	}

	/**
	 * 
	 * @param lastUse
	 */
	public void setDerniersUtilises(List<Concept> lastUse) {
		this.lastUse = new ArrayList<Concept>();
		Iterator<Concept> it = lastUse.iterator();
		while (it.hasNext())
			this.lastUse.add(it.next());

	}

	/**
	 * 
	 * @param favoris
	 * @throws ProfilListeConceptException
	 */
	public void addFavoris(Concept favoris) throws ProfilListeConceptException {
		if (!contientFavoris(favoris))
			this.getFavoris().add(favoris);
	}

	/**
	 * 
	 * @param pic
	 */
	public void addConceptDernier(Concept pic) {
		if (!contientDerniers(pic))
			this.getDerniers().add(pic);

	}

	/**
	 * 
	 * @param favoris
	 */
	public void supFavoris(Concept favoris) {
		if (contientFavoris(favoris) && this.getFavoris().size() > 1)
			this.getFavoris().remove(favoris);
		else if (contientFavoris(favoris) && this.getFavoris().size() == 1)
			this.favourites = new ArrayList<Concept>();
	}

	/**
	 * 
	 * @param pic
	 */
	public void supConceptDernier(Concept pic) {
		if (contientDerniers(pic)) {
			List<Concept> local = this.getDerniers();
			this.lastUse = new ArrayList<Concept>();
			Iterator<Concept> it = local.iterator();
			while (it.hasNext()) {
				Concept local2 = it.next();
				if (!local2.equals(pic))
					this.addConceptDernier(local2);
			}

		}
	}

	/**
	 * 
	 * @param concept
	 * @return true si et seulement si la variable "concept" appartient à la
	 *         liste des Favoris.
	 */
	public boolean contientFavoris(Concept concept) {
		Iterator<Concept> it = this.getFavoris().iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			if (local.equals(concept))
				return true;

		}

		return false;
	}

	/**
	 * 
	 * @param concept
	 * @return true si et seulement si la variable "concept" appartient à la
	 *         liste des derniers utilisées.
	 */
	public boolean contientDerniers(Concept concept) {
		Iterator<Concept> it = this.getDerniers().iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			if (local.equals(concept))
				return true;

		}

		return false;
	}

	/**
	 * 
	 * @return la liste des favoris.
	 */
	public List<Concept> getFavoris() {
		return this.favourites;
	}

	/**
	 * 
	 * @return la liste des derniers utilisées.
	 */
	public List<Concept> getDerniers() {
		return this.lastUse;
	}

	/**
	 * 
	 * @param utilisateur
	 * @return Le nom du fichier serialisée pour le profil.
	 */
	public static String getNameFile(Utilisateur utilisateur) {
		return utilisateur.getIdDossier() + "-plc.bin";
	}

}
