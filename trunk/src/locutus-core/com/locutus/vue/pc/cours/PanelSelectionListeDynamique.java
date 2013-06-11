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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.exceptions.DeplacementDansListeException;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.JImagePanel;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelSelectionListeDynamique extends JPanel {
	/**
	 * 
	 */
	public static final int FIRST = 0;
	/**
	 * 
	 */
	public static final int MIDDLE = 1;
	/**
	 * 
	 */
	public static final int LAST = 2;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String GUI_DOWN = "down.png";
	private static final String GUI_UP = "up.png";
	private static final String GUI_SUP = "need.png";

	private VueCoursSelection vcs;
	private Concept cpt;

	/**
	 * @param vcs
	 * @param cpt
	 */
	public PanelSelectionListeDynamique(VueCoursSelection vcs, Concept cpt) {
		this.vcs = vcs;
		this.cpt = cpt;
		JLabel jl = new JLabel(getConcept().getNomConcept());
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.add(jl);
	}

	/**
	 * @param vcs
	 * @param cpt
	 * @param pos
	 */
	public PanelSelectionListeDynamique(VueCoursSelection vcs, Concept cpt,
			int pos) {
		this(vcs, cpt);
		this.setLayout(new GridLayout(0, 4, 0, 0));
		if (pos == FIRST) {
			// Uniquement bouton pour descendre
			try {

				JImagePanel downButton = new JImagePanel(
						ImageIO.read(new FileInputStream(UtilisationFichier
								.chargerGui(GUI_DOWN))));
				downButton.addMouseListener(new ListeListener(this, false));
				this.add(downButton);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (pos == MIDDLE) {
			// Deux boutons
			try {
				JImagePanel upButton = new JImagePanel(
						ImageIO.read(new FileInputStream(UtilisationFichier
								.chargerGui(GUI_UP))));
				upButton.addMouseListener(new ListeListener(this, true));
				this.add(upButton);
				JImagePanel downButton = new JImagePanel(
						ImageIO.read(new FileInputStream(UtilisationFichier
								.chargerGui(GUI_DOWN))));
				downButton.addMouseListener(new ListeListener(this, false));
				this.add(downButton);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (pos == LAST) {
			// Uniquement bouton pour monter
			try {
				JImagePanel upButton = new JImagePanel(
						ImageIO.read(new FileInputStream(UtilisationFichier
								.chargerGui(GUI_UP))));
				upButton.addMouseListener(new ListeListener(this, true));
				this.add(upButton);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Bouton pour supprimer
		try {
			JImagePanel supButton = new JImagePanel(
					ImageIO.read(new FileInputStream(UtilisationFichier
							.chargerGui(GUI_SUP))));

			supButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					getVueCoursSelection().getModuleInterne().supConcept(
							getConcept());
					getVueCoursSelection().reload(
							getVueCoursSelection().getModuleInterne()
									.getListeTotale());
					getVueCoursSelection().reloadListe();
				}
			});

			this.add(supButton);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the vcs
	 */
	public VueCoursSelection getVueCoursSelection() {
		return vcs;
	}

	/**
	 * @return the cpt
	 */
	public Concept getConcept() {
		return cpt;
	}

	/**
	 * 
	 * @author Sebastien Beugnon
	 * 
	 */
	private final class ListeListener extends MouseAdapter {

		/**
		 * 
		 */
		private PanelSelectionListeDynamique psld;
		/**
		 * 
		 */
		private boolean up;

		/**
		 * 
		 * @param panelSelectionListeDynamique
		 * @param up
		 */
		public ListeListener(
				PanelSelectionListeDynamique panelSelectionListeDynamique,
				final boolean up) {
			this.psld = panelSelectionListeDynamique;
			this.up = up;

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				getPanel()
						.getVueCoursSelection()
						.getModuleInterne()
						.deplacerUnCranDansListe(getPanel().getConcept(),
								getUp());
			} catch (DeplacementDansListeException e) {
				e.printStackTrace();
			}
		}

		public PanelSelectionListeDynamique getPanel() {
			return this.psld;
		}

		public boolean getUp() {
			return this.up;
		}

	}

}
