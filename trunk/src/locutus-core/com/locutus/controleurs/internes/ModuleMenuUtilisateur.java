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

 **/
package com.locutus.controleurs.internes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.cours.ModuleCoursSelection;
import com.locutus.exceptions.modeles.UtilisateurException;
import com.locutus.modeles.Cadre;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.cadres.LampeRouge;

/**
 * 
 * @author BEUGNON Sebastien
 * @version 1.0
 * 
 */
public abstract class ModuleMenuUtilisateur extends ModuleInterne {

	/**
	 * Le module de menu principal qui a généré ce module de menu utilisateur
	 */
	private ModuleMenuPrincipal mmp;
	/**
	 * L'utilisateur courant du module
	 */
	private Utilisateur utilisateurcourant;

	/**
	 * 
	 * @param mp
	 *            le module principal du programme
	 * @param mmp
	 *            le module du menu principal
	 * @param ut
	 *            l'utilisateur sélectionné
	 */
	public ModuleMenuUtilisateur(ModulePrincipal mp, ModuleMenuPrincipal mmp,
			Utilisateur ut) {
		super(mp);
		this.utilisateurcourant = ut;
		this.mmp = mmp;
	}

	@Override
	public abstract void init();

	/**
	 * Chargement du Module de Cours avec les typeRessource de te, et te2.
	 * 
	 * @param te
	 *            le type de la ressource en premier plan (Uniquement
	 *            images/photograhies/pictogrammes)
	 * 
	 * @param te2
	 *            le type de la ressource en deuxième plant(Uniquement
	 *            images/photographies/pictogrammes)
	 */
	public void chargerModuleCours(TypeRessource te, TypeRessource te2) {
		super.getModulePrincipal().changerModuleCourant(
				new ModuleCoursSelection(super.getModulePrincipal(), this, te,
						te2));
	}

	/**
	 * Accesseur à l'utilisateur courant.
	 * 
	 * @return l'utilisateur courant.
	 */
	public Utilisateur getUtilisateur() {
		return this.utilisateurcourant;
	}

	/**
	 * Accesseur au menu principal.
	 * 
	 * @return le module de menu principal.
	 */
	public ModuleMenuPrincipal getModuleMenuPrincipal() {
		return this.mmp;
	}

