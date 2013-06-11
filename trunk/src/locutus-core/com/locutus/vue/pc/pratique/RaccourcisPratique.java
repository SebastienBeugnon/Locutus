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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.locutus.controleurs.internes.pratique.ModulePratique;

/**
 * @author Sebastien Beugnon
 * 
 */
public class RaccourcisPratique implements KeyListener {

	private ModulePratique mp;

	/**
	 * @param modulePratique
	 */
	public RaccourcisPratique(ModulePratique modulePratique) {
		this.mp = modulePratique;
	}

	private ModulePratique getModulePratique() {
		return this.mp;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyChar() == 'q' || arg0.getKeyChar() == 'Q') {

			getModulePratique().chargerModulePratiqueSelection();

			getModulePratique().getModulePrincipal().getVue()
					.removeKeyListener(this);

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyChar() == 'q' || arg0.getKeyChar() == 'Q') {
			getModulePratique().chargerModulePratiqueSelection();

			getModulePratique().getModulePrincipal().getVue()
					.removeKeyListener(this);

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getKeyChar() == 'q' || arg0.getKeyChar() == 'Q') {

			getModulePratique().chargerModulePratiqueSelection();

			getModulePratique().getModulePrincipal().getVue()
					.removeKeyListener(this);

		}
	}

}
