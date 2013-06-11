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
package com.locutus.vue.pc.modification;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.VueMenuUtilisateur;
import com.locutus.vue.pc.modification.personnalisation.FenetrePersonnalisation;

/**
 * 
 * @author Beugnon Sebastien
 * 
 */
public class PanelLigneActions extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	* 
 	*/
	private Concept pi;
	/**
	 * 
	 */

	private VueMenuUtilisateur vmmu;

	/**
	 * @param vmmu
	 * @param pi
	 */
	public PanelLigneActions(VueMenuUtilisateur vmmu, Concept pi) {
		this.pi = pi;
		this.vmmu = vmmu;
		super.setLayout(new GridLayout(1, 1));
		JButton j = new JButton(this.getConcept().getNomConcept());
		j.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chargerFenetrePersonnalisation(getConcept());
			}

		});
		this.add(j);

	}

	/**
	 * 
	 * @return le concept actuel.
	 */
	public Concept getConcept() {
		return this.pi;
	}

	/**
	 * @return la vue du menu utilsateur.
	 */
	public VueMenuUtilisateur getVueMenuUtilisateur() {
		return this.vmmu;
	}

	private void chargerFenetrePersonnalisation(Concept concept) {
		@SuppressWarnings("unused")
		FenetrePersonnalisation fen = new FenetrePersonnalisation(
				getVueMenuUtilisateur(), getVueMenuUtilisateur()
						.getModuleInterne().getUtilisateur(), getConcept());
	}

}