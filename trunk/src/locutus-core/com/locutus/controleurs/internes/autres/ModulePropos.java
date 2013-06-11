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

package com.locutus.controleurs.internes.autres;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuPrincipal;
import com.locutus.vue.pc.VuePropos;

/**
 * @author Sebastien Beugnon
 * 
 */
public class ModulePropos extends ModuleInterne {

	private ModuleMenuPrincipal mmp;

	/**
	 * @param mp
	 * @param mmp
	 */
	public ModulePropos(ModulePrincipal mp, ModuleMenuPrincipal mmp) {
		super(mp);
		this.mmp = (mmp);
		init();
	}

	@Override
	public void init() {
		setVue(new VuePropos(this));

	}

	/**
	 * @return retourne le module du menu principal.
	 **/
	public ModuleMenuPrincipal getModuleMenuPrincipal() {
		return mmp;
	}

	/**
	 * 
	 */
	public void chargerModuleMenuPrincipal() {
		this.getModulePrincipal()
				.changerModuleCourant(getModuleMenuPrincipal());
	}
}
