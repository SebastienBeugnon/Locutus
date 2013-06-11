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
 * @author Sebastien Beugnon
 * 
 */
public class Utilisateur implements Serializable, Comparable<Utilisateur> {
	/**
	 * la version de serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'id du prochain utilisateur doit etre mis a jour par l'utilisation de
	 * constructeurs ou bien a l'aide de la methode statique majID
	 */
	private static int LastId = 0;
	/**
	 * l'id de l'utilisateur
	 */
	private int id;
	/**
	 * le nom de l'utilisateur
	 */
	private String nom;
	/**
	 * le prenom de l'utilisateur
	 */
	private String prenom;
	/**
	 * l'objet Voix correspond a l'utilisateur
	 */
	private Voix voix;

	/**
	 * Les options de l'utilisateur
	 */
	private OptionsUtilisateur opUt;

	/**
	 * @param nom
	 *            nom de l'utilisateur
	 * @param prenom
	 *            prenom de l'utilisateur
	 * @param voix
	 *            voix utilisee par l'utilisateur
	 * @param op
	 *            l'ensemble des options specifiques a l'utilisateur
	 * 
	 **/
	public Utilisateur(String nom, String prenom, Voix voix,
			OptionsUtilisateur op) {
		this.id = LastId++;
		this.nom = nom;
		this.prenom = prenom;
		this.voix = voix;
		this.opUt = op;
	}

	/**
	 * 
	 * @param id2
	 * @param nom2
	 * @param prenom2
	 * @param voix2
	 * @param ou
	 */
	public Utilisateur(int id2, String nom2, String prenom2, Voix voix2,
			OptionsUtilisateur ou) {
		// TODO Auto-generated constructor stub
		this.id = id2;
		this.nom = nom2;
		this.prenom = prenom2;
		this.voix = voix2;
		this.opUt = ou;
		setLastId(id2);
	}

	// ACCESSEURS

	/**
	 * @param li
	 *            dernier id deja utilise dans le programme <br>
	 *            Utilisation exceptionnelle lors de la récupération de la liste
	 *            des enfants en XML
	 * */
	public static void setLastId(int li) {
		if (li >= Utilisateur.LastId)
			Utilisateur.LastId = li + 1;
	}

	/**
	 * @return l'id du dossier de l'utilisateur.
	 * */
	public String getIdDossier() {
		return this.id + this.nom.toLowerCase().toString();
	}

	/**
	 * 
	 * @return l'id de l'utilisateur.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return le nom de l'utilisateur.
	 * */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @return le prenom de l'utilisateur.
	 * */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * @return la voix utilisee par l'utilisateur.
	 * */
	public Voix getVoix() {
		return this.voix;
	}

	/**
	 * 
	 * @return le genre de l'utilisateur.
	 */
	public TypeGenre getGenre() {
		return this.getVoix().getTypeGenre();
	}

	/**
	 * @return le temps de defilement entre les pictogrammes de l'utilisateur.
	 * */
	public OptionsUtilisateur getOptionsUtilisateur() {
		return this.opUt;
	}

	/**
	 * @param voix
	 *            nouvelle voix utilisee de l'utilisateur.
	 * */
	public void setVoix(Voix voix) {
		this.voix = voix;
	}

	/**
	 * 
	 * @param ut
	 * @return true si et seulement si l'id de l'utilisateur, le nom et le
	 *         prénom sont identiques.
	 */
	public boolean equals(Utilisateur ut) {
		return this.getId() == ut.getId()
				&& this.getNom().trim().equals(ut.getNom().trim())
				&& this.getPrenom().trim().equals(ut.getPrenom().trim());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (o instanceof Utilisateur)
			return equals((Utilisateur) o);
		else
			return false;

	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + getIdDossier() + ", nom=" + getNom()
				+ ", prenom=" + getPrenom() + ", voix=" + getVoix().toString()
				+ ", optionsUtilisateur =" + getOptionsUtilisateur() + "]";
	}

	/**
	 * 
	 * @param ou
	 */
	public void setOptionsUtilisateur(OptionsUtilisateur ou) {
		this.opUt = ou;
	}

	@Override
	public int compareTo(Utilisateur o) {
		if (this.getNom().compareTo(((Utilisateur) o).getNom()) == 0)
			return this.getPrenom().compareTo(((Utilisateur) o).getPrenom());
		else
			return this.getNom().compareTo(((Utilisateur) o).getNom());
	}

}