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
 * 
 * @author Sebastien Beugnon
 * 
 */
public class Surbrillance extends Cadre {

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
	private Float transparence;

	/**
	 * 
	 */
	public Surbrillance() {
		this.couleur = Color.pink;
		this.transparence = 0.5F;
	}

	/**
	 * 
	 * @param col
	 */
	public Surbrillance(Color col) {
		this.couleur = col;
		this.transparence = 0.5F;
	}

	/**
	 * 
	 * @param col
	 * @param trans
	 */
	public Surbrillance(Color col, Float trans) {
		this.couleur = col;
		this.transparence = trans;
	}

	/**
	 * 
	 * @return une instance Color pour la couleur du cadre de Subrillance.
	 */
	public Color getCouleur() {
		return this.couleur;
	}

	/**
	 * 
	 * @return un nombre flottant déterminant la transparence du cadre de
	 *         Surbrillance.
	 */
	public Float getTransparence() {
		return this.transparence;
	}

	/**
	 * 
	 * @param ta
	 */
	public void setTransparence(Float ta) {
		this.transparence = ta;
	}

	/**
	 * 
	 * @param col
	 */
	public void setCouleur(Color col) {
		this.couleur = col;
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
			if (pan.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) pan.getGraphics();
				if (g2d != null) {
					Composite originalComposite = g2d.getComposite();
					g2d.setPaint(getCouleur());
					g2d.setComposite(makeComposite(getTransparence()));

					g2d.fill(new Rectangle(x, y, pan.getImage(0).getWidth(),
							pan.getImage(0).getHeight()));
					g2d.setComposite(originalComposite);
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

			if (pan.peutMettreUnCadre()) {
				Graphics2D g2d = (Graphics2D) pan.getGraphics();
				if (g2d != null) {
					Composite originalComposite = g2d.getComposite();
					g2d.setPaint(getCouleur());
					g2d.setComposite(makeComposite(getTransparence()));
					g2d.fill(new Rectangle(x, y, width, 100
							+ pan.getImage(0).getHeight()
							+ pan.getImage(1).getHeight()));
					g2d.setComposite(originalComposite);
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
	public String toString() {
		return "Surbrillance de couleur";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#aDeLaCouleur()
	 */
	@Override
	public boolean aDeLaCouleur() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.modeles.Cadre#aDeLaTransparence()
	 */
	@Override
	public boolean aDeLaTransparence() {
		return true;
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
