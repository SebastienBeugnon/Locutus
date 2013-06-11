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
package com.locutus.vue.pc;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Sebastien Beugnon
 * 
 */
public final class JImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image = null;
	private boolean stretch = false;

	/**
	 * Constructeur
	 * 
	 * @param image
	 *            image à afficher
	 */
	public JImagePanel(Image image) {
		this.image = image;
	}

	/**
	 * Position de l'image sur le panel
	 * 
	 * @param stretch
	 *            true: etirer l'image / false: centrer l'image
	 */
	public void setStretch(Boolean stretch) {
		this.stretch = stretch;
	}

	/**
	 * Surcharger le dessin du composant
	 * 
	 * @param g
	 *            canvas
	 */
	protected void paintComponent(Graphics g) {
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;

		if (this.stretch) {
			width = this.getWidth();
			height = this.getHeight();
		} else {
			width = this.image.getWidth(this);
			height = this.image.getHeight(this);
			x = ((this.getWidth() - width) / 2);
			y = ((this.getHeight() - height) / 2);
		}
		g.drawImage(this.image, x, y, width, height, this);
	}
}
