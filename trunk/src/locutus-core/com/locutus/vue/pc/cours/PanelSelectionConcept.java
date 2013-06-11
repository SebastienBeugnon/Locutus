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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.JImagePanel;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelSelectionConcept extends JPanel {

	class SelectionListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			if (valid.isSelected())
				getVue().getModuleInterne().addConcept(getConcept());
			else
				getVue().getModuleInterne().supConcept(getConcept());

			getVue().reloadListe();

		}
	}

	class SelectionClicListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			valid.setSelected(!valid.isSelected());
		}

	}

	class FavorisClicListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (favoris)
				getVue().getModuleInterne().supConceptFavoris(getConcept());

			else
				getVue().getModuleInterne().addConceptFavoris(getConcept());

			favoris = !favoris;
			getVue().reloadListe();
			getVue().reload(getVue().getModuleInterne().getListeTotale());
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static final String GUI_FAV = "star.png";
	/**
	 * 
	 */
	private static final String GUI_EMPTY_FAV = "emptystar.png";
	/**
	 * 
	 */
	private Concept pic;

	/**
	 * 
	 */
	private JCheckBox valid;
	/**
	 * 
	 */
	private boolean favoris;
	/**
	 * 
	 */
	private VueCoursSelection vc;
	/**
	 * 
	 */
	private JImagePanel favButton;

	/**
	 * 
	 * @param vc
	 * @param pic
	 */
	public PanelSelectionConcept(VueCoursSelection vc, Concept pic) {
		super.setLayout(new GridLayout(0, 2));
		this.valid = new JCheckBox();
		this.valid.addChangeListener(new SelectionListener());

		this.vc = vc;
		this.pic = pic;
		this.favoris = getVue().getModuleInterne().getProfilListeConcept()
				.getFavoris().contains(getConcept());
		load();
	}

	/**
	 * 
	 */
	public void load() {
		this.setLayout(new GridLayout(0, 2));
		JPanel pan = new JPanel();
		pan.add(this.valid);
		JLabel jl = new JLabel(this.getConcept().getNomConcept());
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jl.addMouseListener(new SelectionClicListener());
		pan.add(jl);
		this.add(pan);

		try {

			if (favoris)
				favButton = new JImagePanel(ImageIO.read(new FileInputStream(
						UtilisationFichier.chargerGui(GUI_FAV))));
			else
				favButton = new JImagePanel(ImageIO.read(new FileInputStream(
						UtilisationFichier.chargerGui(GUI_EMPTY_FAV))));

			favButton.addMouseListener(new FavorisClicListener());
			this.add(favButton);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return le pictogramme du panel
	 */
	public Concept getConcept() {
		return this.pic;
	}

	/**
	 * @return la vue de sélection des pictogrammes pour une session de cours.
	 */
	public VueCoursSelection getVue() {
		return this.vc;
	}

	/**
	 * 
	 */
	public void mettreSelectionner() {
		this.valid.setSelected(true);
	}

}
