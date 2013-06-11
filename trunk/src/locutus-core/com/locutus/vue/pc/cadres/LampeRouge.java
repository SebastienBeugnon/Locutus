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
package com.locutus.vue.pc.cadres;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.locutus.modeles.Cadre;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.Dessinable;

/**
 * @author Sebastien Beugnon
 * 
 */
public class LampeRouge extends Cadre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String RedLampOn = "redLampOn.png";

	@Override
	public void dessinerCadre(Dessinable pan) {
		// JE PLACE UNE IMAGE AVEC UNE LAMPE ROUGE EN HAUT
		try {
			BufferedImage on = ImageIO.read(UtilisationFichier
					.chargerGui(RedLampOn));

			int width = pan.getBounds().width;
			if (pan.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) pan.getGraphics();
				if (g2d != null) {
					g2d.drawImage(on, width / 2, 10, on.getWidth(),
							on.getHeight(), pan);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void effacerCadre(Dessinable pan) {

		// JE PLACE UNE IMAGE AVEC UNE LAMPE ETEINTE EN HAUT
		/*
		 * try { BufferedImage off = ImageIO.read(new File(RedLampOff)); //
		 * pan.repaint();
		 * 
		 * int x = pan.getBounds().x; int width = pan.getBounds().width; if
		 * (pan.peutMettreUnCadre()) { Graphics2D g2d = (Graphics2D)
		 * pan.getGraphics(); if (g2d != null) { g2d.drawImage(off, width / 2,
		 * 10, off.getWidth(), off.getHeight(), pan); } }
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
		pan.repaint();
	}

	public String toString() {
		return "Lampe rouge";
	}

	@Override
	public boolean aDeLaCouleur() {

		return false;
	}

	@Override
	public boolean aDeLaTransparence() {
		return false;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

}
