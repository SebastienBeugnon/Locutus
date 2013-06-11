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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class ClicCoursSouris extends MouseAdapter {
	/**
	 * 
	 */
	private PanelDessinCours pand;
	/**
	 * 
	 */
	private VueCoursSouris vcs;

	/**
	 * 
	 * @param vcs
	 * @param pand
	 */
	public ClicCoursSouris(VueCoursSouris vcs, PanelDessinCours pand) {
		this.vcs = vcs;
		this.pand = pand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.pand.chargerCadres();
		if (vcs.getModuleInterne().getModuleCoursSelection().getUtilisateur()
				.getOptionsUtilisateur().getBipDeChangement())
			vcs.getModuleInterne().jouerElementSonore();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		this.pand.retirerCadre();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		vcs.getModuleInterne().getGestionnaireSon()
				.jouerSonConcept(this.pand.getConcept());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}

	@Override
	public String toString() {
		return "ClicCoursSouris";
	}
}
