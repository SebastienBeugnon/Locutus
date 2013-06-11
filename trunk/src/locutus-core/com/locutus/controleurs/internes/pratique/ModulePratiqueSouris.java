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
package com.locutus.controleurs.internes.pratique;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.modeles.graphes.Node;
import com.locutus.vue.pc.pratique.VuePratiqueSouris;

/**
 * @author Sebastien Beugnon
 * 
 */
public final class ModulePratiqueSouris extends ModulePratique {

	private volatile boolean lock = false;

	/**
	 * @param mp
	 * @param modulePratiqueSelection
	 */
	public ModulePratiqueSouris(ModulePrincipal mp,
			ModulePratiqueSelection modulePratiqueSelection) {
		super(mp, modulePratiqueSelection);
		lock = false;
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		super.init();
		super.setVue(new VuePratiqueSouris(this));
		getVue().afficherChoix(getRacine().getStraightSuccessors());
	}

	@Override
	public void selectionConcept(Node<Concept> e) {
		if (!lock) {
			lock=true;
			super.selectionConcept(e);
			lock=false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleInterne#getVue()
	 */
	@Override
	public VuePratiqueSouris getVue() {
		return (VuePratiqueSouris) super.getVue();
	}

}
