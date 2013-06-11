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

package com.locutus.vue.pc.utilisateur.creation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.locutus.modeles.Cadre;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.cadres.CadreClassique;
import com.locutus.vue.pc.cadres.Damier;
import com.locutus.vue.pc.cadres.LampeRouge;
import com.locutus.vue.pc.cadres.Surbrillance;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeCadre;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeCouleur;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeEpaisseur;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeTransparence;

/**
 * @author Sebastien Beugnon
 * 
 */
public class SelecteurDeCadre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String[] tabCadre = { "Surbrillance", "Lampe rouge", "Damier",
			"Cadre classique" };

	/**
	 * 
	 */
	private JComboBox<String> cadres;

	/**
	 * 
	 */
	private SelecteurDeCouleur couleurs;

	/**
	 * 
	 */
	private SelecteurDeTransparence tranparence;
	/**
	 * 
	 */
	private SelecteurDeEpaisseur epaisseur;

	/**
	 * 
	 */
	public SelecteurDeCadre() {
		this.cadres = new JComboBox<String>(tabCadre);
		this.cadres.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		this.cadres.addActionListener(new ActionLimiter(this));

		this.couleurs = new SelecteurDeCouleur();
		this.couleurs.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());

		this.tranparence = new SelecteurDeTransparence();
		this.tranparence.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		this.epaisseur = new SelecteurDeEpaisseur();
		this.epaisseur.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());

		this.add(this.cadres);
		this.add(this.couleurs);
		this.add(this.tranparence);
		this.add(this.epaisseur);
		this.epaisseur.setVisible(false);

	}

	/**
	 * 
	 * @return
	 */
	private String getChoixCadre() {
		return (String) this.cadres.getSelectedItem();
	}

	/**
	 * 
	 * @return
	 */
	private Color getChoixCouleur() {
		return this.couleurs.getCouleur();
	}

	/**
	 * 
	 * @return
	 */
	private float getChoixTransparence() {
		return this.tranparence.getTransparence();
	}

	private int getChoixEpaisseur() {
		return this.epaisseur.getEpaisseur();
	}

	/**
	 * @return le cadre choisi. Méthode à faire évoluer si on rajoute des
	 *         cadres.
	 */
	public Cadre getCadre() {
		Cadre cd = null;
		if (getChoixCadre().equals("Surbrillance"))
			cd = new Surbrillance(getChoixCouleur(), getChoixTransparence());
		else if (getChoixCadre().equals("Lampe rouge"))
			cd = new LampeRouge();
		else if (getChoixCadre().equals("Damier"))
			cd = new Damier();
		else if (getChoixCadre().equals("Cadre classique"))
			cd = new CadreClassique(getChoixCouleur(), getChoixEpaisseur());

		return cd;
	}

	class ActionLimiter implements ActionListener {

		private SelecteurDeCadre sdc;

		public ActionLimiter(SelecteurDeCadre selecteurDeCadre) {
			this.sdc = selecteurDeCadre;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (this.sdc.getChoixCadre().equals("Surbrillance")) {

				this.sdc.couleurs.setVisible(true);
				this.sdc.tranparence.setVisible(true);
				this.sdc.epaisseur.setVisible(false);

			} else if (this.sdc.getChoixCadre().equals("Cadre classique")) {

				this.sdc.couleurs.setVisible(true);
				this.sdc.tranparence.setVisible(false);
				this.sdc.epaisseur.setVisible(true);

			} else if (this.sdc.getChoixCadre().equals("Damier")) {

				this.sdc.couleurs.setVisible(false);
				this.sdc.tranparence.setVisible(false);
				this.sdc.epaisseur.setVisible(true);

			} else {

				this.sdc.couleurs.setVisible(false);
				this.sdc.tranparence.setVisible(false);
				this.sdc.epaisseur.setVisible(false);

			}

		}

	}
}
