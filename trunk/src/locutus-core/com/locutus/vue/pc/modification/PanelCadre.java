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
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.modeles.Cadre;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.cadres.CadreClassique;
import com.locutus.vue.pc.cadres.Damier;
import com.locutus.vue.pc.cadres.Surbrillance;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeCouleur;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeEpaisseur;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeTransparence;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelCadre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cadre cadre;
	private PanelModification pm;
	private SelecteurDeCouleur sdc;
	private SelecteurDeTransparence sdt;
	private SelecteurDeEpaisseur sde;

	/**
	 * @param pm
	 * @param next
	 */
	public PanelCadre(PanelModification pm, Cadre next) {
		this.cadre = next;
		this.pm = pm;
		super.setLayout(new GridLayout(3, 2));
		JLabel jl = new JLabel(this.getCadre().toString());
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.add(jl);

		if (this.getCadre().toString().equals((new Surbrillance()).toString())) {

			Surbrillance sb = (Surbrillance) this.getCadre();
			sdc = new SelecteurDeCouleur((sb.getCouleur()));
			sdc.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			sdc.addActionListener(new MiseAJourCadre());
			this.add(sdc);

			sdt = new SelecteurDeTransparence(sb.getTransparence());
			sdt.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			sdt.addActionListener(new MiseAJourCadre());
			this.add(sdt);

		} else if (this.getCadre().toString().equals((new Damier()).toString())) {

			Damier d = (Damier) this.getCadre();
			sde = new SelecteurDeEpaisseur(d.getEpaisseur());
			sde.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			sde.addActionListener(new MiseAJourCadre());
			this.add(sde);

		} else if (this.getCadre().toString()
				.equals((new CadreClassique()).toString())) {

			CadreClassique cc = (CadreClassique) this.getCadre();
			sdc = new SelecteurDeCouleur((cc.getCouleur()));
			sdc.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			sdc.addActionListener(new MiseAJourCadre());
			this.add(sdc);

			sde = new SelecteurDeEpaisseur(cc.getEpaisseur());
			sde.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			sde.addActionListener(new MiseAJourCadre());
			this.add(sde);
		}

		JButton jb = new JButton("Retirer cadre");
		jb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jb.addActionListener(new RetraitCadre());
		this.add(jb);
	}

	/**
	 * 
	 * @return le panneau de modification
	 */
	public PanelModification getPanelModification() {
		return this.pm;
	}

	/**
	 * 
	 * @return le cadre actuel.
	 */
	public Cadre getCadre() {
		return cadre;
	}

	/**
	 * 
	 * @author Beugnon
	 * 
	 */
	class MiseAJourCadre implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (getCadre().toString().equals((new Surbrillance()).toString())) {

				Surbrillance s = (Surbrillance) getCadre();
				s.setCouleur(sdc.getCouleur());
				s.setTransparence(sdt.getTransparence());
				getPanelModification().getVue().getModuleInterne()
						.lancerModificationCadre(s);

			} else if (getCadre().toString().equals(
					(new CadreClassique()).toString())) {

				CadreClassique s = (CadreClassique) getCadre();
				s.setCouleur(sdc.getCouleur());
				s.setEpaisseur(sde.getEpaisseur());
				getPanelModification().getVue().getModuleInterne()
						.lancerModificationCadre(s);

			} else if (getCadre().toString().equals((new Damier()).toString())) {

				Damier s = (Damier) getCadre();
				s.setEpaisseur(sde.getEpaisseur());
				getPanelModification().getVue().getModuleInterne()
						.lancerModificationCadre(s);

			}
		}

	}

	class RetraitCadre implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			getPanelModification().getVue().getModuleInterne()
					.lancerModificationRetraitCadre(getCadre());
			getPanelModification().chargerListeCadre();
		}

	}
}
