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
public class ClicPratiqueDefilement extends MouseAdapter {

	private VuePratiqueDefilement vdp;

	private volatile Timer time;

	/**
	 * @param vpd
	 */
	public ClicPratiqueDefilement(VuePratiqueDefilement vpd) {
		this.vdp = vpd;
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
								getVue().getTab()[getVue().getModuleInterne()
										.getPositionCurseur()].getNode());
					}
					time = null;
				}
			}, 0);

		}

	}

	/**
	 * @return
	 */
	private VuePratiqueDefilement getVue() {
		return this.vdp;
	}

}
