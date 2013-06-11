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
package com.locutus.vue.pc.pratique;

import java.util.List;

import com.locutus.controleurs.internes.pratique.ModulePratiqueSouris;
import com.locutus.modeles.Concept;
import com.locutus.modeles.graphes.Node;

/**
 * @author Sebastien Beugnon
 * 
 */
public final class VuePratiqueSouris extends VuePratique {

	/**
	 * version de sérialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param mi
	 */
	public VuePratiqueSouris(ModulePratiqueSouris mi) {
		super(mi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.pratique.VuePratique#charger()
	 */
	@Override
	public void charger() {
		afficherChoix(getModuleInterne().getRacine().getStraightSuccessors());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.pratique.VuePratique#afficherChoix(java.util.List)
	 */
	@Override
	public void afficherChoix(List<Node<Concept>> liste) {
		// utilisation de la méthode déjà définie dans VuePratique
		super.afficherChoix(liste);

		for (int i = 0; i < getTab().length; i++) {
			// On ajoute notre MouseListener spécialisé.
			getTab()[i].addMouseListener(new ClicPratiqueSouris(getTab()[i],
					this));
		}
		// On retire les panneaux non référencés.
		System.gc();
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#repaint()
	 */
	@Override
	public void repaint() {
		super.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#getModuleInterne()
	 */
	@Override
	public ModulePratiqueSouris getModuleInterne() {
		return (ModulePratiqueSouris) super.getModuleInterne();
	}
}