	/**
	 * 
	 * @param ou
	 *            les nouvelles OptionsUtilisateur de l'utilisateur courant.
	 * @return true si la modification des options de l'utilisateur s'est bien
	 *         passée, sinon false.
	 */
	public boolean lancerModificationUtilisateur(OptionsUtilisateur ou) {
		if (ou != null) {

			Iterator<Cadre> it = this.getUtilisateur().getOptionsUtilisateur()
					.getCadres().iterator();
			while (it.hasNext())
				ou.getCadres().add(it.next());

			this.getUtilisateur().setOptionsUtilisateur(ou);

		}
		try {
			UtilisationFichier.modUtilisateur(this.getUtilisateur());
			return true;
		} catch (UtilisateurException e) {

			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * @param voix
	 *            la voix que l'on doit mettre à l'utilisateur.
	 * @return true si la modification s'est bien réalisée, sinon false.
	 */
	public boolean lancerModificationVoix(Voix voix) {
		if (voix != null) {
			this.getUtilisateur().setVoix(voix);
		}
		try {
			UtilisationFichier.modUtilisateur(this.getUtilisateur());
			return true;
		} catch (UtilisateurException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param te
	 *            le type de ressource que l'on rajoute.
	 * @param concept
	 *            le concept auquel la ressource est liée.
	 * @return true si la ressource a bien été ajouté pour l'utilisateur, sinon
	 *         false.
	 */
	public boolean addRessource(TypeRessource te, Concept concept) {
		File fl = VueMessages.choisirFichier(te);
		if (fl != null && fl.exists()) {
			String message;

			if (te == TypeRessource.sons)
				message = "Voulez-vous choisir ce son pour \""
						+ concept.getNomConcept() + "\" de "
						+ this.getUtilisateur().getPrenom() + " ?";
			else if (te == TypeRessource.images)
				message = "Voulez-vous choisir cette image pour \""
						+ concept.getNomConcept() + "\" de "
						+ this.getUtilisateur().getPrenom() + " ?";
			else if (te == TypeRessource.photographies)
				message = "Voulez-vous choisir cette photographie pour \""
						+ concept.getNomConcept() + "\" de "
						+ this.getUtilisateur().getPrenom() + " ?";
			else if (te == TypeRessource.pictogrammes)
				message = "Voulez-vous choisir ce pictogramme pour \""
						+ concept.getNomConcept() + "\" de "
						+ this.getUtilisateur().getPrenom() + " ?";
			else
				message = "message vide pour les nouveax types de ressources non implémentes "
						+ "(Développeur : A modifier dans ModuleMenuUtilisateur:SupRessource)";

			int retour = VueMessages.afficherQuestionDansVue(getVue(), message);

			if (retour == VueMessages.YES_OPTION) {
				UtilisationFichier.addRessourceUtilisateur(te, concept,
						getUtilisateur(), fl);
				return true;
			}

		}
		return false;
	}

	/**
	 * @param te
	 *            le type de ressource que l'on modifie.
	 * @param concept
	 *            le concept auquel la ressource est liée.
	 * @return true si la ressource a bien été modifié pour l'utilisateur, sinon
	 *         false.
	 */
	public boolean modRessource(TypeRessource te, Concept concept) {
		return addRessource(te, concept);
	}

	/**
	 * 
	 * @param te
	 *            le type de ressource que l'on supprime.
	 * @param concept
	 *            le concept auquel la ressource est liée.
	 * @return true si la ressource a bien été supprimé pour l'utilisateur,
	 *         sinon false.
	 */
	public boolean supRessource(TypeRessource te, Concept concept) {

		String message;
		if (te == TypeRessource.sons)
			message = "Voulez-vous supprimer le son pour \""
					+ concept.getNomConcept() + "\" de "
					+ this.getUtilisateur().getPrenom() + " ?";
		else if (te == TypeRessource.images)
			message = "Voulez-vous supprimer l'image pour \""
					+ concept.getNomConcept() + "\" de "
					+ this.getUtilisateur().getPrenom() + " ?";
		else if (te == TypeRessource.photographies)
			message = "Voulez-vous supprimer la photographie pour \""
					+ concept.getNomConcept() + "\" de "
					+ this.getUtilisateur().getPrenom() + " ?";
		else if (te == TypeRessource.pictogrammes)
			message = "Voulez-vous supprimer le pictogramme pour \""
					+ concept.getNomConcept() + "\" de "
					+ this.getUtilisateur().getPrenom() + " ?";
		else
			message = "message vide pour les nouveax types de ressources non implémentes "
					+ "(Développeur : A modifier dans ModuleMenuUtilisateur:supRessource)";

		int retour = VueMessages.afficherQuestionDansVue(getVue(), message);

		if (retour == VueMessages.YES_OPTION) {
			return UtilisationFichier.supRessourceUtilisateur(te, concept,
					getUtilisateur());
		}
		return false;
	}

	/**
	 * 
	 * @param cadre
	 */
	public void lancerModificationCadre(Cadre cadre) {
		try {
			if (getUtilisateur().getOptionsUtilisateur().getCadres()
					.contains(cadre)) {

				getUtilisateur().getOptionsUtilisateur().getCadres()
						.remove(cadre);
				if (cadre.toString().equals(new LampeRouge().toString())) {
					getUtilisateur()
							.getOptionsUtilisateur()
							.getCadres()
							.add(getUtilisateur().getOptionsUtilisateur()
									.getCadres().size(), cadre);
				} else {
					getUtilisateur().getOptionsUtilisateur().getCadres()
							.add(0, cadre);
				}

			}
			UtilisationFichier.modUtilisateur(getUtilisateur());

		} catch (UtilisateurException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param cadre
	 */
	public void lancerModificationRetraitCadre(Cadre cadre) {

		if (getUtilisateur().getOptionsUtilisateur().getCadres()
				.contains(cadre)) {
			getUtilisateur().getOptionsUtilisateur().getCadres().remove(cadre);
			try {
				UtilisationFichier.modUtilisateur(getUtilisateur());
			} catch (UtilisateurException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param cadre
	 */
	public void lancerModificationAjoutCadre(Cadre cadre) {
		if (!getUtilisateur().getOptionsUtilisateur().getCadres()
				.contains(cadre)) {

			try {
				if (!cadre.toString().equals(new LampeRouge().toString())
						&& getUtilisateur().getOptionsUtilisateur().getCadres()
								.contains(new LampeRouge())) {
					System.out.println("existe cadre LpR");
					getUtilisateur().getOptionsUtilisateur().getCadres()
							.add(0, cadre);
				} else {
					getUtilisateur().getOptionsUtilisateur().addCadre(cadre);
				}

				UtilisationFichier.modUtilisateur(getUtilisateur());
				VueMessages.afficherMessageDansVue(getVue(),
						"Ajout de cadre réussi.");
			} catch (UtilisateurException e) {
				e.printStackTrace();
			}
		} else {
			VueMessages.afficherMessageDansVue(getVue(),
					"Un cadre de ce type est déjà existant !");
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
