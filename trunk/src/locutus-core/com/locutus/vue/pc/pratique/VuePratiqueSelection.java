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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.controleurs.internes.pratique.ModulePratiqueSelection;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.graphes.DiGraph;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.VuePanneau;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VuePratiqueSelection extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel statistique;

	private JPanel central;

	private JButton[] lockable;

	/**
	 * @param mi
	 */
	public VuePratiqueSelection(ModulePratiqueSelection mi) {
		super(mi);
		charger();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#charger()
	 */
	@Override
	public void charger() {
		super.setLayout(new BorderLayout());
		chargerMenu();

		this.central = new JPanel();
		this.central.setLayout(new GridLayout(2, 1, 10, 10));
		chargerAccesLancement();

		chargerStatistiques(null);
		this.add(this.central);
	}

	/**
	 * 
	 */
	private void chargerMenu() {
		JPanel west = new JPanel(new GridLayout(3, 1, 10, 10));

		JButton retourMenuButton = new JButton("Retour au menu de "
				+ this.getModuleInterne().getUtilisateur().getPrenom());
		retourMenuButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		retourMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerMenuUtilisateur();
			}
		});

		JButton chargerButton = new JButton("Charger l'arborescence de "
				+ this.getModuleInterne().getUtilisateur().getPrenom());
		chargerButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		chargerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				File fl = VueMessages.choisirFichier("graphe");

				if (fl != null && fl.exists())
					getModuleInterne().lancerChargementNouveauGraphe(fl);

				else if (fl != null && !fl.exists())
					VueMessages.afficherMessageDansVue(null,
							"Fichier inexistant.");

			}
		});

		lockable = new JButton[3];

		lockable[0] = new JButton("Récupérer le graphe de "
				+ this.getModuleInterne().getUtilisateur().getPrenom());
		lockable[0].setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		lockable[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File fl = VueMessages.sauvegarderGrapheDans();
				if (fl != null)
					getModuleInterne().lancerExportationGraphe(fl);
			}
		});
		if (getModuleInterne().getGraphe() == null)
			lockable[0].setEnabled(false);

		west.add(chargerButton);
		west.add(lockable[0]);
		west.add(retourMenuButton);
		this.add(west, BorderLayout.WEST);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#getModuleInterne()
	 */
	@Override
	public ModulePratiqueSelection getModuleInterne() {
		return (ModulePratiqueSelection) super.getModuleInterne();
	}

	/**
	 * @param di
	 * 
	 */
	public void chargerStatistiques(DiGraph<Concept> di) {
		if (statistique != null) {
			statistique.removeAll();
		} else {
			statistique = new JPanel(new GridLayout(5, 1));
		}
		if (di != null) {
			JLabel jl = new JLabel("Nombre de concepts : " + di.orderOfGraph());
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			statistique.add(jl);
			;
			jl = new JLabel("Nombre de connexion : " + di.sizeOfGraph());
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			statistique.add(jl);
			if (getModuleInterne().grapheUtilisable()) {

				jl = new JLabel("Etat de l'arborescence : Utilisable.");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				statistique.add(jl);

			} else {
				jl = new JLabel(
						"Etat de l'arborescence : il manque des ressources par défaut. ");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				statistique.add(jl);

				jl = new JLabel(
						"\n(Veuillez passer par l'outil d'administration des concepts pour rajouter les ressources manquantes)");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				statistique.add(jl);
			}
		} else {
			JLabel jl = new JLabel(
					"Pas d'arborescence de communication valable pour "
							+ getModuleInterne().getUtilisateur().getPrenom()
							+ ". ");
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			statistique.add(jl);
		}
		this.central.add(statistique);
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * 
	 */
	private void chargerAccesLancement() {

		JPanel acces = new JPanel();
		lockable[1] = new JButton("Lancer le mode pratique par défilement");
		lockable[1].setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		lockable[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerPratiqueParDefilement();

			}
		});
		acces.add(lockable[1]);

		lockable[2] = new JButton("Lancer le mode pratique à la souris");
		lockable[2].setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		lockable[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerPratiqueParSouris();

			}
		});
		acces.add(lockable[2]);

		this.central.add(acces);
		lockButtons();
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * 
	 */
	public void lockButtons() {
		if (this.getModuleInterne().getGraphe() == null) {
			for (int i = 0; i < 3; i++)
				lockable[i].setEnabled(false);
		} else {

			if (getModuleInterne().grapheUtilisable()
					&& getModuleInterne().getGraphe().getNodes().size() > 0) {
				for (int i = 0; i < 3; i++)
					lockable[i].setEnabled(true);
			}
		}
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

}
