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

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.exceptions.FichierVoixInvalideException;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.Voix;
import com.locutus.outils.fichiers.UtilisationFichier;

/**
 * 
 * @author Beugnon Sebastien
 * 
 */
public class SelecteurDeVoix extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JComboBox<Voix> jBox;

	/**
	 * 
	 */
	public SelecteurDeVoix() {
		List<Voix> liste;
		try {
			liste = UtilisationFichier.chargerListeVoix();
			Voix[] tab = new Voix[liste.size()];
			Iterator<Voix> it = liste.iterator();
			int k = 0;
			while (it.hasNext()) {
				tab[k] = it.next();
				k++;
			}
			JLabel title = new JLabel("Choix de la voix : ");
			title.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.add(title);
			this.jBox = new JComboBox<Voix>(tab);
			this.getBox().setFont(
					OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			this.add(this.getBox());
		} catch (FichierVoixInvalideException e) {

			JLabel title = new JLabel("Choix de la voix : ");
			title.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.add(title);
			this.jBox = new JComboBox<Voix>();
			this.getBox().setFont(
					OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			this.add(this.getBox());
		}

	}

	/**
	 * Constructeur a partir d'un tableau de voix
	 * 
	 * @param v
	 *            la voix a mettre par defaut
	 */
	public SelecteurDeVoix(Voix v) {
		List<Voix> liste;
		try {
			liste = UtilisationFichier.chargerListeVoix();
			Voix[] tab = new Voix[liste.size()];
			Iterator<Voix> it = liste.iterator();
			int k = 0;
			while (it.hasNext()) {
				tab[k] = it.next();
				k++;
			}

			JLabel title = new JLabel("Choix de la voix : ");
			title.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.add(title);
			this.jBox = new JComboBox<Voix>(tab);
			this.getBox().setFont(
					OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			this.getBox().setSelectedItem(v);
			this.add(this.getBox());
		} catch (FichierVoixInvalideException e) {
			JLabel title = new JLabel("Choix de la voix : ");
			title.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.add(title);
			this.jBox = new JComboBox<Voix>();
			this.getBox().setFont(
					OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			this.add(this.getBox());
		}

	}

	/**
	 * 
	 * @return la voix selectionnee par l'utilisateur
	 */
	public Voix getVoix() {
		return (Voix) this.getBox().getSelectedItem();
	}

	/**
	 * 
	 * @return une référence sur le JComboBox pour y ajouter un écouteur.
	 */
	private JComboBox<Voix> getBox() {
		return this.jBox;
	}

	/**
	 * @param al
	 */
	public void addActionListener(ActionListener al) {
		this.getBox().addActionListener(al);
	}

}
