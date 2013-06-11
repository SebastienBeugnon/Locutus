package com.vue.pc;

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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.controleurs.internes.ModuleMenuUtilisateurClassique;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.VueMenuUtilisateur;

/**
 * 
 * @author Beugnon
 * 
 */
public class VueMenuUtilisateurClassique extends VueMenuUtilisateur {

	/**
	 * @param mi
	 */
	public VueMenuUtilisateurClassique(ModuleMenuUtilisateurClassique mi) {
		super(mi);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected void chargerMenu() {
		// /// MENU
		Font font = OptionsProgramme.getOptionsCourantes().getPoliceTexte();
		JPanel pan2 = new JPanel();
		pan2.setLayout(new GridLayout(0, 1, 10, 10));
		JButton j = new JButton("Module Cours");
		j.setFont(font);
		pan2.add(j);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chargerBlocCours();

			}

		});

		j = new JButton("Modifications");
		j.setFont(font);
		pan2.add(j);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chargerBlocModification();
			}

		});

		j = new JButton("Module Pratique");
		j.setFont(font);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModulePratiqueSelection();
			}
		});
		pan2.add(j);

		j = new JButton("Exportation du profil sur USB");
		j.setFont(font);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().lancerExportation();
			}
		});
		pan2.add(j);

		j = new JButton("Retour au menu principal");
		j.setFont(font);
		j.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerMenuPrincipal();
			}
		});

		pan2.add(j);

		super.add(pan2, BorderLayout.WEST);
		// ///FIN MENU
	}

	@Override
	public ModuleMenuUtilisateurClassique getModuleInterne() {
		return (ModuleMenuUtilisateurClassique) super.getModuleInterne();
	}

}
