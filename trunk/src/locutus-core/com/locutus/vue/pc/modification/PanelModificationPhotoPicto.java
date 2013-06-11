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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.outils.fichiers.UtilisationFichier;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelModificationPhotoPicto extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private PanelModification vmu;
	/**
	 * 
	 */
	private JScrollPane jsp;
	/**
	 * 
	 */
	private ArrayList<PanelLigneActions> pics;
	/**
	 * 
	 */
	private JPanel con;

	/**
	 * 
	 * @param vue
	 */
	public PanelModificationPhotoPicto(PanelModification vue) {
		this.vmu = vue;
		this.pics = new ArrayList<PanelLigneActions>();
		this.load(null);
	}

	/**
	 * 
	 * @param liste
	 */
	public void load(List<Concept> liste) {
		this.setLayout(new GridLayout(2, 1));
		if (liste == null) {
			liste = UtilisationFichier.chargerListeConcepts();
			JPanel pan = new JPanel();
			pan.setLayout(new GridLayout(2, 1));

			JLabel titre = new JLabel(
					"Pictogrammes, images, photographies et sons de l'utilisateur");
				
			pan.add(titre);

			con = new JPanel(new GridLayout(liste.size() + 1, 1, 5, 4));

			TextFieldRechercheModification tfrm = new TextFieldRechercheModification(
					"Recherche par nom...", getPanel());
			pan.add(tfrm);
			this.add(pan);
			pics = new ArrayList<>();
			Iterator<Concept> it = liste.iterator();
			int i = 0;
			while (it.hasNext()) {
				Concept local = it.next();
				if (!local.equals(new Concept("origine", "origine (graphe)"))) {
					pics.add(new PanelLigneActions(getPanel().getVue(), local));
					con.add(pics.get(i));
					i++;
				}

			}
		} else {
			this.jsp.removeAll();
			this.remove(jsp);
			if (liste.size() != 0) {
				pics = new ArrayList<>();
				con = new JPanel(new GridLayout(liste.size() + 1, 1, 5, 4));
				Iterator<Concept> it = liste.iterator();
				int i = 0;
				while (it.hasNext()) {
					Concept local = it.next();
					if (!local
							.equals(new Concept("origine", "origine (graphe)"))) {
						pics.add(new PanelLigneActions(getPanel().getVue(),
								local));
						con.add(pics.get(i));
						i++;
					}

				}
			} else {
				JLabel l = new JLabel("Aucun pictogramme...");
				l.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				con = new JPanel();
				con.add(l);
			}

		}
		jsp = new JScrollPane(con);
		this.add(jsp);

		getPanel().getVue().getModuleInterne().getModulePrincipal().getVue()
				.setVisible(true);

	}

	/**
	 * 
	 * @return la VueMenuUtilisateur parent
	 */
	public PanelModification getPanel() {
		return this.vmu;
	}

}
