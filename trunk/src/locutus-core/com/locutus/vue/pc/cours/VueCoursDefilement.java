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
import java.util.List;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.internes.cours.ModuleCoursDefilement;
import com.locutus.modeles.Concept;
import com.locutus.vue.pc.VuePanneau;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class VueCoursDefilement extends VuePanneau {

	/**
	 * Id Version de sérialisation.
	 */
	private static final long serialVersionUID = 1L;
	private PanelDessinCours[] tab;

	/**
	 * 
	 * @param mi
	 */
	public VueCoursDefilement(ModuleInterne mi) {
		super(mi);
		charger();
	}

	@Override
	public void charger() {

	}

	// METHODE PAR DEFILEMENT
	/**
	 * 
	 * @param y
	 */
	public void dessinerCadre(int y) {
		if (y == 0) {
			this.tab[getTab().length - 1].retirerCadre();
		} else {
			this.tab[y - 1].retirerCadre();
		}

		this.tab[y].chargerCadres();

	}

	/**
	 * 
	 * @param i
	 * @return le panneau de dessin à la position i
	 */
	public PanelDessinCours getPanelDessin(int i) {
		return this.tab[i];
	}

	@Override
	public ModuleCoursDefilement getModuleInterne() {
		return (ModuleCoursDefilement) super.getModuleInterne();
	}

	/**
	 * @return le tableau des panneaux à dessin
	 */
	public PanelDessinCours[] getTab() {
		return this.tab;
	}

	/**
	 * @param liste
	 */
	public void afficherChoix(List<Concept> liste) {
		// On supprime les panneaux existant.
		removePanels();

		if (liste.size() <= 4) {
			// ligne de X pictogrammes.
			this.setLayout(new GridLayout(1, liste.size()));
		} else if (liste.size() <= 8) {
			// Ligne de 4 pictogrammes.
			this.setLayout(new GridLayout(1, 4));
		}

		int k;
		if (getModuleInterne().getModuleCoursSelection().estDoubleExercice())
			k = 2;
		else
			k = 1;
		this.tab = new PanelDessinCours[liste.size()];
		for (int i = 0; i < getTab().length; i++) {
			this.tab[i] = new PanelDessinCours(k, getModuleInterne(),
					liste.get(i));
			this.add(tab[i]);

		}

		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
		System.gc();
	}

	/**
	 * Méthode servant à retiré les panneaux de dessin pratique, afin
	 * d'installer des nouveaux dans la vue.
	 */
	public void removePanels() {
		if (tab != null) {
			this.removeAll();
		}
	}
}
