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
package com.locutus.vue.pc.modification;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.locutus.modeles.OptionsProgramme;

/**
 * @author Sebastien Beugnon
 * 
 */
public class TextFieldRechercheModification extends JTextField implements
		DocumentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelModification vs;

	/**
	 * @param text
	 * @param vs
	 */
	public TextFieldRechercheModification(String text, PanelModification vs) {
		super(text);
		this.vs=vs;
		this.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		this.addFocusListener(new Focus());
		this.getDocument().addDocumentListener(this);
	}

	/**
	 * @return la vue pour une sélection des pictogrammes pour une sesion cours.
	 */
	public PanelModification getPanel() {
		return this.vs;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		this.getPanel().rechargerListe(this.vs.getVue().getModuleInterne().rechercheParNom(this.getText()));
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		this.getPanel().rechargerListe(this.vs.getVue().getModuleInterne().rechercheParNom(this.getText()));
	}

	class Focus implements FocusListener {

		@Override
		public void focusGained(FocusEvent arg0) {
			setText("");
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
