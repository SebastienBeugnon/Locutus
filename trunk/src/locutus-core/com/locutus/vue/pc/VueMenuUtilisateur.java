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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.internes.ModuleMenuUtilisateur;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.vue.pc.modification.PanelModification;

/**
 * 
 * @author Beugnon
 * 
 */
public abstract class VueMenuUtilisateur extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JPanel BlocCentral;
	/**
	 * 
	 */
	private JPanel[] MiniBlocs;

	/**
	 * 
	 * @param mi
	 */

	public VueMenuUtilisateur(ModuleInterne mi) {
		super(mi);
		charger();
	}

	@Override
	public void charger() {
		this.setLayout(new BorderLayout());

		this.chargerTitre();

		this.chargerBlocsSpeciaux();

		this.chargerMenu();
	}

	/**
	 * 
	 */
	protected abstract void chargerMenu();

	/**
	 * 
	 */
	protected final void chargerTitre() {
		JLabel j1 = new JLabel("Profil : "
				+ this.getModuleInterne().getUtilisateur().getNom() + " "
				+ getModuleInterne().getUtilisateur().getPrenom());
		j1.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTitre());
		super.add(j1, BorderLayout.NORTH);
	}

	protected final void chargerBlocsSpeciaux() {
		// BLOCS SPECIAUX

		this.BlocCentral = new JPanel();
		this.getBlocCentral().setLayout(new GridLayout(1, 1));

		this.MiniBlocs = new JPanel[2];

		super.add(this.getBlocCentral(), BorderLayout.CENTER);
		// /// FIN BLOC SPECIAL
	}

	/**
	 * 
	 */
	protected final void chargerBlocCours() {
		Font font = OptionsProgramme.getOptionsCourantes().getPoliceTexte();
		this.getBlocCentral().removeAll();

		this.setMiniBloc(0, new JPanel(new GridLayout(0, 1, 10, 10)));
		JButton j = new JButton("Niveau 1 : Photographies et Images");
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModuleCours(
						TypeRessource.photographies, TypeRessource.images);
			}
		});
		j.setFont(font);
		this.getMiniBloc(0).add(j);
		j = new JButton("Niveau 2 : Images et Pictogrammes");
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModuleCours(TypeRessource.images,
						TypeRessource.pictogrammes);
			}
		});
		j.setFont(font);

		this.getMiniBloc(0).add(j);
		j = new JButton("Niveau 3 : Pictogrammes seulement");
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModuleCours(
						TypeRessource.pictogrammes, null);
			}
		});

		j.setFont(font);
		getMiniBloc(0).add(j);
		this.getBlocCentral().add(getMiniBloc(0));
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
		System.gc();
	}

	/**
	 * 
	 */
	protected final void chargerBlocModification() {
		this.getBlocCentral().removeAll();

		this.setMiniBloc(1, new PanelModification(this));
		this.getBlocCentral().add(this.getMiniBloc(1));
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
		System.gc();
	}

	private JPanel getMiniBloc(int i) {
		return this.MiniBlocs[i];
	}

	private void setMiniBloc(int i, JPanel jp) {
		this.MiniBlocs[i] = jp;
	}

	private JPanel getBlocCentral() {
		return this.BlocCentral;
	}

	@Override
	public ModuleMenuUtilisateur getModuleInterne() {
		return (ModuleMenuUtilisateur) super.getModuleInterne();
	}

}
