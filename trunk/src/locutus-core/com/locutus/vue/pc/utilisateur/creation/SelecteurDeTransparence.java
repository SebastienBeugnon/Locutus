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

import javax.swing.JComboBox;

/**
 * @author Sebastien Beugnon
 * 
 */
public class SelecteurDeTransparence extends JComboBox<Float> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SelecteurDeTransparence() {
		for (int i = 1; i < 11; i++) {
			Float fl;
			if (i != 10) {
				fl = new Float("0." + i + "F");
			} else {
				fl = new Float("1.0F");
			}

			this.addItem(fl);
		}
	}

	/**
	 * @param fl
	 */
	public SelecteurDeTransparence(float fl) {
		this();
		this.setSelectedItem(new Float(fl));
	}

	/**
	 * @return la transparence choisie.
	 */
	public float getTransparence() {
		Float fl = (Float) this.getSelectedItem();
		return fl.floatValue();
	}
}
