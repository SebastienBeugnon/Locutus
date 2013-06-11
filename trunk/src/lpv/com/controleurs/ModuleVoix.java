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
package com.controleurs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.exceptions.FichierVoixInvalideException;
import com.locutus.modeles.Concept;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.vue.pc.VueVoix;

/**
 * 
 * @author Beugnon
 * 
 */
public class ModuleVoix extends ModuleInterne {

	/**
	 * 
	 * @param mp
	 */
	public ModuleVoix(ModulePrincipal mp) {
		super(mp);
		init();
	}

	@Override
	public void init() {
		setVue(new VueVoix(this));
	}

	/**
	 * 
	 * @param voix
	 * @param concept
	 */
	public void lancerModificationSon(Voix voix, Concept concept) {
		File fl = VueMessages.choisirFichier(TypeRessource.sons);
		if (fl != null) {
			int retour = VueMessages.afficherQuestionDansVue(
					getVue(),
					"Voulez-vous choisir ce son pour \""
							+ concept.getNomConcept() + "\" ?");
			if (retour == VueMessages.YES_OPTION) {
				try {
					UtilisationFichier.addRessourceSonParDefaut(voix, concept,
							fl);
				} catch (IOException e) {
					e.printStackTrace();
				}
				getVue().chargerVueListeConcept();

			}
		}
	}

	/**
	 * @param voix
	 * @param genre
	 */
	public void lancerModificationVoixGenre(Voix voix, TypeGenre genre) {
		try {
			UtilisationFichier.modVoix(voix, genre);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getVue().chargerListeVoix();
		getVue().chargerVueListeConcept();
	}

	public VueVoix getVue() {
		return (VueVoix) super.getVue();
	}

	/**
	 * @param nomVoix
	 * @param genreVoix
	 * @return true si et seulement si la création s'est bien déroulée.
	 */
	public boolean lancerCreationVoix(String nomVoix, TypeGenre genreVoix) {
		if (!nomVoix.trim().equals("")) {

			List<Voix> vs;
			try {
				vs = UtilisationFichier.chargerListeVoix();
				if (!vs.contains(new Voix(nomVoix, genreVoix))) {

					try {
						UtilisationFichier
								.addVoix(new Voix(nomVoix, genreVoix));
						VueMessages.afficherMessageDansVue(getVue(),
								"Nouvelle voix ajoutée !");
						getVue().chargerListeVoix();
						getVue().chargerVueListeConcept();
						return true;
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				} else {

					VueMessages.afficherMessageDansVue(getVue(),
							"Voix déjà existante !");
					return false;
				}

			} catch (FichierVoixInvalideException e1) {
				e1.printStackTrace();
				VueMessages.afficherMessageDansVue(getVue(),
						"Problème à la lecture des voix.");

				return false;
			}

		} else {
			VueMessages.afficherMessageDansVue(getVue(),
					"Vous n'avez pas spécifié le nom de la voix !");
			return false;
		}

	}

	/**
	 * @param text
	 * @return une liste des concepts commençant par 'text'
	 */
	public List<Concept> rechercheParNom(String text) {
		if (text.trim().equals("")) {
			List<Concept> a = UtilisationFichier.chargerListeConcepts();
			a.remove(new Concept("origine", "origine (graphe)"));
			return a;
		}

		List<Concept> rst = new ArrayList<Concept>();

		Iterator<Concept> it = UtilisationFichier.chargerListeConcepts()
				.iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			int i = 0;
			while (i < text.length()
					&& text.length() <= local.getNomConcept().length()
					&& Character.toLowerCase(text.charAt(i)) == Character
							.toLowerCase(local.getNomConcept().charAt(i)))
				i++;

			if (i == text.length()
					&& !local
							.equals(new Concept("origine", "origine (graphe)")))
				rst.add(local);

		}
		return rst;
	}

}
