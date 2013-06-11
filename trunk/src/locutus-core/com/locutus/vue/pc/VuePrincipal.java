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

import javax.swing.JFrame;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.outils.WindowUtilities;

/**
 * 
 * @author Beugnon Sebastien
 * @version 1.0
 * 
 */
public class VuePrincipal extends JFrame implements VueAbstraite {

	/**
	 * clé de serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private ModulePrincipal mp;

	/**
	 * Constructeur avec titre.
	 * 
	 * @param titre
	 *            le nom de la fenêtre.
	 * @param mp
	 */
	public VuePrincipal(String titre, ModulePrincipal mp) {
		super(titre);
		this.mp = mp;
		this.charger();

	}

	/**
	 * Construit la fenêtre et ses spécificités.
	 */
	public void charger() {
		super.setFocusable(true);
		WindowUtilities.setNativeLookAndFeel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		if (!mp.getFlagState(ModulePrincipal.FLAG_NOT_FULLSCREEN)) {
			this.setResizable(false);
			this.setUndecorated(true);
		}

		this.setVisible(true);

		if (!mp.getFlagState(ModulePrincipal.FLAG_NOT_FULLSCREEN))
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else {
			this.setSize(680, 540);
		}

	}

	/**
	 * Rend visible la vue d'un module interne
	 * 
	 * @param pane
	 *            la Vue de module interne a afficher
	 */
	public void setPanneau(VuePanneau pane) {
		this.setContentPane(pane);
		pane.setVisible(true);
		this.setVisible(true);
	}

}