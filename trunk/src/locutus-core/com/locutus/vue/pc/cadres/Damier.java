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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.locutus.vue.pc.Dessinable;

/**
 * @author Sebastien Beugnon
 * 
 */
public class Damier extends CadreClassique {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Damier() {
		super(Color.black);
	}

	/**
	 * @param ep
	 */
	public Damier(int ep) {
		super(Color.black, ep);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#dessinerCadre(com.vue.pc.Dessinable)
	 */
	@Override
	public void dessinerCadre(Dessinable pan) {

		if (pan.getImage(1) == null) {

			int x = (pan.getWidth() - pan.getImage(0).getWidth()) / 2;
			int y = 100;

			int xf = pan.getImage(0).getWidth();
			int yf = pan.getImage(0).getHeight();

			if (pan.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) pan.getGraphics();
				if (g2d != null) {
					super.dessinerCadre(pan);
					int k = 3 * (xf + 2 * getEpaisseur()) / getEpaisseur();
					int z = 3 * (yf + 2 * getEpaisseur()) / getEpaisseur();

					// HORIZONTALES
					for (int i = 0; i <= k; i++) {

						if (i % 2 != 0) {

							g2d.setPaint(Color.white);

							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur(), getEpaisseur() / 2,
									getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur() + getEpaisseur() / 2,
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf,
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf
									+ getEpaisseur() / 2, getEpaisseur() / 2,
									getEpaisseur() / 2));

						} else {

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur(), getEpaisseur() / 2,
									getEpaisseur() / 2));

							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur() + getEpaisseur() / 2,
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf,
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf
									+ getEpaisseur() / 2, getEpaisseur() / 2,
									getEpaisseur() / 2));
						}
					}

					// VERTICALE
					for (int i = 0; i <= z; i++) {

						if (i % 2 != 0) {
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur(),
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ getEpaisseur() / 2, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x + xf, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x + xf + getEpaisseur() / 2,
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

						} else {

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur(),
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ getEpaisseur() / 2, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x + xf, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x + xf + getEpaisseur() / 2,
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
						}
					}

					g2d.setPaint(pan.getBackground());
					g2d.fillRect(x - getEpaisseur() * 2, yf + y
							+ getEpaisseur(), x + xf + 4 * getEpaisseur(),
							pan.getHeight());
					g2d.fillRect(x - getEpaisseur() * 2, yf - getEpaisseur()
							* 2, getEpaisseur(), y + yf + getEpaisseur());

					g2d.fillRect(xf + x + getEpaisseur(), y - getEpaisseur(),
							pan.getWidth(), y + yf + getEpaisseur());
				}

			}
		} else {

			int x;
			int y;
			int width;

			if (pan.getImage(0).getWidth() > pan.getImage(1).getWidth()) {

				x = (pan.getWidth() - pan.getImage(0).getWidth()) / 2;
				y = 100;
				width = pan.getImage(0).getWidth();

			} else {

				x = (pan.getWidth() - pan.getImage(1).getWidth()) / 2;
				y = 100;
				width = pan.getImage(1).getWidth();
			}

			int xf = width;
			int yf = pan.getImage(0).getHeight() + pan.getImage(1).getHeight();

			if (pan.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) pan.getGraphics();
				if (g2d != null) {
					super.dessinerCadre(pan);
					int k = 3 * (xf + 2 * getEpaisseur()) / getEpaisseur();
					int z = 3 * (yf + 2 * getEpaisseur()) / getEpaisseur();

					for (int i = 0; i <= k; i++) {

						if (i % 2 != 0) {
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur(), getEpaisseur() / 2,
									getEpaisseur() / 2));
							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur() + getEpaisseur() / 2,
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf,
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf
									+ getEpaisseur() / 2, getEpaisseur() / 2,
									getEpaisseur() / 2));

						} else {
							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur(), getEpaisseur() / 2,
									getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y
									- getEpaisseur() + getEpaisseur() / 2,
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf,
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ (getEpaisseur() / 2 * i), y + yf
									+ getEpaisseur() / 2, getEpaisseur() / 2,
									getEpaisseur() / 2));
						}
					}

					for (int i = 0; i <= z; i++) {
						if (i % 2 != 0) {
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur(),
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ getEpaisseur() / 2, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x + xf, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x + xf + getEpaisseur() / 2,
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

						} else {

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x - getEpaisseur(),
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x - getEpaisseur()
									+ getEpaisseur() / 2, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));

							g2d.setPaint(Color.black);
							g2d.fill(new Rectangle(x + xf, y - getEpaisseur()
									+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
							g2d.setPaint(Color.white);
							g2d.fill(new Rectangle(x + xf + getEpaisseur() / 2,
									y - getEpaisseur()
											+ (getEpaisseur() / 2 * i),
									getEpaisseur() / 2, getEpaisseur() / 2));
						}
					}

					g2d.setPaint(pan.getBackground());
					g2d.fillRect(x - getEpaisseur() * 2, yf + y
							+ getEpaisseur(), x + xf + 4 * getEpaisseur(),
							pan.getHeight());
					g2d.fillRect(x - getEpaisseur() * 2, yf - getEpaisseur()
							* 2, getEpaisseur(), y + yf + getEpaisseur());
					g2d.fillRect(xf + x + getEpaisseur(), y - getEpaisseur(),
							pan.getWidth(), y + yf + getEpaisseur());
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#effacerCadre(com.vue.pc.Dessinable)
	 */
	@Override
	public void effacerCadre(Dessinable pan) {
		pan.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#toString()
	 */
	@Override
	public String toString() {
		return "Damier";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#aDeLaCouleur()
	 */
	@Override
	public boolean aDeLaCouleur() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#aDeLaTransparence()
	 */
	@Override
	public boolean aDeLaTransparence() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

}
