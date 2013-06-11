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

package com.locutus.vue.pc.utilisateur.creation;

import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.OptionsUtilisateur;

/**
 * @author Sebastien Beugnon
 * 
 */
public class SelecteurDeDuree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSlider js;
	private JLabel jl;

	/**
	 * 
	 */
	public SelecteurDeDuree() {
		jl = new JLabel("Temps de défilement : ");

		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.add(jl);
		js = new JSlider(JSlider.HORIZONTAL, 2000, 12000, 2000);
		js.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jl = new JLabel("" + getValue() + "ms");
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		js.addChangeListener(new ChangeValue());
		js.setMajorTickSpacing(1000);
		js.setMinorTickSpacing(100);
		js.setSnapToTicks(true);
		this.add(js);
		this.add(jl);
	}

	/**
	 * @param tmp
	 */
	public SelecteurDeDuree(float tmp) {
		jl = new JLabel("Temps de défilement : ");

		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.add(jl);
		if (tmp < OptionsUtilisateur.TMPMIN || tmp > OptionsUtilisateur.TMPMAX) {
			tmp = OptionsUtilisateur.TMPMIN;
		}
		js = new JSlider(JSlider.HORIZONTAL, (int) OptionsUtilisateur.TMPMIN,
				(int) OptionsUtilisateur.TMPMAX, (int) (tmp));
		js.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jl = new JLabel(getValue() + "ms");
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		js.addChangeListener(new ChangeValue());
		js.setMajorTickSpacing(1000);
		js.setMinorTickSpacing(100);
		js.setSnapToTicks(true);
		this.add(js);
		this.add(jl);
	}

	/**
	 * @return le temps sélectionné.
	 */
	public float getValue() {
		return (float) (((float) js.getValue()));
	}

	/**
	 * @param a
	 */
	public void setValue(int a) {
		js.setValue(a);
	}

	class ChangeValue implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			jl.setText(getValue() + "ms");
		}

	}

	/**
	 * 
	 */
	@Override
	public void addMouseListener(MouseListener ml) {
		this.js.addMouseListener(ml);
	}
}
