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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import com.locutus.modeles.Concept;

/**
 * 
 * @author Beugnon
 * 
 */
public class PanelSelectionConceptsPrincipal extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private ArrayList<PanelSelectionConcept> liste;
	/**
	 * 
	 */
	private VueCoursSelection vc;

	/**
	 * 
	 * @param vc
	 * @param list
	 */
	public PanelSelectionConceptsPrincipal(VueCoursSelection vc,
			List<Concept> list) {
		this.vc = vc;
		this.liste = new ArrayList<PanelSelectionConcept>();

		Iterator<Concept> it = list.iterator();
		this.setLayout(new GridLayout(list.size() / 2, 4));
		while (it.hasNext()) {
			PanelSelectionConcept pp = new PanelSelectionConcept(this.vc,
					it.next());
			if (getVue().getModuleInterne().getProfilListeConcept()
					.contientDerniers(pp.getConcept()))
				pp.mettreSelectionner();

			this.liste.add(pp);
			this.add(pp);
		}

	}

	/**
	 * 
	 * @param vc
	 * @param list
	 * @param listePictogramme
	 */
	public PanelSelectionConceptsPrincipal(VueCoursSelection vc,
			List<Concept> list, ArrayList<Concept> listePictogramme) {
		this.vc = vc;
		this.liste = new ArrayList<PanelSelectionConcept>();
		Iterator<Concept> it = list.iterator();
		this.setLayout(new GridLayout(list.size() / 2, 3));
		while (it.hasNext()) {
			PanelSelectionConcept pp = new PanelSelectionConcept(this.vc,
					it.next());
			if (getVue().getModuleInterne().getProfilListeConcept()
					.contientDerniers(pp.getConcept()))
				pp.mettreSelectionner();

			this.liste.add(pp);
			this.add(pp);
		}
	}

	/**
	 * 
	 * @return la vue courante du ModuleCours
	 */
	public VueCoursSelection getVue() {
		return this.vc;
	}

	/**
	 * 
	 */
	public void mettreAJour() {
		Iterator<PanelSelectionConcept> it = this.liste.iterator();
		while (it.hasNext()) {
			PanelSelectionConcept local = it.next();
			if (getVue().getModuleInterne().getProfilListeConcept()
					.contientDerniers(local.getConcept())) {
				local.mettreSelectionner();
			}
		}
		getVue().getModuleInterne().getModulePrincipal().getVue()
				.setVisible(true);
	}

}
