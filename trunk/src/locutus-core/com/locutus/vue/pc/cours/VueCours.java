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

import com.locutus.controleurs.internes.cours.ModuleCours;
import com.locutus.vue.pc.VuePanneau;

/**
 * @author Sebastien Beugnon
 * 
 */
public abstract class VueCours extends VuePanneau {

	/**
	 * Id Version de sérialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param mi
	 */
	public VueCours(ModuleCours mi) {
		super(mi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#charger()
	 */
	@Override
	public abstract void charger();

}
