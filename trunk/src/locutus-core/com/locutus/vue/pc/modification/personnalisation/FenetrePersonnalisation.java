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

package com.locutus.vue.pc.modification.personnalisation;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import com.locutus.modeles.Concept;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.vue.pc.VueMenuUtilisateur;
import com.locutus.vue.pc.modification.personnalisation.PanelTypeRessource;

/**
 * @author Sebastien Beugnon
 * 
 */
public class FenetrePersonnalisation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Concept cpt;
	private Utilisateur ut;
	private PanelTypeRessource[] tab;
	private VueMenuUtilisateur vmmu;

	/**
	 * @param vmmu
	 * @param ut
	 * @param cpt
	 * @param titre
	 */
	public FenetrePersonnalisation(VueMenuUtilisateur vmmu, Utilisateur ut,
			Concept cpt) {
		super.setTitle("Personnalisation de " + cpt.getNomConcept()
				+ " pour " + ut.getPrenom());
		
		this.cpt = cpt;
		this.ut = ut;
		this.vmmu = vmmu;
		chargerListe();
	}

	/**
	 * @return le concept actuel.
	 */
	public Concept getConcept() {
		return this.cpt;
	}

	/**
	 * @return l'utilisateur actuel.
	 */
	public Utilisateur getUtilisateur() {
		return this.ut;
	}

	/**
	 * @return la vue du menu utilisateur.
	 */
	public VueMenuUtilisateur getVueMenuUtilisateur() {
		return this.vmmu;
	}

	/**
	 * 
	 */
	public void chargerListe() {
		this.getContentPane().removeAll();
		this.setVisible(false);
		tab = new PanelTypeRessource[TypeRessource.values().length];
		super.getContentPane().setLayout(
				new GridLayout(TypeRessource.values().length, 1));
		for (int i = 0; i < TypeRessource.values().length; i++) {
			tab[i] = new PanelTypeRessource(this, TypeRessource.values()[i]);
			this.getContentPane().add(tab[i]);
		}
		super.setFocusable(true);
		super.setAutoRequestFocus(true);
		
		super.setAlwaysOnTop(true);
		super.setLocation(200, 200);
		super.setSize(new Dimension(360, 240));
		super.setResizable(false);
		super.setVisible(true);
	}

}
