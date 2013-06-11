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

package com.locutus.controleurs.internes.autres;

import java.io.File;
import java.io.IOException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuPrincipal;
import com.locutus.exceptions.ConnexionException;
import com.locutus.exceptions.NonSpecifieException;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.TypeStatut;
import com.locutus.outils.fichiers.CollecteurDistant;
import com.locutus.outils.fichiers.TraitementFichier;
import com.locutus.outils.fichiers.TraitementSambaFichier;
import com.locutus.vue.pc.optionsprogramme.VueOptionsProgramme;

/**
 * 
 * @author Beugnon
 * 
 */
public class ModuleGestionOptionsProgramme extends ModuleInterne {

	/**
	 * 
	 */
	private ModuleMenuPrincipal mmp;

	/**
	 * 
	 * @param mp
	 * @param mmp
	 */
	public ModuleGestionOptionsProgramme(ModulePrincipal mp,
			ModuleMenuPrincipal mmp) {
		super(mp);
		this.mmp = mmp;
		init();
	}

	@Override
	public void init() {
		super.setVue(new VueOptionsProgramme(this));
	}

	/**
	 * 
	 * @return le module du menu principal lié à ce module.
	 */
	public ModuleMenuPrincipal getMenuPrincipal() {
		return this.mmp;
	}

	/**
	 * 
	 */
	public void chargerModuleMenuPrincipal() {
		this.getModulePrincipal().changerModuleCourant(this.getMenuPrincipal());
	}

	/**
	 * 
	 * @param npa
	 * @param pathSmb
	 * @throws NonSpecifieException
	 * @throws IOException
	 * @throws ConnexionException
	 */
	public void testerConnexionSmb(NtlmPasswordAuthentication npa,
			String pathSmb) throws NonSpecifieException, IOException,
			ConnexionException {
		if (npa.getName() == null || npa.getPassword() == null
				|| npa.getName().trim().equals("")
				|| npa.getPassword().trim().equals("")) {
			throw new NonSpecifieException(
					"ModuleOptionsProgramme:testerConnexionSmb: password/username non spécifié.");
		} else {
			SmbFile fi = new SmbFile(pathSmb, npa);
			if (fi.exists()) {

				if (!fi.isDirectory()) {
					getVue().afficherMessage("Ceci n'est pas un dossier.");
					// throw something ?
				} else {
					// Dossier avec droits en écriture et lecture
					if (fi.canWrite() && fi.canRead())
						getVue().afficherMessage(
								"Le chemin est valide.\n Le programme usera ce dossier pour stocker les fichiers.");
					// Dossier auquel 1 ou 2 droits manques.
					else
						getVue().afficherMessage(
								"Le chemin est valide, mais il s'agit d'un dossier sans permission d'écriture ou/et de lecture.");
				}
			} else {
				SmbFile fi2 = new SmbFile(fi.getParent(), npa);
				if (fi2.exists() && fi2.isDirectory()) {
					if (fi2.canWrite() && fi2.canRead()) {
						getVue().afficherMessage(
								"Le dossier indiqué dans l'adresse n'existe pas, mais il peut être créé par le programme.");
					} else {
						getVue().afficherMessage(
								"Le dossier indiqué dans l'adresse n'existe pas et le dossier parent n'autorise pas la création de nouveaux dossiers.");
					}
				} else {
					throw new ConnexionException();
				}
			}

		}

	}

	/**
	 * 
	 * @param npa
	 * @param path
	 */

