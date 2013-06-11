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
package com.locutus.vue.pc;

import javax.swing.JPanel;

import com.locutus.controleurs.ModuleInterne;

/**
 * 
 * @author Beugnon Sebastien
 * 
 */
public abstract class VuePanneau extends JPanel implements VueAbstraite {
	/**
	 * clé de serialization
	 */
	private static final long serialVersionUID = 1L;
	private ModuleInterne mi;

	/**
	 * @param mi
	 */
	public VuePanneau(ModuleInterne mi) {
		this.mi = mi;
	}

	/**
	 * @return le module interne lié à cette vue.
	 */
	public ModuleInterne getModuleInterne() {
		return this.mi;
	}

	@Override
	public abstract void charger();
}
