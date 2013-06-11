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
package com.vue.pc.utilisateur.creation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.controleurs.internes.utilisateur.ModuleUtilisateurCreation;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.exceptions.modeles.OptionsUtilisateurException;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.vue.pc.VuePanneau;
import com.locutus.vue.pc.utilisateur.creation.PanelNombreImage;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeDuree;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeVoix;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VueUtilisateurCreation extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private SelecteurDeVoix SelecteurVoix;
	/**
	 * 
	 */
	private PanelNombreImage jcb;
	/**
	 * 
	 */
	private SelecteurDeDuree tmpslide;

	/**
	 * @param mi
	 */
	public VueUtilisateurCreation(ModuleInterne mi) {
		super(mi);
		charger();
	}

	@Override
	public void charger() {
		this.setLayout(new GridLayout(5, 1, 10, 10));
		JLabel title = new JLabel("Création d'un nouveau profil");
		title.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTitre());
		this.add(title, 0);

		// CREATION DU PANEL DE SAISIE DE NOM / PRENOM
		JPanel flow = new JPanel();

		JPanel txtpan = new JPanel(new GridLayout(2, 2, 10, 10));
		JLabel labelname = new JLabel("Nom : ");
		labelname.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		txtpan.add(labelname);
		final JTextField txtname = new JTextField();
		txtname.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		txtname.setColumns(11);
		txtpan.add(txtname);
		JLabel labelsurname = new JLabel("Prénom : ");
		labelsurname.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		txtpan.add(labelsurname);
		final JTextField txtsurname = new JTextField();
		txtsurname.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		txtpan.add(txtsurname);

		flow.add(txtpan);
		this.add(flow);

		// CREATION DU PANEL DE SELECTION DE VOIX
		flow = new JPanel();

		this.SelecteurVoix = new SelecteurDeVoix();
		flow.add(this.SelecteurVoix);
		this.add(flow);

		// CREATION PANEL DE CHOIX DU NOMBRE D'IMAGES/DEFILEMENT
		flow = new JPanel();
		this.jcb = new PanelNombreImage();
		this.tmpslide = new SelecteurDeDuree();
		flow.add(this.jcb);
		flow.add(this.tmpslide);
		this.add(flow);

		// CREATION DU PANEL DE CONCLUSION
		flow = new JPanel();
		JButton j = new JButton("Créer");
		j.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!txtname.getText().trim().equals("")
						&& !txtsurname.getText().trim().equals(""))
					try {
						// TEST CREATION
						getModuleInterne().lancerCreation(
								txtname.getText().trim(),
								txtsurname.getText().trim(),
								SelecteurVoix.getVoix(),
								new OptionsUtilisateur(tmpslide.getValue(), jcb
										.getNbImg()));
					} catch (OptionsUtilisateurException e) {
						e.printStackTrace();
					}

			}
		});
		flow.add(j);

		j = new JButton("Retour au menu principal");
		j.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().retourMenuPrincipal();
			}
		});
		flow.add(j);
		this.add(flow);

	}

	@Override
	public ModuleUtilisateurCreation getModuleInterne() {
		return (ModuleUtilisateurCreation) super.getModuleInterne();
	}
}
