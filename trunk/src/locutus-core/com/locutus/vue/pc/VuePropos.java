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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.locutus.controleurs.internes.autres.ModulePropos;
import com.locutus.modeles.OptionsProgramme;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VuePropos extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param mi
	 */
	public VuePropos(ModulePropos mi) {
		super(mi);
		charger();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.vue.pc.VuePanneau#charger()
	 */
	@Override
	public void charger() {
		JButton retour = new JButton("Retour au menu principal");
		retour.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		retour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModuleMenuPrincipal();

			}
		});
		this.add(retour);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.vue.pc.VuePanneau#getModuleInterne()
	 */
	@Override
	public ModulePropos getModuleInterne() {
		return (ModulePropos) super.getModuleInterne();
	}

}
