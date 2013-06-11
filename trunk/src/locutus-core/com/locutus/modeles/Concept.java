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
 * 
 * @author Beugnon
 * 
 */
public class Concept implements Serializable, Comparable<Concept> {
	/**
	 * la version de serialization
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * le nom-fichier du concept
	 */
	private String nomFichier;
	/**
	 * le genre du concept initialement pour tous
	 */
	private TypeGenre limit = TypeGenre.TOUS;
	/**
	 * booleen si la photo est privee pour l'enfant
	 */
	private boolean photoprivee = false;
	/**
	 * Le nom du concept
	 */
	private String nomPicto;

	/**
	 * Constructeur avec nom et nom des fichiers
	 * 
	 * @param nomPicto
	 *            le nom du concept
	 * @param nomFichier
	 *            le nom des fichiers
	 */
	public Concept(String nomFichier, String nomPicto) {
		this.nomPicto = nomPicto;
		this.nomFichier = nomFichier.trim();
	}

	/**
	 * Constructeur par recopie
	 * 
	 * @param origine
	 */
	public Concept(Concept origine) {
		this.nomPicto = origine.getNomConcept();
		this.nomFichier = new String(origine.getNomFichier());
		this.limit = origine.getGenre();
		this.photoprivee = origine.getPhotoPrivee();
	}

	/**
	 * 
	 * @return le nom du concept
	 */
	public String getNomConcept() {
		return this.nomPicto;
	}

	/**
	 * 
	 * @return le nom du concept pour les fichiers
	 */
	public String getNomFichier() {
		return this.nomFichier;
	}

	/**
	 * 
	 * @return le genre auquel est lie le concept
	 */
	public TypeGenre getGenre() {
		// TODO Auto-generated method stub
		return this.limit;
	}

	/**
	 * 
	 * @return vrai si et seulement si la photographie liee au concept est
	 *         privee
	 */
	public boolean getPhotoPrivee() {
		return this.photoprivee;
	}

	/**
	 * 
	 * @param pp
	 *            le fait que la photo soit privee
	 */
	public void setPhotoPrivee(boolean pp) {
		this.photoprivee = pp;
	}

	/**
	 * 
	 * @param genre
	 *            le genre du concept
	 */
	public void setGenre(TypeGenre genre) {
		if (genre == TypeGenre.TOUS || genre == TypeGenre.HOMME
				|| genre == TypeGenre.FEMME)
			this.limit = genre;

	}

	@Override
	public String toString() {
		return "nomConcept= " + this.getNomConcept() + " - nomFichier= "
				+ this.getNomFichier();
	}

	/**
	 * 
	 * @param arg
	 *            le second concept
	 * @return vrai si et seulement si les deux concepts sont identiques
	 */
	public boolean equals(Concept arg) {
		return this.getNomFichier().equals(arg.getNomFichier())
				&& this.getNomConcept().equals(arg.getNomConcept());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;

		if (o instanceof Concept)
			return equals((Concept) o);
		else
			return false;
	}

	@Override
	public int compareTo(Concept arg0) {
		return this.getNomConcept().compareTo(((Concept) arg0).getNomConcept());
	}

}
