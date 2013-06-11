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

package com.locutus.controleurs.internes.cours;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuUtilisateur;
import com.locutus.exceptions.DeplacementDansListeException;
import com.locutus.modeles.Concept;
import com.locutus.modeles.ProfilListeConcept;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.cours.VueCoursSelection;

/**
 * 
 * @author Sebastien Beugnon
 * @version 1.0
 * 
 */
public class ModuleCoursSelection extends ModuleInterne {

	/**
	 * 
	 */
	private ModuleMenuUtilisateur mmu;
	/**
	 * 
	 */
	private TypeRessource path1;
	/**
	 * 
	 */
	private TypeRessource path2 = null;

	/**
	 * 
	 */
	private List<Concept> listetotale;
	/**
	 * 
	 */
	private ProfilListeConcept plc;
	/**
	 * 
	 */
	private ArrayList<Concept> liste;

	/**
	 * Retourne une instance Utilisateur de l'utilisateur courant.
	 * 
	 * @return Une instance Utilisateur de l'utilisateur courant.
	 */
	public Utilisateur getUtilisateur() {
		return this.getModuleMenuUtilisateur().getUtilisateur();
	}

	/**
	 * Retourne l'énumération correspondant au type de la première ligne de
	 * l'écran.
	 * 
	 * @return L'énumération correspondant au type de la première ligne de
	 *         l'écran.
	 */
	public TypeRessource getExo1() {
		return this.path1;
	}

	/**
	 * Retourne l'énumération correspondant au type de la seconde ligne de
	 * l'écran.
	 * 
	 * @return L'énumération correspondant au type de la seconde ligne de
	 *         l'écran.
	 */
	public TypeRessource getExo2() {
		return this.path2;
	}

	/**
	 * Accesseur
	 * 
	 * @return Retourne la liste
	 */
	public List<Concept> getListeConcept() {
		return this.liste;
	}

	/**
	 * Accesseur
	 * 
	 * @return la liste totale des concepts pouvant être choisi.
	 */
	public List<Concept> getListeTotale() {
		return this.listetotale;
	}

	/**
	 * Mutateur
	 * 
	 * @param liste2
	 *            la nouvelle liste totale.
	 */
	private void setListeTotale(List<Concept> liste2) {
		// TODO Auto-generated method stub
		this.listetotale = liste2;
	}

	/**
	 * 
	 * @return le profil des Listes de Favoris et "derniers utilisées" de
	 *         l'utilisateur.
	 */
	public ProfilListeConcept getProfilListeConcept() {
		return this.plc;
	}

	/**
	 * 
	 * @return Le moduleMenuUtilisateur
	 */
	private ModuleMenuUtilisateur getModuleMenuUtilisateur() {
		// TODO Auto-generated method stub
		return this.mmu;
	}

	/**
	 * 
	 * @param mp
	 * @param mmu
	 * @param te
	 * @param te2
	 */
	public ModuleCoursSelection(ModulePrincipal mp, ModuleMenuUtilisateur mmu,
			TypeRessource te, TypeRessource te2) {
		super(mp);
		this.mmu = mmu;
		this.path1 = te;
		this.path2 = te2;
		this.liste = new ArrayList<Concept>();
		init();
	}

	@Override
	/**
	 * 
	 */
	public void init() {
		/*
		 * VERIFICATION DES RESSOURCES NECESSAIRES APRES FILTRE DISPONIBILITE
		 * DES RESSOURCES UTILISATEURS / RESSOURCES PAR DEFAUT SI te et te2
		 * POSSEDENT DES RESSOURCES MANQUANTES ON LES RETIRE
		 * 
		 * SI ut.getVoix() POSSEDENT DES RESSOURCES MANQUANTES ON LES RETIRE
		 */

		this.listetotale = UtilisationFichier.chargerListeConcepts();
		if(this.getListeTotale().contains(new Concept("origine","origine (graphe)"))){
			System.out.println("origine");
			this.getListeTotale().remove(new Concept("origine","origine (graphe)"));
		}
		filtrageDesConcepts();

		this.plc = UtilisationFichier
				.chargerProfilListeConcepts(getUtilisateur());
		if (this.getProfilListeConcept() == null) {
			this.plc = new ProfilListeConcept();
			UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
					getProfilListeConcept());
		}

