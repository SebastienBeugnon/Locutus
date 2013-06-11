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

import com.locutus.exceptions.modeles.OptionsUtilisateurException;

/**
 * 
 * @author Beugnon Sebastien
 * 
 */
public class OptionsUtilisateur implements Serializable {
	/**
	 * la version de serialization
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static float TMPMIN = 2000.0f;// stocker à l'extérieur dans un
											// fichier
	/**
	* 
	*/
	public static float TMPMAX = 20000.0f;
	/**
	 * 
	 */
	public static int NBIMGMINI = 1;
	/**
	 * 
	 */
	public static int NBIMGMAX = 3;
	/**
	 * 
	 */
	private float tmpDefilement;
	/**
	 * 
	 */
	private int nbImgDefilement;
	/**
	 * 
	 */
	private List<Cadre> cadre;
	/**
	 * 
	 */
	private boolean bipDeChangement;

	/**
	 * 
	 * @param tmpDefilement
	 * @param nbImgDefilement
	 * @throws OptionsUtilisateurException
	 */
	public OptionsUtilisateur(float tmpDefilement, int nbImgDefilement)
			throws OptionsUtilisateurException {
		if (tmpDefilement >= TMPMIN && TMPMAX >= tmpDefilement)
			this.tmpDefilement = tmpDefilement;
		else
			throw new OptionsUtilisateurException(
					"Construction OptionsUtilisateur impossible.\nRaison : Temps invalide "
							+ tmpDefilement + " (non compris entre "
							+ OptionsUtilisateur.TMPMIN + " et "
							+ OptionsUtilisateur.TMPMAX + ").");

		if (nbImgDefilement >= NBIMGMINI && NBIMGMAX >= nbImgDefilement)
			this.nbImgDefilement = nbImgDefilement;
		else
			throw new OptionsUtilisateurException(
					"Construction OptionsUtilisateur impossible.\nRaison : Nombre d'images invalide "
							+ tmpDefilement
							+ " (non compris entre "
							+ OptionsUtilisateur.NBIMGMINI
							+ " et "
							+ OptionsUtilisateur.TMPMAX + ").");
		this.bipDeChangement = true;
		this.cadre = new ArrayList<Cadre>();
	}

	/**
	 * 
	 * @param tmp
	 * @param nb
	 * @param bip
	 * @throws OptionsUtilisateurException
	 */
	public OptionsUtilisateur(float tmp, int nb, boolean bip)
			throws OptionsUtilisateurException {
		this(tmp, nb);
		this.bipDeChangement = bip;

	}

	/**
	 * 
	 * @param ou
	 */
	public OptionsUtilisateur(OptionsUtilisateur ou) {
		this.nbImgDefilement = ou.getNbImgDefilement();
		this.tmpDefilement = ou.getTmpDefilement();
		this.cadre = new ArrayList<Cadre>();
		Iterator<Cadre> it = ou.getCadres().iterator();
		while (it.hasNext())
			this.cadre.add(it.next());
		this.bipDeChangement = ou.getBipDeChangement();
	}

	/**
	 * 
	 * @param tmp
	 * @throws OptionsUtilisateurException
	 */
	public void setTmpDefilement(float tmp) throws OptionsUtilisateurException {
		if (tmp < TMPMIN || TMPMAX < tmp)
			throw new OptionsUtilisateurException(
					"Modification OptionsUtilisateur impossible.\nRaison : Temps Invalide "
							+ tmpDefilement + " (non compris entre "
							+ OptionsUtilisateur.TMPMIN + " et "
							+ OptionsUtilisateur.TMPMAX + ").");
		else
			this.tmpDefilement = tmp;
	}

	/**
	 * 
	 * @return le temps de defilement entre chaque image
	 */
	public float getTmpDefilement() {
		return this.tmpDefilement;
	}

	/**
	 * 
	 * @return le nombre d'image affichee en meme temps sur l'ecran
	 */
	public int getNbImgDefilement() {
		return this.nbImgDefilement;
	}

	/**
	 * 
	 * @param size
	 */
	public void setNbImgDefilement(int size) {
		this.nbImgDefilement = size;
	}

	/**
	 * 
	 * @param cad
	 */
	public void addCadre(Cadre cad) {
		if (!this.cadre.contains(cad))
			this.cadre.add(cad);
	}

	/**
	 * 
	 * @param cad
	 */
	public void supCadre(Cadre cad) {
		if (this.cadre.contains(cad))
			this.cadre.remove(cad);
	}

	/**
	 * 
	 * @return La liste des cadres.
	 */
	public List<Cadre> getCadres() {
		return this.cadre;
	}

	/**
	 * 
	 * @return true si et seulement si on a activé le bip de changement.
	 */
	public boolean getBipDeChangement() {
		return this.bipDeChangement;
	}

	/**
	 * 
	 * @param bl
	 */
	public void setBipDeChangement(boolean bl) {
		this.bipDeChangement = bl;
	}

}
