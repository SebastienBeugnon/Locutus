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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.locutus.modeles.Cadre;
import com.locutus.vue.pc.Dessinable;

/**
 * @author Sebastien Beugnon
 * 
 */
public class CadreClassique extends Cadre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Color couleur;

	/**
	 * 
	 */
	private int epaisseur;

	/**
	 * 
	 */
	public CadreClassique() {
		this.couleur = Color.black;
		this.epaisseur = 10;
	}

	/**
	 * 
	 * @param col
	 */
	public CadreClassique(Color col) {
		this.couleur = col;
		this.epaisseur = 10;
	}

	/**
	 * @param col
	 * @param ep
	 */
	public CadreClassique(Color col, int ep) {
		this.couleur = col;
		this.epaisseur = ep;
	}

	/**
	 * 
	 * @return une instance Color pour la couleur du cadre de CadreClassique.
	 */
	public Color getCouleur() {
		return this.couleur;
	}

	/**
	 * 
	 * @param color
	 */
	public void setCouleur(Color color) {
		this.couleur = color;
	}

	/**
	 * 
	 * @return l'épaisseur du cadre.
	 */
	public int getEpaisseur() {
		return this.epaisseur;
	}

	/**
	 * @param ep
	 * 
	 */
	public void setEpaisseur(int ep) {
		this.epaisseur = ep;
	}

	/**
	 * 
	 * @param alpha
	 * @return
	 */
	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	@Override
	public boolean aDeLaCouleur() {
		return true;
	}

	@Override
	public boolean aDeLaTransparence() {
		return false;
	}

	@Override
	public void dessinerCadre(Dessinable arg0) {
		if (arg0.getImage(1) == null) {

			int x = (arg0.getWidth() - arg0.getImage(0).getWidth()) / 2;
			int y = 100;

			int xf = arg0.getImage(0).getWidth();
			int yf = arg0.getImage(0).getHeight();

			if (arg0.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) arg0.getGraphics();
				if (g2d != null) {
					Composite originalComposite = g2d.getComposite();
					g2d.setPaint(getCouleur());
					g2d.setComposite(makeComposite(1F));
					g2d.fill(new Rectangle(x - getEpaisseur(), y
							- getEpaisseur(), xf + 2 * getEpaisseur(),
							getEpaisseur()));
					g2d.fill(new Rectangle(x - getEpaisseur(), y + yf, xf + 2
							* getEpaisseur(), getEpaisseur()));
					g2d.fill(new Rectangle(x - getEpaisseur(), y
							- getEpaisseur(), getEpaisseur(), yf + 2
							* getEpaisseur()));
					g2d.fill(new Rectangle(x + xf, y - getEpaisseur(),
							getEpaisseur(), yf + 2 * getEpaisseur()));

					g2d.setComposite(originalComposite);

				}
			}
		} else {
			int x;
			int y;
			int width;

			if (arg0.getImage(0).getWidth() > arg0.getImage(1).getWidth()) {
				x = (arg0.getWidth() - arg0.getImage(0).getWidth()) / 2;
				y = 100;
				width = arg0.getImage(0).getWidth();
			} else {
				x = (arg0.getWidth() - arg0.getImage(1).getWidth()) / 2;
				y = 100;
				width = arg0.getImage(1).getWidth();
			}

			int xf = x + width;
			int yf = y + arg0.getImage(0).getHeight()
					+ arg0.getImage(1).getHeight();

			if (arg0.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) arg0.getGraphics();
				if (g2d != null) {
					Composite originalComposite = g2d.getComposite();
					g2d.setPaint(getCouleur());
					g2d.setComposite(makeComposite(1F));

					g2d.fill(new Rectangle(x - getEpaisseur(), y
							- getEpaisseur(), xf + 2 * getEpaisseur(),
							getEpaisseur()));
					g2d.fill(new Rectangle(x - getEpaisseur(), y + yf, xf + 2
							* getEpaisseur(), getEpaisseur()));
					g2d.fill(new Rectangle(x - getEpaisseur(), y
							- getEpaisseur(), getEpaisseur(), yf + 2
							* getEpaisseur()));
					g2d.fill(new Rectangle(x + xf, y - getEpaisseur(),
							getEpaisseur(), yf + 2 * getEpaisseur()));

					g2d.setComposite(originalComposite);

				}
			}
		}

	}

	@Override
	public void effacerCadre(Dessinable arg0) {
		arg0.repaint();
	}

	@Override
	public String toString() {
		return "Cadre classique";
	}

}