		Iterator<Concept> it = this.getProfilListeConcept().getDerniers()
				.iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			if (this.getListeTotale().contains(local))
				this.getListeConcept().add(local);
		}

		super.setVue(new VueCoursSelection(this));
		getVue().setVisible(true);
	}

	public VueCoursSelection getVue() {
		return (VueCoursSelection) super.getVue();
	}

	/**
	 * 
	 * @param pic
	 */
	public void addConcept(Concept pic) {

		Iterator<Concept> it = this.getListeConcept().iterator();
		Concept local = null;
		while (it.hasNext()) {
			local = it.next();
			if (local.equals(pic))
				break;
		}
		if (local == null) {
			this.getListeConcept().add(pic);
			this.getProfilListeConcept().addConceptDernier(pic);
		} else {
			if (!local.equals(pic)) {
				this.getListeConcept().add(pic);
				this.getProfilListeConcept().addConceptDernier(pic);
			}
		}

		UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
				getProfilListeConcept());
	}

	/**
	 * 
	 * @param pic
	 */
	public void supConcept(Concept pic) {
		Iterator<Concept> it = this.getListeConcept().iterator();
		Concept local = null;
		while (it.hasNext()) {
			local = it.next();
			if (local.equals(pic))
				break;
		}
		if (local != null) {
			if (local.equals(pic)) {
				this.getListeConcept().remove(local);
				this.getProfilListeConcept().supConceptDernier(local);
			}

		}

		UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
				getProfilListeConcept());
		getVue().mettreAJour();
	}

	/**
	 * 
	 * @param pic
	 */
	public void addConceptFavoris(Concept pic) {
		Iterator<Concept> it = this.getProfilListeConcept().getFavoris()
				.iterator();
		Concept local = null;
		while (it.hasNext()) {
			local = it.next();
			if (local.equals(pic))
				break;
		}
		if (local == null) {
			this.getProfilListeConcept().getFavoris().add(pic);
		} else {
			if (!local.equals(pic))
				this.getProfilListeConcept().getFavoris().add(pic);
		}

		UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
				getProfilListeConcept());

		getVue().mettreAJour();
	}

	/**
	 * 
	 * @param pic
	 */
	public void supConceptFavoris(Concept pic) {
		Iterator<Concept> it = this.getProfilListeConcept().getFavoris()
				.iterator();
		Concept local = null;
		while (it.hasNext()) {
			local = it.next();
			if (local.equals(pic))
				break;
		}
		if (local != null) {
			if (local.equals(pic)) {
				this.getProfilListeConcept().getFavoris().remove(local);

			}

		}

		UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
				getProfilListeConcept());

		getVue().mettreAJour();
	}

	/**
	 * @param cpt
	 * @return la position du concept dans la liste de sélection.
	 * @throws DeplacementDansListeException
	 */
	public int getPositionConceptDansListe(Concept cpt)
			throws DeplacementDansListeException {
		if (getListeConcept().contains(cpt)) {
			int pos = 0;
			while (pos < getListeConcept().size()
					&& !getListeConcept().get(pos).equals(cpt))
				pos++;

			return pos;
		} else {
			throw new DeplacementDansListeException(
					"L'élément choisi ne fait pas partie de la liste des concepts choisis");
		}
	}

	/**
	 * @param cpt
	 * @param up
	 * @throws DeplacementDansListeException
	 */
	public void deplacerUnCranDansListe(Concept cpt, boolean up)
			throws DeplacementDansListeException {
		if (getListeConcept().contains(cpt)) {
			int pos = getPositionConceptDansListe(cpt);

			if (pos == 0 && up)
				throw new DeplacementDansListeException(
						"L'élément choisi est déjà en haut de la liste des concepts");
			else if (pos == getListeConcept().size() - 1 && !up)
				throw new DeplacementDansListeException(
						"L'élément choisi est déjà en bas de la liste des concepts");
			else if (up) {
				getListeConcept().remove(pos);
				getListeConcept().add(pos - 1, cpt);
			} else {
				getListeConcept().remove(pos);
				getListeConcept().add(pos + 1, cpt);
			}

			this.getProfilListeConcept().setDerniersUtilises(getListeConcept());

			UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
					getProfilListeConcept());
			getVue().reloadListe();

		} else {
			throw new DeplacementDansListeException(
					"L'élément choisi ne fait pas partie de la liste des concepts choisis");

		}

	}

	/**
	 * 
	 */
	public void chargerMenuUtilisateur() {
		super.getModulePrincipal().changerModuleCourant(
				this.getModuleMenuUtilisateur());
	}

	/**
	 * 
	 *        
	 */
	public void chargerDefilement() {
		super.getModulePrincipal().changerModuleCourant(
				new ModuleCoursDefilement(super.getModulePrincipal(), this));

	}

	/**
	 * 
	 */
	public void chargerSouris() {
		super.getModulePrincipal().changerModuleCourant(
				new ModuleCoursSouris(getModulePrincipal(), this,
						(ArrayList<Concept>) this.getListeConcept()));

	}

	/**
	 * 
	 */
	public void chargerListeFavoris() {
		this.liste = new ArrayList<Concept>();

		this.getListeConcept()
				.addAll(this.getProfilListeConcept().getFavoris());

		this.getProfilListeConcept().setDerniersUtilises(
				this.getProfilListeConcept().getFavoris());
		UtilisationFichier.enregistrerProfilListeConcept(getUtilisateur(),
				getProfilListeConcept());

		getVue().reload(getListeTotale());
	}

	/**
	 * 
	 * @return true si l'utilisateur a choisi d'afficher deux types d'images,
	 *         sinon false.
	 */
	public boolean estDoubleExercice() {
		return this.getExo2() != null;
	}

	/**
	 * 
	 * @param nom
	 * @return la liste des concepts commençant par la variable nom.
	 */
	public List<Concept> rechercheParNom(String nom) {
		if (nom.trim().equals("")) {
			return this.getListeTotale();
		}

		List<Concept> rst = new ArrayList<Concept>();

		Iterator<Concept> it = this.getListeTotale().iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			int i = 0;
			while (i < nom.length()
					&& nom.length() <= local.getNomConcept().length()
					&& Character.toLowerCase(nom.charAt(i)) == Character
							.toLowerCase(local.getNomConcept().charAt(i)))
				i++;

			if (i == nom.length()
					&& !local.equals(new Concept("origine", "origine (graphe)")))
				rst.add(local);

		}
		return rst;
	}

	/**
	 * 
	 */
	private void filtrageDesConcepts() {
		filtrageDesConceptsParGenre(this.getUtilisateur().getGenre());
		System.out.println("filtrageDesConcepts:"
				+ this.getListeTotale().size() + "par genre");
		if (this.getListeTotale().size() == 0) {
			VueMessages.afficherMessageDansVue(getVue(),
					"Les ressources par défaut sont manquantes pour ce genre : "
							+ this.getUtilisateur().getGenre());
		}

		filtrageDesConceptsParSonsDisponibles(this.getUtilisateur().getVoix());
		System.out.println("filtrageDesConcepts:"
				+ this.getListeTotale().size() + "par voix");
		if (this.getListeTotale().size() == 0) {
			VueMessages.afficherMessageDansVue(getVue(),
					"Les ressources par défaut sont manquantes pour les sons de la voix : "
							+ this.getUtilisateur().getVoix());
		}

		if (getExo1() == TypeRessource.photographies
				|| getExo2() == TypeRessource.photographies) {
			filtrageDesConceptsParPhotographiesDisponibles(this
					.getUtilisateur());
			System.out.println("filtrageDesConcepts:"
					+ this.getListeTotale().size() + "par photographies dispo");
			if (this.getListeTotale().size() == 0) {
				VueMessages
						.afficherMessageDansVue(getVue(),
								"Les ressources par défaut sont manquantes pour les photographies.");
			}
		}

		if (getExo1() == TypeRessource.images
				|| getExo2() == TypeRessource.images) {
			filtrageDesConceptsParImagesDisponibles(this.getUtilisateur());

			if (this.getListeTotale().size() == 0) {
				VueMessages
						.afficherMessageDansVue(getVue(),
								"Les ressources par défaut sont manquantes pour les images");
			}
		}
		if (getExo1() == TypeRessource.pictogrammes
				|| getExo2() == TypeRessource.pictogrammes) {

			filtrageDesConceptsParPictogrammesDisponibles(this.getUtilisateur());

		}

	}

	/**
	 * 
	 * @param genre
	 */
	private void filtrageDesConceptsParGenre(TypeGenre genre) {
		Iterator<Concept> it = this.getListeTotale().iterator();

		List<Concept> liste2 = new ArrayList<Concept>();

		while (it.hasNext()) {
			Concept local = it.next();
			if (local.getGenre() == TypeGenre.TOUS || local.getGenre() == genre)
				liste2.add(local);
		}

		this.setListeTotale(liste2);
	}

	/**
	 * 
	 * @param voix
	 */
	private void filtrageDesConceptsParSonsDisponibles(Voix voix) {
		Iterator<Concept> it = this.getListeTotale().iterator();

		List<Concept> liste2 = new ArrayList<Concept>();

		while (it.hasNext()) {
			Concept local = it.next();
			if (UtilisationFichier.existeSonPour(voix, local)
					|| UtilisationFichier.existeRessourceUtilisateur(
							TypeRessource.sons, local, getUtilisateur()))
				liste2.add(local);
		}

		this.setListeTotale(liste2);
	}

	/**
	 * 
	 * @param utilisateur
	 */
	private void filtrageDesConceptsParPhotographiesDisponibles(
			Utilisateur utilisateur) {
		Iterator<Concept> it = this.getListeTotale().iterator();

		List<Concept> liste2 = new ArrayList<Concept>();

		while (it.hasNext()) {
			Concept local = it.next();
			boolean elementPhotoPrivateFound = (local.getPhotoPrivee() && UtilisationFichier
					.existeRessourceUtilisateur(TypeRessource.photographies,
							local, utilisateur));
			boolean elementPrivateFound = !local.getPhotoPrivee()
					&& UtilisationFichier.existeRessourceUtilisateur(
							TypeRessource.photographies, local,
							getUtilisateur());

			boolean ressDefaut = UtilisationFichier
					.existeRessourceImageParDefaut(TypeRessource.photographies,
							local);
			if (elementPhotoPrivateFound || elementPrivateFound || ressDefaut)
				liste2.add(local);
		}

		this.setListeTotale(liste2);
	}

	/**
	 * 
	 * @param utilisateur
	 */
	private void filtrageDesConceptsParImagesDisponibles(Utilisateur utilisateur) {
		Iterator<Concept> it = this.getListeTotale().iterator();

		List<Concept> liste2 = new ArrayList<Concept>();

		while (it.hasNext()) {
			Concept local = it.next();
			boolean elementPhotoPrivateFound = (local.getPhotoPrivee() && UtilisationFichier
					.existeRessourceUtilisateur(TypeRessource.images, local,
							utilisateur));
			boolean elementPrivateFound = !local.getPhotoPrivee()
					&& UtilisationFichier.existeRessourceUtilisateur(
							TypeRessource.images, local, getUtilisateur());

			boolean ressDefaut = UtilisationFichier
					.existeRessourceImageParDefaut(TypeRessource.images, local);

			if (elementPhotoPrivateFound || elementPrivateFound || ressDefaut) {
				liste2.add(local);
			}
		}

		this.setListeTotale(liste2);
	}

	/**
	 * 
	 * @param utilisateur
	 */
	private void filtrageDesConceptsParPictogrammesDisponibles(
			Utilisateur utilisateur) {
		Iterator<Concept> it = this.getListeTotale().iterator();

		List<Concept> liste2 = new ArrayList<Concept>();

		while (it.hasNext()) {
			Concept local = it.next();

			boolean elementPhotoPrivateFound = (local.getPhotoPrivee() && UtilisationFichier
					.existeRessourceUtilisateur(TypeRessource.pictogrammes,
							local, utilisateur));
			boolean elementPrivateFound = !local.getPhotoPrivee()
					&& UtilisationFichier
							.existeRessourceUtilisateur(
									TypeRessource.pictogrammes, local,
									getUtilisateur());

			boolean ressDefaut = UtilisationFichier
					.existeRessourceImageParDefaut(TypeRessource.pictogrammes,
							local);

			if (elementPhotoPrivateFound || elementPrivateFound || ressDefaut)
				liste2.add(local);
		}

		this.setListeTotale(liste2);
	}
}
