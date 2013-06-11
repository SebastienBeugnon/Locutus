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

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.Utilisateur;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelUtilisateur extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur ut;
	private VueUtilisateurSelection vp;

	/**
	 * @param ut
	 * @param vp
	 */
	public PanelUtilisateur(Utilisateur ut, VueUtilisateurSelection vp) {
		this.ut = ut;
		this.vp = vp;
		this.setLayout(new FlowLayout());
		JPanel pan = new JPanel();
		JButton select = new PanelUtilisateurButton("Sélectionner", this, true);
		select.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		pan.add(select);
		JLabel jl = new JLabel("Nom : " + ut.getNom());
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		JLabel jlb = new JLabel("- Prénom : " + ut.getPrenom() + "  ");
		jlb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		pan.add(jl);
		pan.add(jlb);

		jl = new JLabel("    Voix : " + ut.getVoix().getNom() + " (Genre : "
				+ ut.getVoix().getTypeGenre() + ")");
		jl.setFont(new Font("tahoma", Font.PLAIN, 26));
		pan.add(jl);
		if (!ut.getNom().equalsIgnoreCase("test")) {
			JButton suppri = new PanelUtilisateurButton("Supprimer", this,
					false);
			pan.add(suppri);
		}
		this.add(pan);
	}

	/**
	 * @return la vue de sélection des utilisateurs.
	 */
	public VueUtilisateurSelection getVue() {
		return this.vp;
	}

	/**
	 * @return l'utilisateur courant.
	 */
	public Utilisateur getUtilisateur() {
		return this.ut;
	}

	class PanelUtilisateurButton extends JButton implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private PanelUtilisateur pu;
		private boolean select;

		PanelUtilisateurButton(String title, PanelUtilisateur pu, boolean select) {
			super(title);
			this.pu = pu;
			this.select = select;
			this.addActionListener(this);
		}

		public PanelUtilisateur getPanelUtilisateur() {
			return this.pu;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (select) {
				this.getPanelUtilisateur()
						.getVue()
						.getModuleInterne()
						.chargerUtilisateur(
								this.getPanelUtilisateur().getUtilisateur());
			} else
				this.getPanelUtilisateur()
						.getVue()
						.getModuleInterne()
						.supprimerUtilisateur(
								this.getPanelUtilisateur().getUtilisateur());
		}
	}

}
