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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.locutus.controleurs.internes.cours.ModuleCours;

/**
 * 
 * @author Beugnon Sebastien
 * 
 */
public final class RaccourcisCours implements KeyListener {

	/**
	 * 
	 */
	private ModuleCours md;

	/**
	 * 
	 * @param md
	 */
	public RaccourcisCours(ModuleCours md) {
		this.md = md;
	}

	/**
	 * 
	 * @return le module Cours attaché.
	 */
	protected ModuleCours getModuleCours() {
		return this.md;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyChar() == 'q' || arg0.getKeyChar() == 'Q') {

			getModuleCours().chargerModuleCoursSelection();

			getModuleCours().getModulePrincipal().getVue()
					.removeKeyListener(this);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyChar() == 'q' || arg0.getKeyChar() == 'Q') {

			getModuleCours().chargerModuleCoursSelection();
			getModuleCours().getModulePrincipal().getVue()
					.removeKeyListener(this);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getKeyChar() == 'q' || arg0.getKeyChar() == 'Q') {

			getModuleCours().chargerModuleCoursSelection();
			getModuleCours().getModulePrincipal().getVue()
					.removeKeyListener(this);

		}
	}
}
