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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.internes.cours.ModuleCoursSelection;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.VuePanneau;

/**
 * 
 * Fichier : VueCoursSelection.java<br/>
 * Email : Sébastien Beugnon - sebastien.beugnon.pro@gmail.com<br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Développé pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapés de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Pradès-Le-Lez 34730
 * 
 * @author Beugnon Sebastien - Chef de projet, Analyste, Développeur
 * 
 */
public class VueCoursSelection extends VuePanneau {

	/**
	 * Id Version de sérialisation.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private PanelSelectionConceptsPrincipal pan;

	/**
	 * 
	 */
	private JScrollPane jsp;
	/**
	 * 
	 */
	private TextFieldRechercheConcepts tfr;
	/**
	 * 
	 */
	private JButton retourButton;
	/**
	 * 
	 */
	private JButton defilement;
	/**
	 * 
	 */
	private JScrollPane ListeDynamique;
	/**
	 * 
	 */
	private JPanel west;
	/**
	 * 
	 */
	private JButton souris;

	/**
	 * 
	 * @param mi
	 */
	public VueCoursSelection(ModuleInterne mi) {
		super(mi);
		charger();
	}

	@Override
	/**
	 * 
	 */
	public void charger() {
		this.setLayout(new BorderLayout());

		west = new JPanel(new GridLayout(5, 1, 10, 10));
		west.setPreferredSize(new Dimension(450,1200));
		retourButton = new JButton("Retour au menu de "
				+ getModuleInterne().getUtilisateur().getPrenom());
		retourButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		retourButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerMenuUtilisateur();
			}
		});
		west.add(retourButton);

		JButton favorisButton = new JButton("Charger les concepts favoris");
		favorisButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		favorisButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getModuleInterne().chargerListeFavoris();
				reloadListe();
			}
		});

		west.add(favorisButton);

		defilement = new JButton("Lancer l'apprentissage par le défilement");
		defilement.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		defilement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerDefilement();
			}
		});
		defilement.setEnabled(false);
		west.add(defilement);

		souris = new JButton("Lancer l'apprentissage par la souris");
		souris.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		souris.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getModuleInterne().chargerSouris();
			}
		});
		souris.setEnabled(false);
		west.add(souris);

		// A REMPLACER PAR UN JPANEL SPECIAL QUI RECUPERE LA LISTE
		ListeDynamique = new JScrollPane();
		west.add(ListeDynamique);
		this.add(west, BorderLayout.WEST);
		this.pan = new PanelSelectionConceptsPrincipal(this, getModuleInterne()
				.getListeTotale());// LISTE TOTALE
		this.jsp = new JScrollPane(this.pan);
		this.jsp.setWheelScrollingEnabled(true);
		this.tfr = new TextFieldRechercheConcepts("Rechercher par nom", this);
		this.add(tfr, BorderLayout.NORTH);
		this.add(this.jsp, BorderLayout.CENTER);

		reloadListe();
	}

	@Override
	public ModuleCoursSelection getModuleInterne() {
		return (ModuleCoursSelection) super.getModuleInterne();

	}

	/**
	 * 
	 * @param list
	 */
	public void reload(List<Concept> list) {
		this.remove(this.jsp);
		this.repaint();
		this.getModuleInterne().getModulePrincipal().getVue().setVisible(true);

		this.pan = new PanelSelectionConceptsPrincipal(this, list,
				(ArrayList<Concept>) this.getModuleInterne().getListeConcept());
		this.jsp = new JScrollPane(this.pan);
		this.jsp.setWheelScrollingEnabled(true);

		this.add(this.jsp, BorderLayout.CENTER);
		this.getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * 
	 */
	public void reloadListe() {
		if (getModuleInterne().getListeConcept().size() > 0) {
			defilement.setEnabled(true);
			souris.setEnabled(true);

		} else {
			defilement.setEnabled(false);
			souris.setEnabled(false);
		}

		west.remove(ListeDynamique);
		JPanel toppanel = new JPanel();

		toppanel.setLayout(new GridLayout(1 + getModuleInterne()
				.getListeConcept().size(), 0));
		for (int i = 0; i < getModuleInterne().getListeConcept().size(); i++) {
			PanelSelectionListeDynamique pan;
			if (i == 0)
				pan = new PanelSelectionListeDynamique(this, getModuleInterne()
						.getListeConcept().get(i),
						PanelSelectionListeDynamique.FIRST);
			else if ((i + 1) == getModuleInterne().getListeConcept().size())
				pan = new PanelSelectionListeDynamique(this, getModuleInterne()
						.getListeConcept().get(i),
						PanelSelectionListeDynamique.LAST);
			else
				pan = new PanelSelectionListeDynamique(this, getModuleInterne()
						.getListeConcept().get(i),
						PanelSelectionListeDynamique.MIDDLE);

			toppanel.add(pan);
		}
		ListeDynamique = new JScrollPane(toppanel);
		west.add(ListeDynamique);
		this.getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * @return la zone de recherche des concepts
	 */
	public TextFieldRechercheConcepts getTextFieldRecherche() {
		return this.tfr;
	}

	/**
	 * 
	 */
	public void mettreAJour() {
		this.pan.mettreAJour();
	}

}
