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
package com.locutus.vue.pc.optionsprogramme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.locutus.controleurs.internes.autres.ModuleGestionOptionsProgramme;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.VuePanneau;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VueOptionsProgramme extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JPanel centerPan;

	/**
	 * @param mi
	 */
	public VueOptionsProgramme(ModuleGestionOptionsProgramme mi) {
		super(mi);
		charger();
	}

	@Override
	public ModuleGestionOptionsProgramme getModuleInterne() {
		return (ModuleGestionOptionsProgramme) super.getModuleInterne();
	}

	@Override
	public void charger() {
		this.setLayout(new BorderLayout());

		chargerMenu();

		afficherCentreOutils();
	}

	/**
	 * 
	 */
	private void chargerMenu() {
		JPanel west = new JPanel(new GridLayout(3, 1));
		// BOUTON MODIFICATION OPTIONS PROGRAMME
		JButton modOpButton = new JButton("Modification du programme");
		modOpButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		modOpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				afficherCentreModifications();
			}
		});
		// modOpButton.setEnabled(false);
		west.add(modOpButton);

		// BOUTON OUTILS MAJEURS DE MODIFICATION DU MODELE
		// AJOUT DE CONCEPT // AJOUT/MODIFICATION DES RESSOURCES PAR DEFAUT
		// POUR PROGRAMME INDEPENDANT ET DEPENDANT AU SMB

		JButton outilButton = new JButton("Outils");
		outilButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		outilButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				afficherCentreOutils();
			}
		});
		west.add(outilButton);

		// BOUTON POUR REVENIR EN ARRIERE
		JButton retourButton = new JButton("Retour au menu principal");
		retourButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		retourButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModuleMenuPrincipal();
			}
		});

		west.add(retourButton);
		this.add(west, BorderLayout.WEST);
	}

	/**
	 * 
	 */
	private void afficherCentreModifications() {
		if (this.centerPan != null)
			this.remove(this.centerPan);
		this.centerPan = new PanelOptionProgrammeChemins(this);
		this.add(this.centerPan, BorderLayout.CENTER);
		this.getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	private void afficherCentreOutils() {
		//Afficher les boutons des outils.
		if (this.centerPan != null)
			this.remove(this.centerPan);
		this.centerPan = new JPanel(new GridLayout(2, 0));
		JButton jb = new JButton("Outil de gestion des concepts");
		jb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().lancerOutilsAdministrationConcepts();

			}
		});
		JPanel pan = new JPanel();
		pan.add(jb);
		this.centerPan.add(pan);

		jb = new JButton("Outil de gestion des voix");
		jb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().lancerOutilAdministrationVoix();
			}

		});
		pan = new JPanel();
		pan.add(jb);
		this.centerPan.add(pan);

		this.add(this.centerPan, BorderLayout.CENTER);
		this.getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * 
	 * @param message
	 */
	public void afficherMessage(String message) {
		VueMessages.afficherMessageDansVue(this, message);
	}

	/**
	 * 
	 * @param message
	 * @return YES_OPTION / NO_OPTION / CANCEL_OPTION. see VueMessages !
	 */
	public int afficherQuestion(String message) {
		return VueMessages.afficherQuestionDansVue(this, message);
	}

}
