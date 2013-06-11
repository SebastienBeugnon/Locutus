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

package com.vue.pc.utilisateur.selection;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.controleurs.internes.utilisateur.ModuleUtilisateurSelection;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.Utilisateur;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VuePanneau;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VueUtilisateurSelection extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<Utilisateur> own;
	/**
	 * 
	 */
	private JScrollPane jsp;

	/**
	 * @param mi
	 */
	public VueUtilisateurSelection(ModuleInterne mi) {
		super(mi);
		this.own = UtilisationFichier.chargerListeUtilisateur();
		charger();
	}

	/**
	 * @param mi
	 * @param recherche
	 */
	public VueUtilisateurSelection(ModuleInterne mi, List<Utilisateur> recherche) {
		super(mi);
		this.own = recherche;
		charger();
	}

	public ModuleUtilisateurSelection getModuleInterne() {
		return (ModuleUtilisateurSelection) super.getModuleInterne();
	}

	@Override
	public void charger() {
		this.setLayout(new BorderLayout());
	
			JTextField jtf = new TextFieldRechercheUtilisateur(
					"Recherche par nom...", this);
			jtf.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			this.add(jtf, BorderLayout.NORTH);
		
		JPanel topPanel = new JPanel(new GridLayout(this.own.size(), 1, 10, 10));
		Iterator<Utilisateur> it = own.iterator();
		while (it.hasNext()) {
			PanelUtilisateur pu = new PanelUtilisateur(it.next(), this);
			topPanel.add(pu);
		}
		JButton retour = new JButton("Retour au menu Principal");
		retour.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		retour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerMenuPrincipal();
			}

		});
		this.jsp = new JScrollPane(topPanel);
		this.add(this.jsp, BorderLayout.CENTER);
		this.add(retour, BorderLayout.SOUTH);
	}

	/**
	 * @param list
	 */
	public void recharger(List<Utilisateur> list) {
		this.remove(this.jsp);
		this.own = list;
		JPanel topPanel = new JPanel(new GridLayout(this.own.size(), 1, 10, 10));
		Iterator<Utilisateur> it = own.iterator();
		while (it.hasNext()) {
			PanelUtilisateur pu = new PanelUtilisateur(it.next(), this);
			topPanel.add(pu);
		}

		this.jsp = new JScrollPane(topPanel);
		this.jsp.setWheelScrollingEnabled(true);
		this.add(this.jsp, BorderLayout.CENTER);
		this.getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

}
