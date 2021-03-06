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

import com.locutus.controleurs.internes.pratique.ModulePratiqueDefilement;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VuePratiqueDefilement extends VuePratique {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param mi
	 */
	public VuePratiqueDefilement(ModulePratiqueDefilement mi) {
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

	}

	/**
	 * @param y
	 */
	public void dessinerCadre(int y) {

		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
		if (y == 0) {
			getTab()[getTab().length - 1].effacerCadres();
		} else {
			getTab()[y - 1].effacerCadres();
		}

		if (y < getTab().length)
			getTab()[y].afficherCadres();
		else
			System.out.println("mon curseur est trop grand.");

	}

	@Override
	public ModulePratiqueDefilement getModuleInterne() {
		return (ModulePratiqueDefilement) super.getModuleInterne();
	}



}
