package com.locutus.vue.pc.utilisateur.creation;

import javax.swing.JComboBox;

/**
 * @author Sebastien Beugnon
 * 
 */
public class SelecteurDeEpaisseur extends JComboBox<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SelecteurDeEpaisseur() {
		int i = 5;
		while (i < 60) {
			this.addItem("" + i);

			i += 5;
		}
	}

	/**
	 * @param ep
	 */
	public SelecteurDeEpaisseur(int ep) {
		this();
		setSelectedItem("" + ep);
	}

	/**
	 * @return l'épaisseur sélectionné.
	 */
	public int getEpaisseur() {
		return Integer.parseInt((String) getSelectedItem());
	}

}
