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

package com.locutus.controleurs.internes.cours;

import java.util.List;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.vue.pc.cours.VueCoursSouris;

/**
 * @author Sebastien Beugnon
 * 
 */
public class ModuleCoursSouris extends ModuleCours {

	/**
	 * 
	 * @param modulePrincipal
	 * @param moduleCours
	 * @param liste
	 * @param optionsUtilisateur
	 */
	public ModuleCoursSouris(ModulePrincipal modulePrincipal,
			ModuleCoursSelection moduleCours, List<Concept> liste) {
		super(modulePrincipal, moduleCours);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.internes.cours.ModuleCours#init()
	 */
	@Override
	public void init() {
		super.init();
		this.setVue(new VueCoursSouris(this));
	}

	public void chargerModuleCoursSelection() {
		super.chargerModuleCoursSelection();
	}

}
