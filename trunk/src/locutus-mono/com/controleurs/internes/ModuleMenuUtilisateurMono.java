package com.controleurs.internes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuUtilisateur;
import com.locutus.controleurs.internes.cours.ModuleCoursSelection;
import com.locutus.controleurs.internes.pratique.ModulePratiqueSelection;
import com.locutus.exceptions.modeles.UtilisateurException;
import com.locutus.modeles.Cadre;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.vue.pc.VueMenuUtilisateurMono;

/**
 * 
 * @author Beugnon
 * @version Mono-User
 * 
 */
public class ModuleMenuUtilisateurMono extends ModuleMenuUtilisateur {

	/**
	 * 
	 */
	private Utilisateur utilisateurcourant;

	/**
	 * 
	 * @param mp
	 * @param ut
	 */
	public ModuleMenuUtilisateurMono(ModulePrincipal mp, Utilisateur ut) {
		super(mp, null, ut);
		this.utilisateurcourant = ut;
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.controleurs.internes.ModuleMenuUtilisateur#init()
	 */
	@Override
	public void init() {
		super.setVue(new VueMenuUtilisateurMono(this));
	}

	/**
	 * 
	 * @return l'utilisateur courant
	 */
	public Utilisateur getUtilisateur() {
		return this.utilisateurcourant;
	}

	/**
	 * 
	 * @param te
	 * @param te2
	 */
	public void chargerModuleCours(TypeRessource te, TypeRessource te2) {
		super.getModulePrincipal().changerModuleCourant(
				new ModuleCoursSelection(super.getModulePrincipal(), this, te,
						te2));
	}

	/**
	 * Chargement du Module Pratique.
	 */
	public void chargerModulePratiqueSelection() {
		super.getModulePrincipal().changerModuleCourant(
				new ModulePratiqueSelection(getModulePrincipal(), this));
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
			this.getUtilisateur().setOptionsUtilisateur(ou);
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(new File(
						System.getProperty("user.dir") + File.separator
								+ "utilisateur.bin")));
				try {
					oos.writeObject(this.getUtilisateur());
					oos.close();
				} catch (IOException e) {
					return false;
				}
			} catch (IOException e1) {
				return false;
			}

		}
		return true;

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
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(new File(
						System.getProperty("user.dir") + File.separator
								+ "utilisateur.bin")));
				try {
					oos.writeObject(this.getUtilisateur());
					oos.close();
				} catch (IOException e) {
					return false;
				}
			} catch (IOException e1) {
				return false;
			}
		}
		return true;
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
		// TODO Auto-generated method stub
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
				getUtilisateur().getOptionsUtilisateur().addCadre(cadre);
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
		// TODO Auto-generated method stub
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
				getUtilisateur().getOptionsUtilisateur().addCadre(cadre);
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

}
