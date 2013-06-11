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
package com.locutus.vue.pc.cours;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.locutus.controleurs.internes.cours.ModuleCours;
import com.locutus.controleurs.internes.cours.ModuleCoursDefilement;
import com.locutus.controleurs.internes.cours.ModuleCoursSouris;
import com.locutus.modeles.Cadre;
import com.locutus.modeles.Concept;
import com.locutus.vue.pc.Dessinable;

/**
 * 
 * @author Beugnon
 * 
 */
public class PanelDessinCours extends Dessinable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private BufferedImage[] imgs;
	/**
	 * 
	 */
	private ModuleCours ts;

	/**
	 * 
	 */
	private Concept concept;

	/**
	 * 
	 * @param i
	 * @param defilement
	 * @param jf
	 * @param concept2
	 */
	public PanelDessinCours(int i, ModuleCoursDefilement jf, Concept concept2) {
		setBackground(null);
		setForeground(null);
		this.imgs = new BufferedImage[i];
		this.ts = jf;
		this.concept = concept2;
		this.imgs[0] = jf.getPictogrammesExo1(getConcept());
		if (this.imgs.length == 2)
			this.imgs[1] = jf.getPictogrammesExo2(getConcept());
	}

	/**
	 * @param jf
	 * @param cpt
	 */
	public PanelDessinCours(ModuleCoursSouris jf, Concept cpt) {
		setBackground(null);
		setForeground(null);
		this.imgs = new BufferedImage[1];
		this.ts = jf;
		this.concept = cpt;
		this.imgs[0] = jf.getImage1(getConcept());

	}

	/**
	 * 
	 * @return le concept dessiné.
	 */
	public Concept getConcept() {
		return this.concept;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		boolean paint = false;
		if (this.imgs != null) {
			paint = true;
		}
		if (paint) {
			for (int i = 0; i < this.imgs.length; i++) {
				if (this.imgs[i] != null) {
					int x = (this.getWidth() - this.imgs[i].getWidth()) / 2;
					int y;
					if (i == 0)
						y = 100;
					else
						y = 100 + this.imgs[0].getHeight();

					g2d.drawImage(this.imgs[i], x, y, this.imgs[i].getWidth(),
							this.imgs[i].getHeight(), this);
				}
			}
		}
	}

	@Override
	public void repaint() {
		super.repaint();

		Graphics2D g = (Graphics2D) this.getGraphics();
		boolean repaint = false;
		if (this.imgs != null) {
			repaint = true;
		}

		if (repaint && g != null) {
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			for (int i = 0; i < this.imgs.length; i++) {
				if (this.imgs[i] != null) {
					int x = (this.getWidth() - this.imgs[i].getWidth()) / 2;
					int y;
					if (i == 0)
						y = 100;
					else
						y = 100 + this.imgs[0].getHeight();

					g.drawImage(this.imgs[i], x, y, this.imgs[i].getWidth(),
							this.imgs[i].getHeight(), this);
				}
			}
		}

	}

	/**
	 * 
	 */
	public void chargerCadres() {
		Iterator<Cadre> it = ts.getModuleCoursSelection().getUtilisateur()
				.getOptionsUtilisateur().getCadres().iterator();
		while (it.hasNext()) {
			it.next().dessinerCadre(this);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public void retirerCadre() {
		Iterator<Cadre> it = ts.getModuleCoursSelection().getUtilisateur()
				.getOptionsUtilisateur().getCadres().iterator();
		while (it.hasNext()) {
			it.next().effacerCadre(this);
		}

	}

	/**
	 * 
	 * @return true si on peut dessiner un cadre, sinon false.
	 */
	@Override
	public boolean peutMettreUnCadre() {
		return this.imgs[0] != null;
	}

	/**
	 * 
	 * @param i
	 * @return une instance BufferedImage d'une image lié au concept à la
	 *         position i.
	 */
	@Override
	public BufferedImage getImage(int i) {
		if (i < this.imgs.length)
			return this.imgs[i];
		else
			return null;
	}

}
