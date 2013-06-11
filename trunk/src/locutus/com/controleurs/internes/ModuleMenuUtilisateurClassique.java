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
package com.controleurs.internes;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuPrincipal;
import com.locutus.controleurs.internes.ModuleMenuUtilisateur;
import com.locutus.controleurs.internes.pratique.ModulePratiqueSelection;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.modeles.enums.TypeStatut;
import com.locutus.outils.fichiers.TraitementFichier;
import com.locutus.outils.fichiers.TraitementSambaFichier;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.vue.pc.VueMenuUtilisateurClassique;

/**
 * 
 * @author BEUGNON Sebastien
 * @version 1.0
 * 
 */
public class ModuleMenuUtilisateurClassique extends ModuleMenuUtilisateur {

	/**
	 * @param mp
	 * @param mmp
	 * @param ut
	 */
	public ModuleMenuUtilisateurClassique(ModulePrincipal mp,
			com.controleurs.internes.ModuleMenuPrincipalClassique mmp,
			Utilisateur ut) {
		super(mp, mmp, ut);
		init();
	}

	/**
	 * 
	 * @param mp
	 *            le module principal du programme
	 * @param mmp
	 *            le module du menu principal
	 * @param ut
	 *            l'utilisateur sélectionné
	 */
	public ModuleMenuUtilisateurClassique(ModulePrincipal mp,
			ModuleMenuPrincipal mmp, Utilisateur ut) {
		super(mp, mmp, ut);

		init();
	}

	/**
	 * Chargement du Module Pratique.
	 */
	public void chargerModulePratiqueSelection() {
		super.getModulePrincipal().changerModuleCourant(
				new ModulePratiqueSelection(getModulePrincipal(), this));
	}

	/**
	 * Chargement du Module de Menu Principal.
	 */
	public void chargerMenuPrincipal() {
		super.getModulePrincipal().changerModuleCourant(
				getModuleMenuPrincipal());
	}

	/**
	 * Lancer la demande d'exportation du profil utilisateur pour l'utiliser
	 * dans un programme mono-user
	 */
	public void lancerExportation() {

		int retour = VueMessages
				.afficherQuestionDansVue(getVue(),
						"Voulez-vous aussi récupérer les ressources déjà installées pour ce profil ?");
		if (!(retour == VueMessages.CANCEL_OPTION)) {
			File fl = VueMessages.choisirDossier();
			if (fl != null)
				fl.mkdirs();
			System.out.println(fl.getPath());
			if (fl.exists()) {

				String path = fl.getAbsolutePath();

				try {
					// Recopie des ressources par défaut
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TraitementFichier.creerDossier(path, "ressources");
					TraitementFichier.copierFichierVers(
							System.getProperty("user.dir"), path, "ressources",
							null, false);
					System.out.println("ressources done.");
					// Creation du fichier utilisateur
					TraitementFichier.creerDossier(path, "local");
					TraitementFichier.enregistrerFichier(path,
							"utilisateur.bin", this.getUtilisateur());
					System.out.println("utilisateur done.");

					TraitementFichier.enregistrerFichier(path + File.separator
							+ "local", "concepts.bin",
							(Serializable) UtilisationFichier
									.chargerListeConcepts());
					System.out.println("concepts done.");

					// Recopie du .jar special mono-user

					TraitementFichier.copierFichierVers(
							System.getProperty("user.dir"), path,
							"locutus-mono.jar", null, true);
					System.out.println("mono done.");

					// Recopie du core du programme
					TraitementFichier.copierFichierVers(
							System.getProperty("user.dir"), path,
							"locutus-core.jar", null, false);

					// Recopie des librairies liées
					TraitementFichier.copierFichierVers(
							System.getProperty("user.dir"), path, "lib", null,
							false);

					// Recopie du .exe //PLUS TARD
					TraitementFichier.copierFichierVers(
							System.getProperty("user.dir"), path,
							"locutus-mono.exe", null, false);

					if (retour == VueMessages.YES_OPTION) {

						// VERIFIER STATUT
						// Recopie des ressources utilisateurs

						if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
							for (int i = 0; i < TypeRessource.values().length; i++) {
								TraitementFichier.copierFichierVers(
										OptionsProgramme.getOptionsCourantes()
												.getCheminCourant()
												+ File.separator
												+ "ressources"
												+ File.separator
												+ getUtilisateur()
														.getIdDossier()
												+ File.separator, path
												+ File.separator
												+ "local"
												+ File.separator
												+ "ressources"
												+ File.separator
												+ getUtilisateur()
														.getIdDossier()
												+ File.separator, TypeRessource
												.values()[i].toString(), null,
										false);

							}
						} else if (OptionsProgramme.getOptionsCourantes()
								.getStatut() == TypeStatut.DependantAuReseauSmb) {

							// DEPLACEMENT RECURSIF DE SMB
							TraitementSambaFichier.getFichier(OptionsProgramme
									.getOptionsCourantes()
									.getAuthentificateurSMB(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant(),
									"ressources");

							TraitementSambaFichier.copierFichierVersLocal(
									OptionsProgramme.getOptionsCourantes()
											.getAuthentificateurSMB(),
									OptionsProgramme.getOptionsCourantes()
											.getCheminDistant(), "ressources",
									path, null, false);

						}
					} else {
						TraitementFichier.creerDossier(path + File.separator
								+ "local" + File.separator, "ressources");
						TraitementFichier.creerDossier(path + File.separator
								+ "local" + File.separator + "ressources",
								getUtilisateur().getIdDossier());

						int i = 0;
						while (i < TypeRessource.values().length) {
							TraitementFichier.creerDossier(path
									+ File.separator + "local" + File.separator
									+ "ressources" + File.separator
									+ getUtilisateur().getIdDossier(),
									TypeRessource.values()[i].toString());
							i++;
						}
					}
				} catch (IOException e) {
					// ERREUR LORS DE LA RECOPIE DES FICHIERS OU LA CREATION DES
					// DOSSIERS
					e.printStackTrace();
				}

				VueMessages.afficherMessageDansVue(getVue(),
						"Exportation réussie !");
			} else {
				VueMessages.afficherMessageDansVue(getVue(),
						"Annulation de l'exportation.");
			}

		}
	}

	@Override
	public void init() {
		super.setVue(new VueMenuUtilisateurClassique(this));
	}

}
