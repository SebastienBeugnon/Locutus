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
package com.locutus.vue.pc.cours;

import java.awt.GridLayout;

import com.locutus.controleurs.internes.cours.ModuleCoursSouris;
import com.locutus.vue.pc.VuePanneau;

/**
 * 
 * Fichier : VueCoursSouris.java<br/>
 * Email : Sébastien Beugnon - sebastien.beugnon.pro@gmail.com<br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Développé pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapés de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Pradès-Le-Lez 34730
 * 
 * @author Beugnon Sebastien - Chef de projet, Analyste, Développeur
 * 
 */
public final class VueCoursSouris extends VuePanneau {

	/**
	 * Id Version de sérialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur unique de VueCours Souris.
	 * 
	 * @param mi
	 *            Le module de Cours par apprentissage à la souris créant cette
	 *            vue.
	 */
	public VueCoursSouris(ModuleCoursSouris mi) {
		super(mi);
		charger();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#charger()
	 */
	@Override
	public void charger() {

		// On arrange l'organisation visuel de la fenêtre
		if (getModuleInterne().getListeConcept().size() > 4)
			// ligne de 4 pictogrammes
			super.setLayout(new GridLayout(0, 4));
		else
			// Ligne de X pictogrammes
			super.setLayout(new GridLayout(1, getModuleInterne()
					.getListeConcept().size()));

		PanelDessinCours[] vect = new PanelDessinCours[getModuleInterne()
				.getListeConcept().size()];

		for (int i = 0; i < getModuleInterne().getListeConcept().size(); i++) {
			// On construit chaque panel dessin de cours
			vect[i] = new PanelDessinCours(getModuleInterne(),
					getModuleInterne().getListeConcept().get(i));
			// On rajoute un KeyListener spécialisé
			vect[i].addMouseListener(new ClicCoursSouris(this, vect[i]));
			// On les rajoute à la fenêtre
			this.add(vect[i]);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#getModuleInterne()
	 */
	@Override
	public ModuleCoursSouris getModuleInterne() {
		return (ModuleCoursSouris) super.getModuleInterne();
	}
}
