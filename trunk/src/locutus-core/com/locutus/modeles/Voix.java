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

import com.locutus.modeles.enums.TypeGenre;

/**
 * @author Sebastien Beugnon
 * 
 */
public final class Voix implements Serializable {
	/**
	 * la version de serialization
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * le nom de la voix (de meme que le dossier)
	 */
	private String nomdossier;
	/**
	 * le genre de la voix
	 */
	private TypeGenre tg;

	/**
	 * 
	 * @param nomdossier
	 *            le nom du dossier de la voix
	 * @param genreVoix
	 *            le genre de la voix
	 */
	public Voix(String nomdossier, TypeGenre genreVoix) {
		this.nomdossier = nomdossier;
		this.tg = genreVoix;
	}

	/**
	 * 
	 * @return le nom de la voix (du dossier)
	 */
	public String getNom() {
		return this.nomdossier;
	}

	/**
	 * 
	 * @return le genre de la voix
	 */
	public TypeGenre getTypeGenre() {
		return this.tg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * @return une chaine de caractère donnant le contenu des champs de l'objet
	 */
	@Override
	public String toString() {
		return "Voix : " + getNom() + " (genre : " + getTypeGenre() + ")";
	}

}
