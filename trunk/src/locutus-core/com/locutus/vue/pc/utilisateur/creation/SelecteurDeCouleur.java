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

import javax.swing.JComboBox;

/**
 * @author Sebastien Beugnon
 * 
 */
public class SelecteurDeCouleur extends JComboBox<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] tabCouleur = { "Rose", "Jaune", "Cyan", "Vert", "Noir" };

	/**
	 * 
	 */
	public SelecteurDeCouleur() {
		this.addItems(tabCouleur);
	}

	/**
	 * @param couleur
	 */
	public SelecteurDeCouleur(Color couleur) {

		this.addItems(tabCouleur);

		if (couleur.equals(Color.pink))
			this.setSelectedItem(tabCouleur[0]);
		else if (couleur.equals(Color.yellow))
			this.setSelectedItem(tabCouleur[1]);
		else if (couleur.equals(Color.cyan))
			this.setSelectedItem(tabCouleur[2]);
		else if (couleur.equals(Color.green))
			this.setSelectedItem(tabCouleur[3]);
		else
			this.setSelectedItem(tabCouleur[4]);

	}

	private void addItems(String[] items) {
		for (int i = 0; i < items.length; i++)
			this.addItem(items[i]);
	}

	/**
	 * @return une instance Color liée à la sélection.
	 */
	public Color getCouleur() {
		switch ((String) this.getSelectedItem()) {
		case "Rose":
			return Color.pink;
		case "Jaune":
			return Color.yellow;
		case "Cyan":
			return Color.cyan;
		case "Vert":
			return Color.green;
		case "Noir":
			return Color.black;
		default:
			return Color.gray;
		}
	}

}