	public void changerStatutSmb(NtlmPasswordAuthentication npa, String path) {

		try {
			OptionsProgramme.getOptionsCourantes().setAuthentificateurSMB(npa);
			OptionsProgramme.getOptionsCourantes().setCheminDistant(path);
			OptionsProgramme.getOptionsCourantes().setStatut(
					TypeStatut.DependantAuReseauSmb);

			SmbFile fi = new SmbFile(OptionsProgramme.getOptionsCourantes()
					.getCheminDistant(), npa);
			// DOSSIER VIDE
			if (!fi.exists()) {
				// RECOPIE DES FICHIERS LOCAUX
				try {
					fi.mkdirs();
					new SmbFile(fi + "/local/", npa).mkdirs();
					new SmbFile(fi + "/ressources/", npa).mkdirs();
					new SmbFile(fi + "/local/ressources/", npa).mkdirs();
					TraitementSambaFichier.copierLocalVersFichier(npa,
							OptionsProgramme.getOptionsCourantes()
									.getCheminCourant(), "utilisateurs.bin",
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant() + "/local/", null,
							false);

					TraitementSambaFichier.copierLocalVersFichier(npa,
							OptionsProgramme.getOptionsCourantes()
									.getCheminCourant(), "concepts.bin",
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant() + "/local/", null,
							false);

					// RECOPIE DES RESSOURCES UTILISATEURS
					File fl = TraitementFichier.chargerFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant(),
							"ressources");
					TraitementSambaFichier.copierLocalVersFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), fl
									.getParentFile().getAbsolutePath(), fl
									.getName(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant()
									+ "local", "ressources", false);

					// RECOPIE DES RESSOURCES PAR DEFAUT
					fl = TraitementFichier.chargerFichier(
							System.getProperty("user.dir"), "ressources");
					TraitementSambaFichier.copierLocalVersFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), fl
									.getParentFile().getAbsolutePath(), fl
									.getName(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant(),
							null, false);

				} catch (SmbException e) {
					e.printStackTrace();
					// CREATION DU CHEMIN IMPOSSIBLE
				}

			} else {
				// CONNEXION A UN DOSSIER EXISTANT
				if (TraitementSambaFichier.existeFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant() + "/local/",
						"utilisateurs.bin")
						&& TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme.getOptionsCourantes()
										.getCheminDistant() + "/local/",
								"concepts.bin")
						&& TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme.getOptionsCourantes()
										.getCheminDistant(), "ressources")) {
					CollecteurDistant.getInstance().recuperation();
					// UTILISABLE
				} else {
					// SIMPLE INSTALLATION
					SmbFile f = new SmbFile(fi + "/local/", npa);
					if (!f.exists())
						f.mkdirs();

					f = new SmbFile(fi + "/ressources/", npa);
					if (!f.exists())
						f.mkdirs();
					if (!f.exists())
						f = new SmbFile(fi + "/local/ressources/", npa);

					TraitementSambaFichier.copierLocalVersFichier(npa,
							OptionsProgramme.getOptionsCourantes()
									.getCheminCourant(), "utilisateurs.bin",
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant() + "/local/", null,
							false);
					TraitementSambaFichier.copierLocalVersFichier(npa,
							OptionsProgramme.getOptionsCourantes()
									.getCheminCourant(), "concepts.bin",
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant() + "/local/", null,
							false);

					// RECOPIE DES RESSOURCES UTILISATEURS
					File fl = TraitementFichier.chargerFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant(),
							"ressources");

					TraitementSambaFichier.copierLocalVersFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), fl
									.getParentFile().getAbsolutePath(), fl
									.getName(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant()
									+ "/local/", "ressources", false);

					// RECOPIE DES RESSOURCES PAR DEFAUT
					fl = TraitementFichier.chargerFichier(
							System.getProperty("user.dir"), "ressources");
					TraitementSambaFichier.copierLocalVersFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), fl
									.getParentFile().getAbsolutePath(), fl
									.getName(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant(),
							null, false);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
			getVue().afficherMessage(
					"Recopie des données utilisateurs sur le réseau réussie."
							+ " Vous pouvez connecter d'autres ordinateurs sur le dossier suivant : \n "
							+ path);
		} catch (IOException e) {
			OptionsProgramme.getOptionsCourantes().setAuthentificateurSMB(npa);
			OptionsProgramme.getOptionsCourantes().setCheminDistant(path);
			OptionsProgramme.getOptionsCourantes().setCheminCourant("local");
			OptionsProgramme.getOptionsCourantes().setStatut(
					TypeStatut.Independant);
			e.printStackTrace();
			getVue().afficherMessage(
					"Une erreur s'est produite lors de la connexion au serveur Samba.");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.controleurs.ModuleInterne#getVue()
	 */
	@Override
	public VueOptionsProgramme getVue() {
		return (VueOptionsProgramme) super.getVue();
	}

	/**
	 * 
	 */
	public void changerStatutIndependant() {

		OptionsProgramme.getOptionsCourantes().setAuthentificateurSMB(null);

		OptionsProgramme.getOptionsCourantes().setCheminDistant(null);

		OptionsProgramme.getOptionsCourantes()
				.setStatut(TypeStatut.Independant);

		OptionsProgramme.getOptionsCourantes().setCheminCourant("local");

	}

	/**
	 * 
	 */
	public void lancerOutilsAdministrationConcepts() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("java -jar lpc.jar &");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void lancerOutilAdministrationVoix() {
		Runtime runtime = Runtime.getRuntime();

		try {
			runtime.exec("java -jar lpv.jar &");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
