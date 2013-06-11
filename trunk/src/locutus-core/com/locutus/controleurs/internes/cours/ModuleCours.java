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

 **/

package com.locutus.controleurs.internes.cours;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.outils.son.GestionnaireSon;
import com.locutus.vue.pc.cours.RaccourcisCours;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class ModuleCours extends ModuleInterne {

	/**
	 * 
	 */
	private ModuleCoursSelection mcs;

	/**
	 * 
	 */
	private RaccourcisCours rc;

	/**
	 * 
	 */
	private List<Concept> liste;
	/**
	 * 
	 */
	private Map<Concept, BufferedImage> image1;
	/**
	 * 
	 */
	private GestionnaireSon son;

	/**
	 * 
	 * @param mp
	 * @param mcs
	 */
	public ModuleCours(ModulePrincipal mp, ModuleCoursSelection mcs) {
		super(mp);
		this.mcs = mcs;
		this.rc = new RaccourcisCours(this);
		getModulePrincipal().getVue().addKeyListener(getRaccourcisCours());
	}

	/**
	 * 
	 * @return
	 */
	private RaccourcisCours getRaccourcisCours() {
		return this.rc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		this.liste = getModuleCoursSelection().getListeConcept();
		this.son = new GestionnaireSon();
		this.image1 = new HashMap<Concept, BufferedImage>();

		Iterator<Concept> it = this.getListeConcept().iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			this.getGestionnaireSon().ajouterSonConcept(
					local,
					UtilisationFichier.chargerSon(local,
							getModuleCoursSelection().getUtilisateur()));

			this.image1.put(local, UtilisationFichier.chargerImage(
					getModuleCoursSelection().getExo1(), local,
					getModuleCoursSelection().getUtilisateur()));
		}
	}

	/**
	 * 
	 */
	public void chargerModuleCoursSelection() {
		getModulePrincipal().changerModuleCourant(getModuleCoursSelection());
		getModulePrincipal().getVue().removeKeyListener(this.rc);
	}

	/**
	 * 
	 * @return le module de sélection des pictogrammes pour une session cours.
	 */
	public ModuleCoursSelection getModuleCoursSelection() {
		return this.mcs;
	}

	/**
	 * Accesseur
	 * 
	 * @return le gestionnaire de sons de ce module.
	 */
	public GestionnaireSon getGestionnaireSon() {
		return this.son;
	}

	/**
	 * 
	 * @param cpt
	 * @return Une image utilisable pour un affichage.
	 */
	public BufferedImage getImage1(Concept cpt) {
		return this.image1.get(cpt);
	}

	/**
	 * 
	 * @return La liste des concepts à afficher.
	 */
	public List<Concept> getListeConcept() {
		return this.liste;
	}

	/**
	 * 
	 */
	public void jouerElementSonore() {
		try {
			getGestionnaireSon().jouerElementSonore(
					UtilisationFichier.chargerGui("bip.mp3"));
		} catch (IOException e) {
			System.err.println("Chargement du but sonore impossible"
					+ e.getMessage());
			e.printStackTrace();
		}
	}
}
