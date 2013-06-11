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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Sebastien Beugnon
 * 
 */
public class ClicPratiqueSouris extends MouseAdapter {
	private PanelDessinPratique pdp;
	private VuePratiqueSouris vps;
	private Timer time;

	/**
	 * @param pdp
	 * @param vps
	 */
	public ClicPratiqueSouris(PanelDessinPratique pdp, VuePratiqueSouris vps) {
		this.pdp = pdp;
		this.vps = vps;
		time = null;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (getVue().getModuleInterne().getModulePratiqueSelection()
				.getUtilisateur().getOptionsUtilisateur().getBipDeChangement())
			getVue().getModuleInterne().jouerElementSonore();

		getPanel().afficherCadres();

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		getPanel().effacerCadres();
	}

	@Override
	public void mousePressed(final MouseEvent arg0) {
		if (time == null) {
			time = new Timer();
			time.schedule(new TimerTask() {
				@Override
				public void run() {

					if (arg0.getClickCount() == 1) {
						getVue().getModuleInterne().selectionConcept(
								getPanel().getNode());
						getVue().setVisible(true);
					}
				}
			}, 0);
		}
	}

	private PanelDessinPratique getPanel() {
		return this.pdp;
	}

	private VuePratiqueSouris getVue() {
		return this.vps;
	}

}
