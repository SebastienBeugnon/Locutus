/**
 * Copyright (C) 2013
	S�bastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
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

import javax.swing.JComboBox;

import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.TypeGenre;

/**
 * @author Sebastien Beugnon
 * 
 */
public class SelecteurDeGenre extends JComboBox<TypeGenre> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SelecteurDeGenre() {
		for (TypeGenre t : TypeGenre.values())
			this.addItem(t);
		super.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
	}

	/**
	 * @param te
	 */
	public SelecteurDeGenre(TypeGenre te) {
		this();
		super.setSelectedItem(te);
	}

	/**
	 * @return le genre choisi.
	 */
	public TypeGenre getGenre() {
		return (TypeGenre) super.getSelectedItem();
	}
}
