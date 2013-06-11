/**
 * Copyright (C) 2013
	Sebastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
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
package com.locutus.controleurs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;

import com.locutus.exceptions.FichierVoixInvalideException;
import com.locutus.exceptions.modeles.OptionsUtilisateurException;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeStatut;
import com.locutus.outils.fichiers.CollecteurDistant;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.starter.StarterConcept;
import com.locutus.starter.StarterUtilisateur;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.VuePrincipal;

/**
 * Fichier : ModulePrincipal.java<br/>
 * Email : Sebastien Beugnon - sebastien.beugnon.pro[at]gmail.com <br/>
 * Projet : Locutus. (Logiciel d'Apprentissage des Pictogrammes par les Images
 * et le Son.) Developpe pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapes de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Prades-Le-Lez 34730 <br/>
 * <br/>
 * 
 * Le ModulePrincipal est le module (ou le controleur) principal de
 * l'application s'occupant des traitements de demarrage de l'application comme
 * :<br>
 * - faire les mises-a-jours du programme.<br>
 * - relever les erreurs d'integrite de contenu (ressources disparues, connexion
 * impossible aux reseaux).<br>
 * - stocker si le reseau distant est accessible La vue d'un ModulePrincipal est
 * une fenetre dont le module change le contenu pour afficher celui des modules
 * internes.<br>
 * 
 * @author Sebastien Beugnon - Chef de projet, Analyste, Developpeur
 * 
 */
public final class ModulePrincipal extends ModuleAbstrait {

	/**
	 * 
	 */
	public static final int FLAG_NOT_FULLSCREEN = 0;

	/**
	 * 
	 */
	public static final int FLAG_MONO_USER = 1;

	/**
	 * 
	 */
	public static final int FLAG_IGNORE_USERS = 2;

	/**
	 * 
	 */
	public static final int FLAG_IGNORE_VOICES = 3;
	/**
	 * 
	 */
	private String titre;
	/**
	 * Contient le module/controleur interne courant
	 */
	private ModuleInterne modulecourant;

	/**
	 * 
	 */
	private boolean[] flags;

	/**
	 * Accesseur au controleur interne
	 * 
	 * @return le controleur interne en cours d'utilisation par le programme.
	 */
	private ModuleInterne getModuleCourant() {
		return this.modulecourant;

	}

	/**
	 * 
	 * @param flag
	 *            le drapeau/l'option a vérifié.
	 * @return true si le drapeau/l'option est activée.
	 */
	public boolean getFlagState(int flag) {
		return this.flags[flag];
	}

	/**
	 * Constructeur vide d'un ModulePrincipal
	 * 
	 * @param title
	 */
	public ModulePrincipal(String title) {
		this.titre = title;
		this.flags = new boolean[4];
		for (int i = 0; i < flags.length; i++) {
			this.flags[i] = false;
		}
		init();
	}

	/**
	 * @param title
	 * @param flag
	 */
	public ModulePrincipal(String title, int... flag) {
		this.titre = title;
		this.flags = new boolean[4];
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == FLAG_NOT_FULLSCREEN) {
				this.flags[FLAG_NOT_FULLSCREEN] = true;
			} else if (flag[i] == FLAG_MONO_USER)
				this.flags[FLAG_MONO_USER] = true;
			else if (flag[i] == FLAG_IGNORE_USERS)
				this.flags[FLAG_IGNORE_USERS] = true;
			else if (flag[i] == FLAG_IGNORE_VOICES)
				this.flags[FLAG_IGNORE_VOICES] = true;
			else
				System.err.println("not valid flag");
		}
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		initSystemErr();
		OptionsProgramme.initialisation();
		
		super.setVue(new VuePrincipal(this.getTitre(), this));

		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb)
			initSmbRecuperation();
		
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant)
			initChargementConcepts();

		if (!(getFlagState(FLAG_MONO_USER) || getFlagState(FLAG_IGNORE_USERS)))
			initChargementUtilisateurs();

		if (!getFlagState(FLAG_IGNORE_VOICES))
			initChargementVoix();

	}

	private void initChargementConcepts() {
		if (UtilisationFichier.chargerListeConcepts() == null)
			try {
				StarterConcept.starter();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
	}

	private void initChargementVoix() {
		boolean pass = true;

		do {
			pass = true;
			try {
				UtilisationFichier.chargerListeVoix();
			} catch (FichierVoixInvalideException e) {
				System.err.println(new Date());
				System.err.println("Dossier de voix sans fichier voix.");
				try {
					UtilisationFichier.addVoix(new Voix(e.getDirectory()
							.getName(), TypeGenre.TOUS));
				} catch (IOException e1) {
				}
				pass = false;
			}

		} while (!pass);
	}

	/**
	 * 
	 */
	private void initSystemErr() {
		try {
			System.setErr(new PrintStream(new FileOutputStream(new File(
					"debug.log"), true), true));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void initSmbRecuperation() {
		if (this.verifierAccesSmb()) {

			CollecteurDistant.getInstance().recuperation();

		} else {
			OptionsProgramme.getOptionsCourantes().setStatut(
					TypeStatut.Independant);
			VueMessages
					.afficherMessageDansVue(
							null,
							"L'accès au serveur de partage Windows (SMB) semble être compromis. Lancement en mode independant.");
		}
	}

	/**
	 * 
	 */
	private void initChargementUtilisateurs() {
		try {
			this.chargerListeUtilisateur();

		} catch (Exception e) {
			VueMessages.afficherMessageDansVue(null,
					"Liste d'utilisateur inexistante.");
			// Generation d'une liste par defaut
			try {
				StarterUtilisateur.starter();
			} catch (IOException | OptionsUtilisateurException e1) {

			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean verifierAccesSmb() {
		return UtilisationFichier.chargerListeConcepts() != null
				&& UtilisationFichier.chargerListeUtilisateur() != null;
	}

	/**
	 * 
	 * @return le titre du programme
	 */
	private String getTitre() {
		return this.titre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#getVue()
	 */
	@Override
	public VuePrincipal getVue() {
		return ((VuePrincipal) super.getVue());
	}

	/**
	 * Cette methode remplacera le controleur actuel par le controleur interne
	 * transmis en parametre et s'occupera de relier la vue du controleur
	 * interne a celle du controleur principal.
	 * 
	 * @param AM
	 *            le controleur/module que l'on souhaite utiliser (avec sa vue
	 *            pre-chargee)
	 */
	public void changerModuleCourant(ModuleInterne AM) {
		if (this.getModuleCourant() != null)
			this.getModuleCourant().getVue().setVisible(false);

		this.modulecourant = AM;

		if (this.getModuleCourant().getVue() != null) {
			this.getVue().setPanneau(this.getModuleCourant().getVue());
		} else {
			System.err
					.println("ModulePrincipal:changerModuleCourant: vue vide");
		}
		System.gc();
	}

	/**
	 * Methode servant a commander le chargement de l'ensemble d'utilisateurs
	 * sauvegardee.
	 * 
	 */
	public void chargerListeUtilisateur() {
		Iterator<Utilisateur> it = UtilisationFichier.chargerListeUtilisateur()
				.iterator();
		while (it.hasNext()) {
			Utilisateur local = it.next();
			Utilisateur.setLastId(local.getId());
		}
	}

}