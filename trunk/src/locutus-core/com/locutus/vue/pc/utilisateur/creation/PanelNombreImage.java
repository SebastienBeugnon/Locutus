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

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.OptionsUtilisateur;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelNombreImage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JComboBox<String> liste;

	/**
	 * 
	 */
	public PanelNombreImage() {
		JLabel title = new JLabel("Nombre d'images par défilement : ");
		title.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.add(title);
		this.liste = new JComboBox<String>();
		this.getBox().setFont(
				OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		for (int i = OptionsUtilisateur.NBIMGMINI; i <= OptionsUtilisateur.NBIMGMAX; i++) {
			this.getBox().addItem("" + i);
		}
		this.add(this.getBox());
	}

	/**
	 * @param nbImgDefilement
	 */
	public PanelNombreImage(int nbImgDefilement) {
		JLabel title = new JLabel("Nombre d'images par défilement : ");
		title.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.add(title);
		this.liste = new JComboBox<String>();
		this.getBox().setFont(
				OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		for (int i = OptionsUtilisateur.NBIMGMINI; i <= OptionsUtilisateur.NBIMGMAX; i++) {
			this.getBox().addItem("" + i);
		}
		this.add(this.getBox());
		this.getBox().setSelectedIndex(nbImgDefilement - 1);
	}

	/**
	 * 
	 * @return le nombre d'images choisi par l'utilisateur
	 */
	public int getNbImg() {
		return Integer.parseInt((String) this.getBox().getSelectedItem());
	}

	/**
	 * 
	 * @param i
	 */
	public void setValue(int i) {
		this.getBox().setSelectedIndex(i - 1);
	}

	/**
	 * @return une référence du JComboBox pour y ajouter un écouteur.
	 */
	private JComboBox<String> getBox() {
		return this.liste;
	}

	/**
	 * @param al
	 */
	public void addActionListener(ActionListener al){
		this.getBox().addActionListener(al);
	}
}
