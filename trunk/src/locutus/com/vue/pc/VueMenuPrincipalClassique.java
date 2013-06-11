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
package com.vue.pc;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.controleurs.internes.ModuleMenuPrincipalClassique;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.VueMenuPrincipal;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class VueMenuPrincipalClassique extends VueMenuPrincipal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String[] buttonname = { "Création d'un profil",
			"Sélection d'un profil existant", "Options de programme",
			"A propos", "Quitter" };

	/**
	 * @param mi
	 */
	public VueMenuPrincipalClassique(ModuleMenuPrincipalClassique mi) {
		super(mi);
		charger();
	}

	@Override
	public void charger() {
		this.setLayout(new GridLayout(buttonname.length + 2, 0, 50, 50));
		this.add(new JLabel());
		JButton[] buttons = new JButton[this.buttonname.length]; // Initialisation
																	// du
																	// tableau
																	// des
																	// boutons
		for (int i = 0; i < this.buttonname.length; i++) {
			buttons[i] = new JButton(this.buttonname[i]);
			buttons[i].setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			buttons[i].setSize(this.getWidth(), 10);
			this.add(buttons[i]); // Ajout des boutons a l'interface
			if (i == 0) {
				// Ajout de l'interaction : Creation Utilisateur
				buttons[i].setEnabled(true);
				buttons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						getModuleInterne().chargerModuleCreation();
					}

				});
			} else if (i == 1) {
				// Ajout de l'interaction : Selection Utilisateur
				buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						getModuleInterne().chargerModuleSelection();
					}

				});
			} else if (i == 2) {
				// Ajout de l'interaction Module Options Programme
				buttons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						getModuleInterne().chargerModuleOptionsProgramme();
					}
				});
			} else if (i == 3) {
				// Ajout de l'interaction Module A propos
				buttons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						getModuleInterne().chargerModulePropos();
					}
				});
			} else {
				// Ajout de l'interaction Quitter Programme
				buttons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						getModuleInterne().quitter();
					}
				});
			}

		}
	}

	/*
	 * 
	 */
	@Override
	public ModuleMenuPrincipalClassique getModuleInterne() {
		return (ModuleMenuPrincipalClassique) super.getModuleInterne();
	}

}
