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
package com.locutus.vue.pc.pratique;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import com.locutus.modeles.Cadre;
import com.locutus.modeles.Concept;
import com.locutus.modeles.graphes.Node;
import com.locutus.vue.pc.Dessinable;

/**
 * @author Sebastien Beugnon
 * 
 * 
 */
public class PanelDessinPratique extends Dessinable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private VuePratique vps;

	/**
	 * 
	 */
	private Node<Concept> node;

	/**
	 * 
	 */
	private BufferedImage img;

	/**
	 * @param vuePratique
	 * @param node2
	 * @param b
	 */
	public PanelDessinPratique(VuePratique vuePratique, Node<Concept> node2) {
		this.vps = vuePratique;
		this.node = node2;
		this.img = getVue().getModuleInterne().getImage(getNode());

	}

	/**
	 * 
	 */
	public void effacerCadres() {
		List<Cadre> list = getVue().getModuleInterne()
				.getModulePratiqueSelection().getUtilisateur()
				.getOptionsUtilisateur().getCadres();
		Iterator<Cadre> it = list.iterator();
		while (it.hasNext()) {
			Cadre local = it.next();
			local.effacerCadre(this);
		}

	}

	/**
	 * 
	 */
	public void afficherCadres() {
		List<Cadre> list = getVue().getModuleInterne()
				.getModulePratiqueSelection().getUtilisateur()
				.getOptionsUtilisateur().getCadres();
		Iterator<Cadre> it = list.iterator();
		while (it.hasNext()) {
			Cadre local = it.next();
			local.dessinerCadre(this);
	
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		boolean paint = false;

		if (this.img != null) {
			paint = true;
		}
		if (paint) {
			int x = (this.getWidth() - this.img.getWidth()) / 2;
			int y = 100;
			g2d.drawImage(this.img, x, y, this.img.getWidth(),
					this.img.getHeight(), this);
		}

	}

	@Override
	public void repaint() {
		super.repaint();
		Graphics2D g = (Graphics2D) this.getGraphics();
		boolean repaint = false;
		if (this.img != null) {
			repaint = true;
		}

		if (repaint && g != null) {

			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			int x = (this.getWidth() - this.img.getWidth()) / 2;
			int y = 100;
			g.drawImage(this.img, x, y, this.img.getWidth(),
					this.img.getHeight(), this);
		}

	}

	/**
	 * 
	 * @return le noeud de concept lié à ce panel.
	 */
	public Node<Concept> getNode() {
		return this.node;
	}

	/**
	 * 
	 * @return
	 */
	private VuePratique getVue() {
		return this.vps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.Drawing#peutMettreUnCadre()
	 */
	@Override
	public boolean peutMettreUnCadre() {
		return this.img != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.Drawing#getImage(int)
	 */
	@Override
	public BufferedImage getImage(int i) {
		if (i == 0)
			return this.img;
		else
			return null;
	}

}
